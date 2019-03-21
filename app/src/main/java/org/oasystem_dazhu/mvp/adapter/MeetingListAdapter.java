package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.MeetingListBean;

import java.util.List;

/**
 * Created by www on 2019/3/19.
 */

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingListViewHolder> {
    private Context context;
    private List<MeetingListBean.DataBean> beanList;
    private OnItemClickListener itemClickListener;
    public MeetingListAdapter(Context context, List<MeetingListBean.DataBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public MeetingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeetingListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_meeting,parent,false));
    }

    @Override
    public void onBindViewHolder(MeetingListViewHolder holder, final int position) {
        MeetingListBean.DataBean bean = beanList.get(position);
        holder.item_meeting_title.setText(bean.getName());
        holder.item_meeting_num.setText("会议编号："+bean.getId());
        holder.item_meeting_time.setText("开始时间："+bean.getStart_time());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener != null)
                    itemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class MeetingListViewHolder extends RecyclerView.ViewHolder {
        private TextView item_meeting_title, item_meeting_num, item_meeting_position, item_meeting_time;

        public MeetingListViewHolder(View itemView) {
            super(itemView);
            item_meeting_title = itemView.findViewById(R.id.item_meeting_title);
            item_meeting_num = itemView.findViewById(R.id.item_meeting_num);
            item_meeting_position = itemView.findViewById(R.id.item_meeting_title);
            item_meeting_time = itemView.findViewById(R.id.item_meeting_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }
}
