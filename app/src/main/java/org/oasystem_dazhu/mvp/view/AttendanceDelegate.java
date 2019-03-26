package org.oasystem_dazhu.mvp.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.manager.UserManager;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.InitDateUtil;
import org.oasystem_dazhu.utils.LoadImgUtil;

/**
 * Created by www on 2019/3/25.
 */

public class AttendanceDelegate extends ViewDelegate {
    private TextView sign_time, sign_off_time, sign_in_status;
    private ImageView sign_regular, sign_late, sign_regular_off, sign_leave;
    private int signType = 0;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_attendance;
    }

    @Override
    public void initWidget() {
        getTitleView().setText("考勤打卡");
        ImageView icon = get(R.id.sign_in_icon);
        LoadImgUtil.loadCirclePic(this.getActivity(), UserManager.getInstance().getUserInfo().getHeadimg(),
                icon, R.mipmap.center_icon);
        TextView userName = get(R.id.sign_in_name);
        userName.setText(UserManager.getInstance().getUserInfo().getName());
        TextView sign_in_date = get(R.id.sign_in_date);
        sign_in_date.setText(InitDateUtil.getDate2(System.currentTimeMillis()));
        sign_time = get(R.id.sign_time);
        sign_off_time = get(R.id.sign_off_time);
        sign_in_status = get(R.id.sign_in_status);
        sign_regular = get(R.id.sign_regular);
        sign_late = get(R.id.sign_late);
        sign_regular_off = get(R.id.sign_regular_off);
        sign_leave = get(R.id.sign_leave);

        setToolBarRightTv2("统计");
        setToolBarRightTv("请假");
    }

    public void initView(AttendanceBean bean) {
        if (null == bean || bean.getStart_type() == null || Integer.parseInt(bean.getStart_type()) == 0) {
            sign_time.setText("未打卡");
            sign_in_status.setText("上班打卡");
            sign_regular.setVisibility(View.GONE);
            sign_late.setVisibility(View.GONE);
            sign_regular_off.setVisibility(View.GONE);
            sign_leave.setVisibility(View.GONE);
            //给待会打卡提供类型(上班)
            signType = 1;
        } else {
            //给待会打卡提供类型(下班)
            signType = 2;
            if (bean.getStart_time() != null)
                sign_time.setText(bean.getStart_time());
            if (bean.getStart_type() != null) {
                if (Integer.parseInt(bean.getStart_type()) == 2) {
                    sign_regular.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(bean.getStart_type()) == 1) {
                    sign_late.setVisibility(View.VISIBLE);
                }
            }
            if (bean.getEnd_time() != null) {
                sign_off_time.setText(bean.getEnd_time());
            }
            if (bean.getEnd_type() != null) {
                if (Integer.parseInt(bean.getEnd_type()) == 0) {
                    sign_off_time.setText("未打卡");
                    sign_in_status.setText("下班打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 2) {
                    sign_regular_off.setVisibility(View.VISIBLE);
                    sign_in_status.setText("更新打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 1) {
                    sign_leave.setVisibility(View.VISIBLE);
                    sign_in_status.setText("更新打卡");
                }
            } else {
                sign_off_time.setText("未打卡");
                sign_in_status.setText("下班打卡");
            }
        }
    }

    public int getSignType() {
        return signType;
    }
}
