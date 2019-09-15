package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import org.oasystem_dazhu.mvp.view.customView.XCRoundProgressBar;

import java.util.List;

/**
 * Created by www on 2019/1/6.
 * 所有的文件列表的Adapter
 */

public class OfficialDocumentAdapter extends RecyclerView.Adapter<OfficialDocumentAdapter.OfficialDocumentViewHolder> {
    private Context mContext;
    private List<DocumentBean.DataBean> mBeanList;
    private OnItemClickListener mItemClickListener;
    private boolean mDone;
    private int mUrgent = 0; //加急的标志

    public OfficialDocumentAdapter(boolean done, Context context, List<DocumentBean.DataBean> beanList) {
        this.mDone = done;
        this.mContext = context;
        this.mBeanList = beanList;
    }

    public void setBeanList(List<DocumentBean.DataBean> waitBeanList) {
        this.mBeanList = waitBeanList;
    }

    @Override
    public OfficialDocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OfficialDocumentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_official_document, parent, false));
    }

    @Override
    public void onBindViewHolder(OfficialDocumentViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
        DocumentBean.DataBean bean = mBeanList.get(position);
        if (bean.getDispatch() != null) {
            mUrgent = bean.getDispatch().getUrgent();
        }
        setText(holder.official_title, bean.getDispatch().getName(), mUrgent);
        String serial = "-";
        if (!TextUtils.isEmpty(bean.getDispatch().getSerial())) {
            serial = bean.getDispatch().getSerial();
        }
        setText(holder.official_serial, "文件号：" + serial, mUrgent);
        setText(holder.official_time, "发起时间：" + bean.getDispatch().getCreated_at(), mUrgent);
        setText(holder.official_last_time, "最后操作：" + bean.getDispatch().getUpdated_at(), mUrgent);
        holder.item_percent_circle.setDisplayText(true);
        //专门的处理，是加急文件就显示截办时间
        if (mUrgent == 1) {
            holder.official_end_time.setVisibility(View.VISIBLE);
            String endTime = "-";
            if (!TextUtils.isEmpty(bean.getDispatch().getUrgent_date())) {
                endTime = bean.getDispatch().getUrgent_date();
            }
            setText(holder.official_end_time, "截办时间：" + endTime, mUrgent);
        } else {
            holder.official_end_time.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(bean.getSchedule())) {
            holder.item_percent_circle.setProgress(0);
        } else {
            holder.item_percent_circle.setProgress(Integer.parseInt(bean.getSchedule().replace("%", "")));
        }
        Glide.with(mContext).load(FirmingTypeManager.getInstance().getTypeImg(bean.getDispatch().getForm_type())).
                placeholder(getDefaultResourceId(bean.getDispatch().getForm_type()))
                .into(holder.official_left_img);
    }


    private int getDefaultResourceId(int type) {
        if (type == 1) {
            return R.mipmap.shangjilaiwen;
        }
        if (type == 2) {
            return R.mipmap.pingjilaiwen;
        }

        if (type == 3) {
            return R.mipmap.xiajilaiwen;
        }
        return R.mipmap.wenjianjiankong;
    }

    private void setText(TextView v, String content, int urgent) {
        if (urgent == 1) {
            v.setTextColor(mContext.getResources().getColor(R.color.color_f0000));
        } else v.setTextColor(mContext.getResources().getColor(R.color.color_010101));
        v.setText(content);

    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class OfficialDocumentViewHolder extends RecyclerView.ViewHolder {
        public ImageView official_left_img;
        public TextView official_title, official_end_time, official_company_name, official_serial, official_user_name, official_time, official_last_time;
        public XCRoundProgressBar item_percent_circle;

        public OfficialDocumentViewHolder(View itemView) {
            super(itemView);
            official_left_img = itemView.findViewById(R.id.official_left_img);
            official_serial = itemView.findViewById(R.id.official_serial);
            official_time = itemView.findViewById(R.id.official_time);
            official_end_time = itemView.findViewById(R.id.official_end_time);
            official_title = itemView.findViewById(R.id.official_title);
            official_last_time = itemView.findViewById(R.id.official_last_time);
            item_percent_circle = itemView.findViewById(R.id.item_percent_circle);
        }
    }
}
