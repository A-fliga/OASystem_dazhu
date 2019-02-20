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
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.DocumentBean;

import java.util.List;

/**
 * Created by www on 2019/1/6.
 */

public class OfficialDocumentAdapter extends RecyclerView.Adapter<OfficialDocumentAdapter.OfficialDocumentViewHolder> {

    private Boolean done;
    private Context context;
    private List<DocumentBean.DataBean> beanList;
    private OnItemClickListener itemClickListener;
    private int urgent = 0; //加急的标志

    public OfficialDocumentAdapter(Boolean done, Context context, List<DocumentBean.DataBean> beanList) {
        this.done = done;
        this.context = context;
        this.beanList = beanList;
    }

    public void setBeanList(List<DocumentBean.DataBean> waitBeanList) {
        this.beanList = waitBeanList;
    }

    @Override
    public OfficialDocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OfficialDocumentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_official_document, parent, false));
    }

    @Override
    public void onBindViewHolder(OfficialDocumentViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });
        DocumentBean.DataBean bean = beanList.get(position);
        if (bean.getDispatch() != null)
            urgent = bean.getDispatch().getUrgent();
        setText(holder.official_title, bean.getDispatch().getName(), urgent);
        setText(holder.official_step, "流程步骤：" + bean.getName(), urgent);
        setText(holder.official_time, "发起时间：" + bean.getDispatch().getCreated_at(), urgent);
        setText(holder.official_last_time, "最后操作：" + bean.getDispatch().getUpdated_at(), urgent);
        Glide.with(context).load(FirmingTypeManager.getInstance().getTypeImg(bean.getDispatch().getForm_type())).placeholder(R.mipmap.ic_launcher_round)
                .into(holder.official_left_img);

    }

    private void setText(TextView v, String content, int urgent) {
        if (urgent == 1) {
            v.setTextColor(context.getResources().getColor(R.color.color_f0000));
        } else v.setTextColor(context.getResources().getColor(R.color.color_010101));
        v.setText(content);

    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class OfficialDocumentViewHolder extends RecyclerView.ViewHolder {
        public ImageView official_left_img;
        public TextView official_title, official_office_id, official_company_name, official_step, official_serial, official_user_name, official_time, official_last_time;

        public OfficialDocumentViewHolder(View itemView) {
            super(itemView);
            official_left_img = itemView.findViewById(R.id.official_left_img);
            official_step = itemView.findViewById(R.id.official_step);
            official_time = itemView.findViewById(R.id.official_time);
            official_title = itemView.findViewById(R.id.official_title);
            official_last_time = itemView.findViewById(R.id.official_last_time);
        }
    }
}
