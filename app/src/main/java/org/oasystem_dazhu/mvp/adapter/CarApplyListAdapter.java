package org.oasystem_dazhu.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.adapter.itemClickListener.OnItemClickListenerWithData;
import org.oasystem_dazhu.mvp.model.bean.CarApplyListBean;

import java.util.List;

/**
 * Created by www on 2019/3/23.
 */

public class CarApplyListAdapter extends RecyclerView.Adapter<CarApplyListAdapter.CarApplyViewHolder> {
    private List<CarApplyListBean.DataBean> beanList;
    private OnItemClickListenerWithData itemClickListenerWithData;

    public CarApplyListAdapter(List<CarApplyListBean.DataBean> beanList) {
        this.beanList = beanList;
    }

    @Override
    public CarApplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarApplyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_apply, parent, false));
    }

    @Override
    public void onBindViewHolder(CarApplyViewHolder holder, int position) {
        final CarApplyListBean.DataBean bean = beanList.get(position);
        holder.car_apply_user_name.setText("申请人："+bean.getUser().getName());
        holder.car_apply_position.setText("目的地：" + bean.getDestination());
        holder.car_apply_mileage.setText("预计里程：" + bean.getMileage() + "km");
        holder.car_apply_reason.setText("用车原因：" + bean.getType_name());
        holder.car_apply_time.setText("用车时间：" + bean.getDate());
        holder.car_apply_status.setText(bean.getStatus_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListenerWithData != null) {
                    itemClickListenerWithData.onItemClick(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class CarApplyViewHolder extends RecyclerView.ViewHolder {
        public TextView car_apply_user_name,car_apply_position, car_apply_mileage, car_apply_reason,
                car_apply_driver, car_apply_phone, car_apply_time, car_apply_status;

        public CarApplyViewHolder(View itemView) {
            super(itemView);
            car_apply_user_name = itemView.findViewById(R.id.car_apply_user_name);
            car_apply_position = itemView.findViewById(R.id.car_apply_position);
            car_apply_mileage = itemView.findViewById(R.id.car_apply_mileage);
            car_apply_reason = itemView.findViewById(R.id.car_apply_reason);
            car_apply_driver = itemView.findViewById(R.id.car_apply_driver);
            car_apply_phone = itemView.findViewById(R.id.car_apply_phone);
            car_apply_time = itemView.findViewById(R.id.car_apply_time);
            car_apply_status = itemView.findViewById(R.id.car_apply_status);
        }
    }

    public void setOnItemClickListener(OnItemClickListenerWithData itemClickListener) {
        this.itemClickListenerWithData = itemClickListener;
    }
}
