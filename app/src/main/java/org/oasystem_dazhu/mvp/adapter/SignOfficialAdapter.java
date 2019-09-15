package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.oasystem_dazhu.BuildConfig;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.DensityUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/1/6.
 */

public class SignOfficialAdapter extends RecyclerView.Adapter<SignOfficialAdapter.SignOfficialViewHolder> {
    private List<String> mContentTv;
    private List<FrameLayout> mFrameLayoutList = new ArrayList<>();
    private List<TextView> mTextViewList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private boolean mNeedLarge = false;

    public SignOfficialAdapter(List<String> contentTv, Context context) {
        this.mContentTv = contentTv;
        this.mContext = context;
        mNeedLarge = BuildConfig.HOST.equals("http://112.35.0.188:9098/api/");
    }

    @Override
    public SignOfficialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignOfficialViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sign_right, parent, false));
    }

    @Override
    public void onBindViewHolder(final SignOfficialViewHolder holder, final int position) {
        mFrameLayoutList.add(holder.sign_right_fl);
        mTextViewList.add(holder.sign_list_tv);
        if (position == 0) {
            mFrameLayoutList.get(position).setBackgroundResource(R.mipmap.sign_right_selected);
            mTextViewList.get(position).setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AppUtil.isFastDoubleClick(1000)) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(position);
                    }
                    if (position < mContentTv.size() - 1) {
                        holder.sign_right_fl.setBackgroundResource(R.mipmap.sign_right_selected);
                        holder.sign_list_tv.setTextColor(mContext.getResources().getColor(R.color.color_ffffff));
                        for (int i = 0; i < mFrameLayoutList.size(); i++) {
                            if (i != position) {
                                mFrameLayoutList.get(i).setBackgroundResource(R.mipmap.sign_right_back);
                                mTextViewList.get(i).setTextColor(mContext.getResources().getColor(R.color.color_000000));
                            }
                        }
                    }
                }
                else {
                    ToastUtil.s("请勿点击太快");
                }
            }

        });
        holder.sign_list_tv.setText(mContentTv.get(position));
    }

    @Override
    public int getItemCount() {
        return mContentTv.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    class SignOfficialViewHolder extends RecyclerView.ViewHolder {
        TextView sign_list_tv;
        public FrameLayout sign_right_fl;

        public SignOfficialViewHolder(View itemView) {
            super(itemView);
            sign_list_tv = itemView.findViewById(R.id.sign_list_tv);
            sign_right_fl = (FrameLayout) itemView.findViewById(R.id.sign_right_fl);
            if(mNeedLarge){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) sign_right_fl.getLayoutParams();
                params.width = DensityUtil.dip2px(mContext,55);
                params.height = DensityUtil.dip2px(mContext,55);
            }
        }
    }
}
