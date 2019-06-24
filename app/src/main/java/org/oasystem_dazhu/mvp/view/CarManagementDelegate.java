package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.RecyclerView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.CarApplyListAdapter;
import org.oasystem_dazhu.mvp.model.bean.CarApplyListBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.List;

/**
 * Created by www on 2019/3/23.
 */

public class CarManagementDelegate extends ViewDelegate {
    private RecyclerView recyclerView;
    private CarApplyListAdapter adapter;
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_car_management;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("用车管理");
        setToolBarRightTv("申请");
        recyclerView = get(R.id.car_apply_list);
    }


    public CarApplyListAdapter initList(List<CarApplyListBean.DataBean> beanList) {
        adapter = new CarApplyListAdapter(beanList);
        setRecycler(recyclerView, adapter, true);
        if (beanList.size() == 0) {
            ToastUtil.s("暂无数据");
        }
        return adapter;
    }
}
