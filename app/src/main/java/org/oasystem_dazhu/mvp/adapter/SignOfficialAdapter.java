package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.utils.AppUtil;
import org.oasystem_dazhu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/1/6.
 */

public class SignOfficialAdapter extends RecyclerView.Adapter<SignOfficialAdapter.SignOfficialViewHolder> {
    private List<String> contentTv;
    private Context context;
    private OnItemClickListener itemClickListener;
    private List<FrameLayout> frameLayoutList = new ArrayList<>();
    private List<TextView> textViewList = new ArrayList<>();

    public SignOfficialAdapter(List<String> contentTv, Context context) {
        this.contentTv = contentTv;
        this.context = context;
    }

    @Override
    public SignOfficialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignOfficialViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sign_right, parent, false));
    }

    @Override
    public void onBindViewHolder(final SignOfficialViewHolder holder, final int position) {
        frameLayoutList.add(holder.sign_right_fl);
        textViewList.add(holder.sign_list_tv);
        if (position == 0) {
            frameLayoutList.get(position).setBackgroundResource(R.mipmap.sign_right_selected);
            textViewList.get(position).setTextColor(context.getResources().getColor(R.color.color_ffffff));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AppUtil.isFastDoubleClick(1000)) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(position);
                    }
                    if (position < contentTv.size() - 1) {
                        holder.sign_right_fl.setBackgroundResource(R.mipmap.sign_right_selected);
                        holder.sign_list_tv.setTextColor(context.getResources().getColor(R.color.color_ffffff));
                        for (int i = 0; i < frameLayoutList.size(); i++) {
                            if (i != position) {
                                frameLayoutList.get(i).setBackgroundResource(R.mipmap.sign_right_back);
                                textViewList.get(i).setTextColor(context.getResources().getColor(R.color.color_000000));
                            }
                        }
                    }
                }
                else {
                    ToastUtil.s("请勿点击太快");
                }
            }

        });
        holder.sign_list_tv.setText(contentTv.get(position));
    }

    @Override
    public int getItemCount() {
        return contentTv.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class SignOfficialViewHolder extends RecyclerView.ViewHolder {
        TextView sign_list_tv;
        public FrameLayout sign_right_fl;

        public SignOfficialViewHolder(View itemView) {
            super(itemView);
            sign_list_tv = itemView.findViewById(R.id.sign_list_tv);
            sign_right_fl = itemView.findViewById(R.id.sign_right_fl);
        }
    }
}
