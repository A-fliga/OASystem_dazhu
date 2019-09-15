package org.oasystem_dazhu.mvp.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.listener.OnYearMonthChangedListener;

import org.oasystem_dazhu.R;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.view.baseDelegate.ViewDelegate;
import org.oasystem_dazhu.mvp.view.calendar.MyMonthCalendar;
import org.oasystem_dazhu.mvp.view.calendar.MyPainter;
import org.oasystem_dazhu.utils.InitDateUtil;

/**
 * Created by www on 2019/3/25.
 */

public class AttendanceStatisticsDelegate extends ViewDelegate {
    private MyMonthCalendar mCalendar;
    private MyPainter mMyPainter;
    private TextView mSignTime, mSignOffTime;
    private ImageView mSignRegular, mSignLate, mSignRegularOff, mSignLeave;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_attendance_statistics;
    }

    @Override
    public void initWidget() {
        mSignTime = get(R.id.sign_time);
        mSignOffTime = get(R.id.sign_off_time);
        mSignRegular = get(R.id.sign_regular);
        mSignLate = get(R.id.sign_late);
        mSignRegularOff = get(R.id.sign_regular_off);
        mSignLeave = get(R.id.sign_leave);
    }


    public MyMonthCalendar initCalendar() {
        mCalendar = get(R.id.monthCalendar);
        mCalendar.setOnYearMonthChangeListener(new OnYearMonthChangedListener() {
            @Override
            public void onYearMonthChanged(BaseCalendar baseCalendar, int year, int month, boolean isClick) {
                getTitleView().setText(year + "年" + month + "月");
            }
        });
        mCalendar.setDateInterval(InitDateUtil.getLastMonth(3) + "-01", InitDateUtil.ConverToString(InitDateUtil.getTime()));
        mMyPainter = (MyPainter) mCalendar.getCalendarPainter();
        return mCalendar;
    }

    public MyPainter getMyPainter() {
        return mMyPainter;
    }

    public MyMonthCalendar getCalendar() {
        return mCalendar;
    }


    public void setDate(String date) {
        TextView attendanceStatisticsDate = get(R.id.attendanceStatisticsDate);
        attendanceStatisticsDate.setText(date);
    }

    public void initView(AttendanceBean bean) {
        mSignRegular.setVisibility(View.GONE);
        mSignLate.setVisibility(View.GONE);
        mSignRegularOff.setVisibility(View.GONE);
        mSignLeave.setVisibility(View.GONE);
        if (null == bean || bean.getStart_type() == null || Integer.parseInt(bean.getStart_type()) == 0) {
            mSignTime.setText("未打卡");
            mSignOffTime.setText("");
        } else {
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
            else {
                mSignOffTime.setText("");
            }
            if (bean.getEnd_type() != null) {
                if (Integer.parseInt(bean.getEnd_type()) == 0) {
                    mSignOffTime.setText("未打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 2) {
                    mSignRegularOff.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(bean.getEnd_type()) == 1) {
                    mSignLeave.setVisibility(View.VISIBLE);
                }
            } else {
                mSignOffTime.setText("未打卡");
            }
        }
    }
}
