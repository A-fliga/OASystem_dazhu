package org.oasystem_dazhu.mvp.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.utils.InitDateUtil;


/**
 * Created by www on 2019/3/25.
 */

public class AttendanceDelegate extends ViewDelegate {
    private TextView mSignTime, mSignOffTime, mSignInStatus;
    private ImageView mSignRegular, mSignLate, mSignRegularOff, mSignLeave;
    private int mSignType = 0;

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
        TextView sign_in_date = get(R.id.sign_in_date);
        sign_in_date.setText(InitDateUtil.getDate2(System.currentTimeMillis()) + "  " + InitDateUtil.getWeek());
        mSignTime = get(R.id.sign_time);
        mSignOffTime = get(R.id.sign_off_time);
        mSignInStatus = get(R.id.sign_in_status);
        mSignRegular = get(R.id.sign_regular);
        mSignLate = get(R.id.sign_late);
        mSignRegularOff = get(R.id.sign_regular_off);
        mSignLeave = get(R.id.sign_leave);

        setToolBarRightTv2("统计");
        setToolBarRightTv("请假");
    }

    public void initView(AttendanceBean bean) {
        if (null == bean || bean.getStart_type() == null || Integer.parseInt(bean.getStart_type()) == 0) {
            mSignTime.setText("未打卡");
            mSignInStatus.setText("上班打卡");
            mSignRegular.setVisibility(View.GONE);
            mSignLate.setVisibility(View.GONE);
            mSignRegularOff.setVisibility(View.GONE);
            mSignLeave.setVisibility(View.GONE);
            //给待会打卡提供类型(上班)
            mSignType = 1;
        } else {
            //给待会打卡提供类型(下班)
            mSignType = 2;
            if (bean.getStart_time() != null)
                mSignTime.setText(bean.getStart_time());
            if (bean.getStart_type() != null) {
                if (Integer.parseInt(bean.getStart_type()) == 2) {
                    mSignRegular.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(bean.getStart_type()) == 1) {
                    mSignLate.setVisibility(View.VISIBLE);
                }
            }
            if (bean.getEnd_time() != null) {
                mSignOffTime.setText(bean.getEnd_time());
            }
            if (bean.getEnd_type() != null) {
                if (Integer.parseInt(bean.getEnd_type()) == 0) {
                    mSignOffTime.setText("未打卡");
                    mSignInStatus.setText("下班打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 2) {
                    mSignRegularOff.setVisibility(View.VISIBLE);
                    mSignInStatus.setText("更新打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 1) {
                    mSignLeave.setVisibility(View.VISIBLE);
                    mSignInStatus.setText("更新打卡");
                }
            } else {
                mSignOffTime.setText("未打卡");
                mSignInStatus.setText("下班打卡");
            }
        }
    }

    public int getSignType() {
        return mSignType;
    }
}
