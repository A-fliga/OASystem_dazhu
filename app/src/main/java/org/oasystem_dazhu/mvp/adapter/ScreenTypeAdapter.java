package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by www on 2019/1/8.
 */

public class ScreenTypeAdapter extends RecyclerView.Adapter<ScreenTypeAdapter.ScreenTypeViewHolder> {
    private OnItemClickListener itemClickListener;
    private List<HomeTypeBean.DataBean> beanList;
    private Context context;
    private List<TextView> tv_view = new ArrayList<>();
    private List<Map<Integer, Boolean>> selectedList;

    public ScreenTypeAdapter(List<HomeTypeBean.DataBean> beanList, List<Map<Integer, Boolean>> selectedList, Context context) {
        this.beanList = beanList;
        this.context = context;
        this.selectedList = selectedList;
    }

    @Override
    public ScreenTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScreenTypeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_screen_type, parent, false));
    }

    @Override
    public void onBindViewHolder(ScreenTypeViewHolder holder, final int position) {
        tv_view.add(holder.item_type_tv);
        holder.item_type_tv.setText(beanList.get(position).getName());
        if(selectedList.get(position).get(beanList.get(position).getId())){
            TextView tv = tv_view.get(position);
            tv.setBackgroundResource(R.drawable.et_selected);
            tv.setTextColor(context.getResources().getColor(R.color.color_ffffff));
        }
        setItemClick(holder, position);
    }

    public List<Map<Integer, Boolean>> getList() {
        return selectedList;
    }

    private void setItemClick(ScreenTypeViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean b = selectedList.get(position).get(beanList.get(position).getId());
                Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
                map.put(beanList.get(position).getId(), !b);
                selectedList.set(position, map);
                if (!b) {
                    TextView tv = tv_view.get(position);
                    tv.setBackgroundResource(R.drawable.et_selected);
                    tv.setTextColor(context.getResources().getColor(R.color.color_ffffff));
                } else {
                    TextView tv = tv_view.get(position);
                    tv.setBackgroundResource(R.drawable.et);
                    tv.setTextColor(context.getResources().getColor(R.color.color_010101));
                }
                if (itemClickListener != null)
                    itemClickListener.onItemClick(position);
            }
        });
    }

    public void clearSelected() {
        for (int i = 0; i < tv_view.size(); i++) {
            TextView tv = tv_view.get(i);
            tv.setBackgroundResource(R.drawable.et);
            tv.setTextColor(context.getResources().getColor(R.color.color_010101));
        }
        if (selectedList != null) {
            for (int i = 0; i < selectedList.size(); i++) {
                selectedList.get(i).put(beanList.get(i).getId(), false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class ScreenTypeViewHolder extends RecyclerView.ViewHolder {
        public TextView item_type_tv;

        public ScreenTypeViewHolder(View itemView) {
            super(itemView);
            item_type_tv = itemView.findViewById(R.id.item_screen_type);
        }
    }
}
