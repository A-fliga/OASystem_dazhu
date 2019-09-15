package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.OfficeListBean;

import java.util.List;

/**
 * Created by www on 2019/1/5.
 * 上传个人文件用的  现在暂时没有用处
 */

public class OfficeListAdapter extends RecyclerView.Adapter<OfficeListAdapter.OfficeListViewHolder> {
    private List<OfficeListBean.DataBean> mBeanList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public OfficeListAdapter(List<OfficeListBean.DataBean> beanList, Context context) {
        this.mBeanList = beanList;
        this.mContext = context;
    }

    @Override
    public OfficeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OfficeListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_office_list, parent, false));
    }

    @Override
    public void onBindViewHolder(OfficeListViewHolder holder, final int position) {
        holder.office_list_title.setText(mBeanList.get(position).getName());
        holder.office_list_time.setText(mBeanList.get(position).getUpdated_at());
        String[] str = mBeanList.get(position).getSource().getName().split("\\.");

        if (str[str.length - 1].equals("pdf")) {
            Glide.with(mContext).load(R.mipmap.office_pdf).into(holder.office_detail_img);
        }
        if (str[str.length - 1].equals("doc") || str[str.length - 1].equals("docx")) {
            Glide.with(mContext).load(R.mipmap.office_word).into(holder.office_detail_img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class OfficeListViewHolder extends RecyclerView.ViewHolder {
        public TextView office_list_title, office_list_time;
        public ImageView office_detail_img;

        public OfficeListViewHolder(View itemView) {
            super(itemView);
            office_list_title = itemView.findViewById(R.id.office_list_title);
            office_list_time = itemView.findViewById(R.id.office_list_time);
            office_detail_img = itemView.findViewById(R.id.office_detail_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }
}
