package org.oasystem_dazhu.mvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListenerWithData;
import org.oasystem_dazhu.mvp.model.bean.AskLeaveBean;

import java.util.List;

/**
 * Created by www on 2019/3/26.
 */

public class AskForLeaveAdapter extends RecyclerView.Adapter<AskForLeaveAdapter.AskForLeaveViewHolder> {
    private List<AskLeaveBean.DataBean> beanList;
    private OnItemClickListenerWithData itemClickListenerWithData;
    @NonNull
    @Override
    public AskForLeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AskForLeaveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ask_for_leave, parent, false));
    }

    public AskForLeaveAdapter(List<AskLeaveBean.DataBean> beanList) {
        this.beanList = beanList;
    }

    @Override
    public void onBindViewHolder(@NonNull AskForLeaveViewHolder holder, int position) {
        final AskLeaveBean.DataBean bean = beanList.get(position);
        holder.ask_for_leave_status.setText(bean.getStatus_name());
        holder.ask_for_leave_user_name.setText("申请人："+bean.getUser().getName());
        holder.ask_for_leave_type.setText("请假类型："+bean.getType_name());
        holder.ask_for_leave_date.setText("时间："+bean.getDate());
        holder.ask_for_leave_reason.setText("请假事由："+bean.getRemark());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListenerWithData != null) {
                    itemClickListenerWithData.onItemClick(bean);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class AskForLeaveViewHolder extends RecyclerView.ViewHolder {
        private TextView ask_for_leave_user_name,ask_for_leave_type,ask_for_leave_date,ask_for_leave_count,ask_for_leave_reason,ask_for_leave_status;
        public AskForLeaveViewHolder(View itemView) {
            super(itemView);
            ask_for_leave_user_name = itemView.findViewById(R.id.ask_for_leave_user_name);
            ask_for_leave_type = itemView.findViewById(R.id.ask_for_leave_type);
            ask_for_leave_date = itemView.findViewById(R.id.ask_for_leave_date);
            ask_for_leave_count = itemView.findViewById(R.id.ask_for_leave_count);
            ask_for_leave_reason = itemView.findViewById(R.id.ask_for_leave_reason);
            ask_for_leave_status = itemView.findViewById(R.id.ask_for_leave_status);
        }
    }

    public void setOnItemClickListener(OnItemClickListenerWithData itemClickListener) {
        this.itemClickListenerWithData = itemClickListener;
    }
}
