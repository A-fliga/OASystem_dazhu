package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.AskForLeaveAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListenerWithData;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;
import org.oasystem_dazhu.mvp.view.AskForLeaveDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/3/25.
 */

public class AskForLeaveActivity extends ActivityPresenter<AskForLeaveDelegate> {
    private List<Integer> idList;
    private int selectedId;

    @Override
    public Class<AskForLeaveDelegate> getDelegateClass() {
        return AskForLeaveDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAskLeaveBean();
        EventBus.getDefault().register(this);
        setOnclick();
        init();

    }

    private void getAskLeaveBean() {
        PublicModel.getInstance().getAskLeaveBean(new MSubscribe<BaseEntity<AskLeaveBean>>() {
            @Override
            public void onNext(BaseEntity<AskLeaveBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    AskForLeaveAdapter adapter = viewDelegate.initList(bean.getData().getData());
                    setItemCLick(adapter);
                }
            }
        });
    }

    private void setItemCLick(AskForLeaveAdapter adapter) {
        adapter.setOnItemClickListener(new OnItemClickListenerWithData() {
            @Override
            public void onItemClick(Object bean) {
                AskLeaveBean.DataBean data = (AskLeaveBean.DataBean) bean;
                Bundle bundle = new Bundle();
                if (selectedId == R.id.my_apply) {
                    bundle.putBoolean("isApplyDetail", true);
                } else {
                    bundle.putString("examine_id", data.getAttendance_examine_one().getId());
//                    //判断一下有没有审批过，有的话就不需要显示审批按钮
                    boolean index = true;
                    for (int i = 0; i < data.getAttendance_examine().size(); i++) {
                        if (Integer.parseInt(data.getAttendance_examine().get(i).getUser_id()) ==
                                UserManager.getInstance().getUserInfo().getId()) {
                            if (Integer.parseInt(data.getAttendance_examine().get(i).getStatus()) == 1)
                                index = false;
                        }
                    }
                    bundle.putBoolean("isApplyDetail", index);

                }
                bundle.putString("leave_apply_id", data.getId());
                startMyActivity(AskForLeaveDetailActivity.class, bundle);
            }
        });
    }


    private void setOnclick() {
        viewDelegate.getToolBarRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity(AddLeaveActivity.class, null);
            }
        });
    }


    private void init() {
        idList = new ArrayList<>();
        idList.add(R.id.my_apply);
        idList.add(R.id.my_approver);
        selectedId = R.id.my_apply;
        setCheckStates(selectedId);
        viewDelegate.setOnClickListener(onClickListener, R.id.my_apply, R.id.my_approver);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setCheckStates(view.getId());
            switch (view.getId()) {
                case R.id.my_apply:
                    if (selectedId != view.getId()) {
                        viewDelegate.getToolBarRightImg().setVisibility(View.VISIBLE);
                        getAskLeaveBean();
                        selectedId = view.getId();
                    }
                    break;
                case R.id.my_approver:
                    if (selectedId != view.getId()) {
                        viewDelegate.getToolBarRightImg().setVisibility(View.GONE);
                        getLeaveApproveBean();
                        selectedId = view.getId();
                    }
                    break;
            }
        }
    };

    private void getLeaveApproveBean() {
        PublicModel.getInstance().getLeaveApprove(new MSubscribe<BaseEntity<AskLeaveBean>>() {
            @Override
            public void onNext(BaseEntity<AskLeaveBean> bean) {
                super.onNext(bean);
                AskForLeaveAdapter adapter = viewDelegate.initList(bean.getData().getData());
                setItemCLick(adapter);
            }
        });
    }

    private void setCheckStates(int id) {
        RelativeLayout parent;
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i) == id) {
                viewDelegate.get(id).setSelected(true);
                parent = (RelativeLayout) viewDelegate.get(id).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_ffffff));

            } else {
                viewDelegate.get(idList.get(i)).setSelected(false);
                parent = (RelativeLayout) viewDelegate.get(idList.get(i)).getParent();
                TextView childTv = (TextView) parent.getChildAt(1);
                childTv.setTextColor(getResources().getColor(R.color.color_e8421d));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshLeaveList(AskLeaveBean bean) {
        if (bean != null) {
            if (selectedId == R.id.my_apply)
                getAskLeaveBean();
            else {
                getLeaveApproveBean();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
