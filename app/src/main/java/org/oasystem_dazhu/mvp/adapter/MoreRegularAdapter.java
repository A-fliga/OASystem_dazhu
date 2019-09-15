package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.HomeBusinessManagerBean;

import java.util.List;

/**
 * Created by www on 2019/4/14.
 * 首页分类点击更多后的Adapter
 */

public class MoreRegularAdapter extends RecyclerView.Adapter<MoreRegularAdapter.HomeBusinessManagerHolder> {
    private OnItemClickListener mListener;
    private List<HomeBusinessManagerBean> mBeanList;
    private int mWidth;

    public MoreRegularAdapter(List<HomeBusinessManagerBean> beanList, Context context) {
        this.mBeanList = beanList;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();
    }

    @NonNull
    @Override
    public HomeBusinessManagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeBusinessManagerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBusinessManagerHolder holder, final int position) {
        ViewGroup.LayoutParams param = holder.itemView.getLayoutParams();
        param.width = mWidth / 6;
        holder.type_tv.setText(mBeanList.get(position).name);
        holder.type_img.setImageResource(mBeanList.get(position).resId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class HomeBusinessManagerHolder extends RecyclerView.ViewHolder {
        private ImageView type_img;
        private TextView type_tv;
        public HomeBusinessManagerHolder(View itemView) {
            super(itemView);
            type_img = itemView.findViewById(R.id.type_img);
            type_tv = itemView.findViewById(R.id.type_tv);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
