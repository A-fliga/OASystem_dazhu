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
    private MyMonthCalendar calendar;
    private MyPainter myPainter;
    private TextView sign_time, sign_off_time;
    private ImageView sign_regular, sign_late, sign_regular_off, sign_leave;

    @Override
    public void onDestroy() {

    }

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_attendance_statistics;
    }

    @Override
    public void initWidget() {
        sign_time = get(R.id.sign_time);
        sign_off_time = get(R.id.sign_off_time);
        sign_regular = get(R.id.sign_regular);
        sign_late = get(R.id.sign_late);
        sign_regular_off = get(R.id.sign_regular_off);
        sign_leave = get(R.id.sign_leave);
    }


    public MyMonthCalendar initCalendar() {
        calendar = get(R.id.monthCalendar);
        calendar.setOnYearMonthChangeListener(new OnYearMonthChangedListener() {
            @Override
            public void onYearMonthChanged(BaseCalendar baseCalendar, int year, int month, boolean isClick) {
                getTitleView().setText(year + "年" + month + "月");
            }
        });
        calendar.setDateInterval(InitDateUtil.getLastMonth(3) + "-01", InitDateUtil.ConverToString(InitDateUtil.getTime()));
        myPainter = (MyPainter) calendar.getCalendarPainter();
        return calendar;
    }

    public MyPainter getMyPainter() {
        return myPainter;
    }

    public MyMonthCalendar getCalendar() {
        return calendar;
    }


    public void setDate(String date) {
        TextView attendanceStatisticsDate = get(R.id.attendanceStatisticsDate);
        attendanceStatisticsDate.setText(date);
    }

    public void initView(AttendanceBean bean) {
        sign_regular.setVisibility(View.GONE);
        sign_late.setVisibility(View.GONE);
        sign_regular_off.setVisibility(View.GONE);
        sign_leave.setVisibility(View.GONE);
        if (null == bean || bean.getStart_type() == null || Integer.parseInt(bean.getStart_type()) == 0) {
            sign_time.setText("未打卡");
            sign_off_time.setText("");
        } else {
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
            else {
                sign_off_time.setText("");
            }
            if (bean.getEnd_type() != null) {
                if (Integer.parseInt(bean.getEnd_type()) == 0) {
                    sign_off_time.setText("未打卡");
                }
                if (Integer.parseInt(bean.getEnd_type()) == 2) {
                    sign_regular_off.setVisibility(View.VISIBLE);
                }
                if (Integer.parseInt(bean.getEnd_type()) == 1) {
                    sign_leave.setVisibility(View.VISIBLE);
                }
            } else {
                sign_off_time.setText("未打卡");
            }
        }
    }
}
