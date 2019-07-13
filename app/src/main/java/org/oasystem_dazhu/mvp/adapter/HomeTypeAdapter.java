package org.oasystem_dazhu.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.constants.Constants;
import org.oasystem_dazhu.manager.FirmingTypeManager;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListener;
import org.oasystem_dazhu.mvp.model.bean.HomeTypeBean;

import java.util.List;

/**
 * Created by www on 2019/2/16.
 */

public class HomeTypeAdapter extends RecyclerView.Adapter<HomeTypeAdapter.HomeTypeViewHolder> {
    private List<String> imgIdList;
    private List<String> typeContentList;
    private OnItemClickListener listener;
    private Context context;
    private int windowWidth;
    private boolean isHome;
    private int visibleSize = 0;
    private List<HomeTypeBean.DataBean> beanList;

    public HomeTypeAdapter(Context context, List<String> imgIdList, List<String> typeContentList, List<HomeTypeBean.DataBean> beanList, boolean isHome) {
        this.imgIdList = imgIdList;
        this.typeContentList = typeContentList;
        this.context = context;
        this.beanList = beanList;
        this.isHome = isHome;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
    }


    @Override
    public HomeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeTypeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_type, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeTypeViewHolder holder, final int position) {
        ViewGroup.LayoutParams param = holder.itemView.getLayoutParams();
        param.width = windowWidth / (Constants.TYPE_WIDTH_COUNT + 1);
        if ("more".equals(imgIdList.get(position % imgIdList.size()))) {
            Glide.with(context).load(R.mipmap.more_type).fitCenter().into(holder.type_img);
        } else {
            Glide.with(context).load(imgIdList.get(position % imgIdList.size())).fitCenter().placeholder(R.mipmap.sign_add_advise).into(holder.type_img);
        }
        holder.type_tv.setText(typeContentList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        holder.home_type_count.setVisibility(View.VISIBLE);
        if (isHome) {
            if (position < visibleSize - 1) {
                holder.home_type_count.setText(beanList.get(position).getDispatch_flow_list_count() > 99 ? "99"
                        : String.valueOf(beanList.get(position).getDispatch_flow_list_count()));
            } else {
                long totalCount = 0;
                //分类数小于5时，不显示统计的数量
                if (FirmingTypeManager.getInstance().getBeanList().size() <= Constants.TYPE_WIDTH_COUNT) {
                    holder.home_type_count.setVisibility(View.GONE);
                } else {
                    for (int i = Constants.TYPE_WIDTH_COUNT; i < FirmingTypeManager.getInstance().getBeanList().size(); i++) {
                        totalCount = totalCount + FirmingTypeManager.getInstance().getBeanList().get(i).getDispatch_flow_list_count();
                    }
                    holder.home_type_count.setText(totalCount > 99 ? "99"
                            : String.valueOf(totalCount));
                }
            }
        } else {
            holder.home_type_count.setText(beanList.get(position).getDispatch_flow_list_count() > 99 ? "99"
                    : String.valueOf(beanList.get(position).getDispatch_flow_list_count()));
        }
    }

    @Override
    public int getItemCount() {
        if (isHome) {
            if (beanList.size() > Constants.TYPE_WIDTH_COUNT) {
                visibleSize = Constants.TYPE_WIDTH_COUNT + 1;
            } else {
                visibleSize = beanList.size() + 1;
            }
        } else {
            visibleSize = beanList.size() - Constants.TYPE_WIDTH_COUNT;
        }
        return visibleSize;
    }

    class HomeTypeViewHolder extends RecyclerView.ViewHolder {
        private ImageView type_img;
        private TextView type_tv, home_type_count;

        public HomeTypeViewHolder(View itemView) {
            super(itemView);
            type_img = itemView.findViewById(R.id.type_img);
            type_tv = itemView.findViewById(R.id.type_tv);
            home_type_count = itemView.findViewById(R.id.home_type_count);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
