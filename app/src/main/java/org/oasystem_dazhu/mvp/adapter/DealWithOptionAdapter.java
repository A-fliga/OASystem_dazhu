package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.bean.DealWithOptionBean;

import java.util.List;

/**
 * Created by www on 2019/5/22.
 */

public class DealWithOptionAdapter extends RecyclerView.Adapter<DealWithOptionAdapter.DealWithOptionViewHolder> {
    private Context context;
    private List<DealWithOptionBean.DispatchSuggestBean> beanList;
    private OnItemClick itemClick;
    private boolean done;

    public DealWithOptionAdapter(Context context, List<DealWithOptionBean.DispatchSuggestBean> beanList, boolean done) {
        this.done = done;
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public DealWithOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DealWithOptionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_deal_with_option, null));
    }

    @Override
    public void onBindViewHolder(@NonNull DealWithOptionViewHolder holder, int position) {
        DealWithOptionBean.DispatchSuggestBean bean = beanList.get(position);
        holder.item_deal_option_name.setText("办理人：" + bean.getUser().getName());
        holder.item_deal_option_flow.setText("办理步骤：" + bean.getFlow().getName());
        holder.item_deal_option_time.setText(bean.getCreated_at());
        holder.item_option_content.setText(bean.getContent());
        if (Integer.parseInt(bean.getUser().getId()) == UserManager.getInstance().getUserInfo().getId() && !done) {
            holder.item_deal_option_ll.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_f5f5f5));
        } else {
            holder.item_deal_option_ll.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.color_ffffff));
        }
        if (holder.item_deal_option_ll.getVisibility() == View.VISIBLE) {
            holder.item_option_change.setOnClickListener(getOnClickListener(position));
            holder.item_option_delete.setOnClickListener(getOnClickListener(position));
        }
    }

    private View.OnClickListener getOnClickListener(final int position) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClick != null) {
                    itemClick.onItemOnclick(position, view.getId());
                }
            }
        };
        return onClickListener;
    }


    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class DealWithOptionViewHolder extends RecyclerView.ViewHolder {
        public TextView item_deal_option_name, item_deal_option_flow, item_option_change,
                item_option_delete, item_deal_option_time, item_option_content;
        private LinearLayout item_deal_option_ll;

        public DealWithOptionViewHolder(View itemView) {
            super(itemView);
            item_deal_option_name = itemView.findViewById(R.id.item_deal_option_name);
            item_deal_option_flow = itemView.findViewById(R.id.item_deal_option_flow);
            item_option_change = itemView.findViewById(R.id.item_option_change);
            item_option_delete = itemView.findViewById(R.id.item_option_delete);
            item_deal_option_time = itemView.findViewById(R.id.item_deal_option_time);
            item_option_content = itemView.findViewById(R.id.item_option_content);
            item_deal_option_ll = itemView.findViewById(R.id.item_deal_option_ll);
        }
    }

    public void setOnItemClickListener(OnItemClick itemClickListener) {
        itemClick = itemClickListener;
    }

    public interface OnItemClick {
        void onItemOnclick(int position, int viewId);
    }
}
