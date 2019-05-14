package org.oasystem_dazhu.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.model.bean.AskForLeaveDetailBean;

import java.util.List;

/**
 * Created by www on 2019/3/26.
 */

public class AskForLeaveFlowAdapter extends RecyclerView.Adapter<AskForLeaveFlowAdapter.AskForLeaveFlowViewHolder> {
    private AskForLeaveDetailBean.UserBean userBean;
    private List<AskForLeaveDetailBean.AttendanceExamineBean> beanList;
    private AskForLeaveDetailBean bean;
    private boolean firstAgree = false;

    public AskForLeaveFlowAdapter(AskForLeaveDetailBean bean) {
        this.bean = bean;
        this.beanList = bean.getAttendance_examine();
        this.userBean = bean.getUser();
    }



    @Override
    public AskForLeaveFlowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AskForLeaveFlowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sign_flows, parent, false));
    }

    @Override
    public void onBindViewHolder(AskForLeaveFlowViewHolder holder, int position) {
        if (position == 0) {
            holder.user_name.setText("申请人：" + userBean.getName());
            holder.update_time.setText(bean.getCreated_at());
            holder.status_img.setImageResource(R.mipmap.already_sign);
            holder.line_img.setImageResource(R.mipmap.sign_line);
        } else {
            AskForLeaveDetailBean.AttendanceExamineBean bean = beanList.get(position - 1);
            holder.user_name.setText(bean.getUser().getName());
            holder.flow_name.setText(getFlowName(bean.getStatus()));
            //除了待审批以外的状态才显示时间,
            if (Integer.parseInt(bean.getStatus()) > 1) {
                holder.update_time.setText(bean.getUpdated_at());
                holder.status_img.setImageResource(R.mipmap.already_sign);
                holder.line_img.setImageResource(R.mipmap.sign_line);
            }
            //待审核状态  之后的条条都是灰色
            if (Integer.parseInt(bean.getStatus()) == 1 && !firstAgree) {
                firstAgree = true;
                holder.status_img.setImageResource(R.mipmap.is_signing);
            }
            //最后一个的 需要隐藏掉
            if (position == beanList.size()) {
                holder.line_img.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size() + 1;
    }

    private String getFlowName(String status) {
        if (status.equals("1"))
            return "待审批";
        if (status.equals("2"))
            return "通过";
        if (status.equals("3"))
            return "不通过";
        else return "异常状态";

    }

    class AskForLeaveFlowViewHolder extends RecyclerView.ViewHolder {
        private TextView user_name, flow_name, update_time;
        private ImageView status_img, line_img;

        public AskForLeaveFlowViewHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.sign_name);
            flow_name = itemView.findViewById(R.id.sign_flows);
            update_time = itemView.findViewById(R.id.sign_update_time);
            status_img = itemView.findViewById(R.id.sign_status_img);
            line_img = itemView.findViewById(R.id.sign_line_img);
        }
    }
}
