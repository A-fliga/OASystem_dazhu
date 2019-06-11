package org.oasystem_dazhu.mvp.presenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.adapter.DealWithOptionAdapter;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.DealWithOptionBean;
import org.oasystem_dazhu.mvp.view.DealWithOptionFormDelegate;
import org.oasystem_dazhu.utils.DialogUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/5/22.
 */

public class DealWithOptionFormActivity extends ActivityPresenter<DealWithOptionFormDelegate> {
    private List<DealWithOptionBean.DispatchSuggestBean> beanList;
    private int listId,itemId;
    private boolean done = false;
    private DealWithOptionAdapter adapter;

    @Override
    public Class<DealWithOptionFormDelegate> getDelegateClass() {
        return DealWithOptionFormDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listId = bundle.getInt("listId");
            itemId = bundle.getInt("itemId");
            done = bundle.getBoolean("done");
            if(done){
                viewDelegate.initOptionView();
            }
            initRecyclerView();
        }
        viewDelegate.setOnClickListener(onClickListener, R.id.add_option_btn);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_option_btn:
                    if(!done) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("itemId", itemId);
                        startMyActivity(AddOptionFormActivity.class, bundle);
                    }
                    else {
                        ToastUtil.s("当前不可提出意见");
                    }
                    break;

            }
        }
    };

    private void initRecyclerView() {
        if (beanList != null) {
            beanList.clear();
        } else {
            beanList = new ArrayList<>();
        }
        PublicModel.getInstance().getFormList(new MSubscribe<BaseEntity<DealWithOptionBean>>() {
            @Override
            public void onNext(BaseEntity<DealWithOptionBean> bean) {
                super.onNext(bean);
                if (beanList.size() != 0) {
                    beanList.clear();
                }
                beanList.addAll(bean.getData().getDispatch_suggest());
                viewDelegate.initLeftTv(beanList.size());
                adapter = viewDelegate.initList(beanList,done);
                adapter.setOnItemClickListener(new DealWithOptionAdapter.OnItemClick() {
                    @Override
                    public void onItemOnclick(int position, int viewId) {
                        handleClickEvent(position, viewId);
                    }
                });
                if (bean.getData().getDispatch_suggest().size() == 0) {
                    ToastUtil.s("暂无数据");
                }

            }
        }, String.valueOf(listId));

    }

    private void handleClickEvent(int position, int viewId) {
        switch (viewId) {
            case R.id.item_option_delete:
                showSureDialog(beanList.get(position).getId());
                break;
            case R.id.item_option_change:
                Bundle bundle = new Bundle();
                bundle.putInt("itemId", itemId);
                bundle.putString("content", beanList.get(position).getContent());
                startMyActivity(AddOptionFormActivity.class, bundle);
                break;

        }
    }

    private void showSureDialog(final String id) {
        DialogUtil.showDialog(this, "您确认要删除吗？", "确认", "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -1) {
                    toDelete(id);
                }
                dialogInterface.dismiss();
            }
        });
    }

    private void toDelete(String id) {
        PublicModel.getInstance().DeleteOptionData(new MSubscribe<BaseEntity>() {
            @Override
            public void onNext(BaseEntity bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    ToastUtil.s("操作成功");
                    initRecyclerView();
                }
            }
        }, id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshOptionlist(DealWithOptionBean bean) {
        if (bean != null) {
            initRecyclerView();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
