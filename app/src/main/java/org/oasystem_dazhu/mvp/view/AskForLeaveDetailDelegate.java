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
    private RecyclerView mApplyDetailRecyclerView;
    private LinearLayout mApproveLL;
    private TextView mLeaveApplyDetailUserName;
    private TextView mLeaveApplyDetailDuringTime;
    private TextView mLeaveApplyDetailTypeName;
    private TextView mLeaveApplyDetailUserApprover;
    private TextView mLeaveApplyDetailReason;

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
        mApproveLL = get(R.id.approve_ll);
        mApplyDetailRecyclerView = get(R.id.apply_detail_recyclerView);
        mLeaveApplyDetailUserName = get(R.id.leave_apply_detail_user_name);
        mLeaveApplyDetailTypeName = get(R.id.leave_apply_detail_type_name);
        mLeaveApplyDetailDuringTime = get(R.id.leave_apply_detail_during_time);
        mLeaveApplyDetailUserApprover = get(R.id.leave_apply_detail_user_approver);
        mLeaveApplyDetailReason = get(R.id.leave_apply_detail_reason);
    }

    public void initView(AskForLeaveDetailBean bean) {
        mLeaveApplyDetailUserName.setText("申请人：" + bean.getUser().getName());
        mLeaveApplyDetailTypeName.setText("请假类型：" + bean.getType_name());
        mLeaveApplyDetailDuringTime.setText("请假时间：" + bean.getDate());
        mLeaveApplyDetailUserApprover.setText("审批人：" + bean.getUser_ids_name());
        mLeaveApplyDetailReason.setText("请假内容：\n\n" + bean.getRemark());
    }

    public void showBottom(Boolean show) {
        if (show) {
            mApproveLL.setVisibility(View.VISIBLE);
        } else
            mApproveLL.setVisibility(View.GONE);
    }


    public void initFlows(AskForLeaveDetailBean bean) {
        AskForLeaveFlowAdapter adapter = new AskForLeaveFlowAdapter(bean);
        setRecyclerView(mApplyDetailRecyclerView, adapter);
    }

    private void setRecyclerView(RecyclerView recyclerView, AskForLeaveFlowAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
