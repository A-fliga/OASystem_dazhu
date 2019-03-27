package org.oasystem_dazhu.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.AskForLeaveFlowAdapter;
import org.oasystem_dazhu.mvp.model.bean.AskForLeaveDetailBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;

/**
 * Created by www on 2019/3/26.
 */

public class AskForLeaveDetailDelegate extends ViewDelegate {
    private RecyclerView apply_detail_recyclerView;
    private LinearLayout approve_ll;
    private TextView leave_apply_detail_user_name,leave_apply_detail_type_name,
            leave_apply_detail_during_time,leave_apply_detail_user_approver,leave_apply_detail_reason;
    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_ask_for_leave_detail;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("申请详情");
        approve_ll = get(R.id.approve_ll);
        apply_detail_recyclerView = get(R.id.apply_detail_recyclerView);
        leave_apply_detail_user_name = get(R.id.leave_apply_detail_user_name);
        leave_apply_detail_type_name = get(R.id.leave_apply_detail_type_name);
        leave_apply_detail_during_time = get(R.id.leave_apply_detail_during_time);
        leave_apply_detail_user_approver = get(R.id.leave_apply_detail_user_approver);
        leave_apply_detail_reason = get(R.id.leave_apply_detail_reason);
    }

    public void initView(AskForLeaveDetailBean bean){
        leave_apply_detail_user_name.setText("申请人："+bean.getUser().getName());
        leave_apply_detail_type_name.setText("请假类型："+bean.getType_name());
        leave_apply_detail_during_time.setText("请假时间："+bean.getDate());
        leave_apply_detail_user_approver.setText("审批人："+bean.getUser_ids_name());
        leave_apply_detail_reason.setText("请假内容：\n\n"+bean.getRemark());
    }

    public void showBottom(Boolean show) {
        if (show) {
            approve_ll.setVisibility(View.VISIBLE);
        }
        else
            approve_ll.setVisibility(View.GONE);
    }


    public void initFlows(AskForLeaveDetailBean bean) {
        AskForLeaveFlowAdapter adapter = new AskForLeaveFlowAdapter(bean);
        setRecyclerView(apply_detail_recyclerView, adapter);
    }

    private void setRecyclerView(RecyclerView recyclerView, AskForLeaveFlowAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
