package org.oasystem_dazhu.mvp.presenter.activity;

import android.os.Bundle;

import com.necer.entity.NDate;
import com.necer.listener.OnMonthSelectListener;

import org.joda.time.LocalDate;
import org.oasystem_dazhu.http.MSubscribe;
import org.oasystem_dazhu.mvp.model.BaseEntity;
import org.oasystem_dazhu.mvp.model.PublicModel;
import org.oasystem_dazhu.mvp.model.bean.AttendanceBean;
import org.oasystem_dazhu.mvp.model.bean.AttendanceStatisticsBean;
import org.oasystem_dazhu.mvp.view.AttendanceStatisticsDelegate;
import org.oasystem_dazhu.mvp.view.calendar.MyMonthCalendar;
import org.oasystem_dazhu.utils.InitDateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by www on 2019/3/25.
 */

public class AttendanceStatisticsActivity extends ActivityPresenter<AttendanceStatisticsDelegate> {
    private MyMonthCalendar calendar;
    private LocalDate selectedDate;

    @Override
    public Class<AttendanceStatisticsDelegate> getDelegateClass() {
        return AttendanceStatisticsDelegate.class;
    }

    @Override
    public boolean isSetDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAttendanceBean();
        calendar = viewDelegate.initCalendar();
        setOnCalendarItemClickListener(calendar);
        selectedDate = new LocalDate(InitDateUtil.getTime());
        viewDelegate.setDate(selectedDate.toString());
        getAttendanceDetail(selectedDate);
    }

    private void getAttendanceDetail(LocalDate selectedDate) {
        PublicModel.getInstance().getAttendanceInfo(new MSubscribe<BaseEntity<AttendanceBean>>() {
            @Override
            public void onNext(BaseEntity<AttendanceBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0) {
                    viewDelegate.initView(bean.getData());
                }
            }
        }, selectedDate.toString());
    }

    private void setOnCalendarItemClickListener(MyMonthCalendar calendar) {
        calendar.setOnMonthSelectListener(new OnMonthSelectListener() {
            @Override
            public void onMonthSelect(NDate date, boolean isClick) {
                if (isClick && !selectedDate.toString().equals(date.localDate.toString())) {
                    selectedDate = date.localDate;
                    viewDelegate.setDate(date.localDate.toString());
                    getAttendanceDetail(date.localDate);
                }
            }
        });
    }

    private void getAttendanceBean() {
        PublicModel.getInstance().getAttendanceStatistics(new MSubscribe<BaseEntity<AttendanceStatisticsBean>>() {
            @Override
            public void onNext(BaseEntity<AttendanceStatisticsBean> bean) {
                super.onNext(bean);
                if (bean.getCode() == 0)
                    classifyBean(bean.getData());
            }
        }, InitDateUtil.getLastMonth(3) + "-01", InitDateUtil.ConverToString(InitDateUtil.getTime()));
    }

    private void classifyBean(AttendanceStatisticsBean data) {
        List<LocalDate> normalBean = new ArrayList<>();
        List<LocalDate> lateOrLeaveBean = new ArrayList<>();
        List<LocalDate> lakeBean = new ArrayList<>();
        List<LocalDate> ask_leaveBean = new ArrayList<>();
        List<AttendanceStatisticsBean.AttendanceRecordBean> recordBean = new ArrayList<>();
        recordBean.addAll(data.getAttendance_record());
        List<AttendanceStatisticsBean.AuthenticateBean> leaveBean = new ArrayList<>();
        leaveBean.addAll(data.getAuthenticate());

        for (int i = 0; i < recordBean.size(); i++) {
            if (recordBean.get(i).getStart_type() != null && recordBean.get(i).getEnd_type() != null) {
                //只有早晚都是正常 才是正常
                if (Integer.parseInt(recordBean.get(i).getStart_type()) == 2 &&
                        Integer.parseInt(recordBean.get(i).getEnd_type()) == 2) {
                    normalBean.add(new LocalDate(recordBean.get(i).getDate()));
                }
                //只要有一个不正常 当天状态就不正常
                if (Integer.parseInt(recordBean.get(i).getStart_type()) == 1 ||
                        Integer.parseInt(recordBean.get(i).getEnd_type()) == 1) {
                    lateOrLeaveBean.add(new LocalDate(recordBean.get(i).getDate()));
                }
                //只要有一个缺卡 当天状态就有缺卡
                if (Integer.parseInt(recordBean.get(i).getStart_type()) == 0 ||
                        Integer.parseInt(recordBean.get(i).getEnd_type()) == 0) {
                    lakeBean.add(new LocalDate(recordBean.get(i).getDate()));
                }
            }
        }
        for (int i = 0; i < leaveBean.size(); i++) {
            List<Date> betweenDate = getMonthBetweenDate(InitDateUtil.ConverToDate(leaveBean.get(i).getStart_time()),
                    InitDateUtil.ConverToDate(leaveBean.get(i).getEnd_time()));
            if (betweenDate != null) {
                for (int j = 0; j < betweenDate.size(); j++) {
                    ask_leaveBean.add(new LocalDate(InitDateUtil.ConverToString(betweenDate.get(j))));
                }
            }
        }
        viewDelegate.getMyPainter().addNormalData(normalBean);
        viewDelegate.getMyPainter().addAbNormalData(lateOrLeaveBean);
        viewDelegate.getMyPainter().addLakeData(lakeBean);
        viewDelegate.getMyPainter().addLeaveData(ask_leaveBean);
        viewDelegate.getCalendar().notifyAllView();
    }

    public static List<Date> getMonthBetweenDate(Date beginDate, Date endDate) {
        if (beginDate.getTime() == endDate.getTime()) {
            return null;
        }
        List<Date> lDate = new ArrayList<>();
        lDate.add(beginDate);//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);//把结束时间加入集合
        return lDate;
    }

}
