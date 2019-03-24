package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.adapter.CarApplyListAdapter;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListenerWithData;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.CarApplyBean;
import org.oasystem_dazhu.mvp.model.bean.CarApplyListBean;
import org.oasystem_dazhu.mvp.view.CarManagementDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/3/23.
 */

public class CarManagementActivity extends ActivityPresenter<CarManagementDelegate> {
    private List<Integer> idList;
    private int selectedId;

    @Override
    public Class<CarManagementDelegate> getDelegateClass() {
        return CarManagementDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        getApplyData();
        setOnclick();
        init();
    }

    private void init() {
        idList = new ArrayList<>();
        idList.add(R.id.my_apply);
        idList.add(R.id.my_approver);
        selectedId = R.id.my_apply;
        viewDelegate.get(R.id.my_apply).setSelected(true);
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
                        getApplyData();
                        selectedId = view.getId();
                    }
                    break;
                case R.id.my_approver:
                    if (selectedId != view.getId()) {
                        viewDelegate.getToolBarRightImg().setVisibility(View.GONE);
                        getApproveData();
                        selectedId = view.getId();
                    }
                    break;
            }
        }
    };

    private void getApproveData() {
        PublicModel.getInstance().getApproveBean(new MSubscribe<BaseEntity<CarApplyListBean>>() {
            @Override
            public void onNext(BaseEntity<CarApplyListBean> bean) {
                super.onNext(bean);
                CarApplyListAdapter adapter = viewDelegate.initList(bean.getData().getData());
                setItemCLick(adapter);
            }
        });
    }

    private void setCheckStates(int id) {
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i) == id) {
                viewDelegate.get(id).setSelected(true);
            } else
                viewDelegate.get(idList.get(i)).setSelected(false);
        }
    }

    private void getApplyData() {
        PublicModel.getInstance().getApplyBean(new MSubscribe<BaseEntity<CarApplyListBean>>() {
            @Override
            public void onNext(BaseEntity<CarApplyListBean> bean) {
                super.onNext(bean);
                CarApplyListAdapter adapter = viewDelegate.initList(bean.getData().getData());
                setItemCLick(adapter);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void refreshCarList(CarApplyBean bean) {
        if (bean != null) {
            if (selectedId == R.id.my_apply)
                getApplyData();
            else {
                getApproveData();
            }
        }
    }


    private void setItemCLick(CarApplyListAdapter adapter) {
        adapter.setOnItemClickListener(new OnItemClickListenerWithData() {
            @Override
            public void onItemClick(Object bean) {
                CarApplyListBean.DataBean data = (CarApplyListBean.DataBean) bean;
                Bundle bundle = new Bundle();
                if (selectedId == R.id.my_apply) {
                    bundle.putBoolean("isApplyDetail", true);
                } else {
                    bundle.putString("examine_id", data.getCar_use_examine_one().getId());
//                    //判断一下有没有审批过，有的话就不需要显示审批按钮
                    boolean index = true;
                    for (int i = 0; i < data.getCar_use_examine().size(); i++) {
                        if (Integer.parseInt(data.getCar_use_examine().get(i).getId()) ==
                                UserManager.getInstance().getUserInfo().getId()) {
                            if (Integer.parseInt(data.getCar_use_examine().get(i).getStatus()) == 1)
                                index = false;
                        }
                    }
                    bundle.putBoolean("isApplyDetail", index);

                }
                bundle.putString("car_apply_id", data.getId());
                startMyActivity(CarApplyDetailActivity.class, bundle);
            }
        });
    }

    private void setOnclick() {
        viewDelegate.getToolBarRightImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity(AddCarApplyActivity.class, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
