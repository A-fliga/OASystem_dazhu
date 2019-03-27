package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.CarApplyFlowAdapter;
import org.oasystem_dazhu.mvp.model.bean.CarApplyDetailBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

/**
 * Created by www on 2019/3/23.
 */

public class CarApplyDetailDelegate extends ViewDelegate {
    private RecyclerView apply_detail_recyclerView;
    private LinearLayout approve_ll;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_car_detail;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("申请详情");
        apply_detail_recyclerView = get(R.id.apply_detail_recyclerView);
        approve_ll = get(R.id.approve_ll);
    }


    public void initView(CarApplyDetailBean bean) {
        TextView car_apply_detail_user_name = get(R.id.car_apply_detail_user_name);
        car_apply_detail_user_name.setText("用车人：" + bean.getUser().getName());
        TextView car_apply_detail_type_name = get(R.id.car_apply_detail_type_name);
        car_apply_detail_type_name.setText("用车类型：" + bean.getType_name());
        TextView car_apply_detail_apply_time = get(R.id.car_apply_detail_apply_time);
        car_apply_detail_apply_time.setText("申请时间：" + bean.getCreated_at());
        TextView car_apply_detail_car_num = get(R.id.car_apply_detail_car_num);
        car_apply_detail_car_num.setText("申请车辆：" + bean.getCar_number());
        TextView car_apply_detail_during_time = get(R.id.leave_apply_detail_during_time);
        car_apply_detail_during_time.setText("预定时间：" + bean.getDate());
        TextView car_apply_detail_user_mileage = get(R.id.car_apply_detail_user_mileage);
        car_apply_detail_user_mileage.setText("预定里程：" + bean.getMileage());
        TextView car_apply_detail_user_target = get(R.id.car_apply_detail_user_target);
        car_apply_detail_user_target.setText("预定目的地：" + bean.getDestination());
        TextView car_apply_detail_user_approver = get(R.id.car_apply_detail_user_approver);
        car_apply_detail_user_approver.setText("审批人：" + bean.getUser_ids_name());
    }

    public void showBottom(Boolean show) {
        if (show) {
            approve_ll.setVisibility(View.VISIBLE);
        } else approve_ll.setVisibility(View.GONE);
    }

    public void initFlows(CarApplyDetailBean bean) {
        CarApplyFlowAdapter adapter = new CarApplyFlowAdapter(bean);
        setRecyclerView(apply_detail_recyclerView, adapter);
    }

    private void setRecyclerView(RecyclerView recyclerView, CarApplyFlowAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
