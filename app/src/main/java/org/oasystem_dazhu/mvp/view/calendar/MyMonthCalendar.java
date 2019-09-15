package org.oasystem_dazhu.mvp.view.calendar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.necer.adapter.BaseCalendarAdapter;
import com.necer.adapter.MonthCalendarAdapter;
import com.necer.calendar.BaseCalendar;
import com.necer.entity.NDate;
import com.necer.listener.OnClickMonthViewListener;
import com.necer.listener.OnMonthAnimatorListener;
import com.necer.listener.OnMonthSelectListener;
import com.necer.painter.CalendarPainter;
import com.necer.utils.Attrs;
import com.necer.utils.AttrsUtil;
import com.necer.utils.Util;

import org.joda.time.LocalDate;

/**
 * Created by www on 2019/3/5.
 */

public class MyMonthCalendar extends BaseCalendar implements OnClickMonthViewListener, ValueAnimator.AnimatorUpdateListener {
    protected ValueAnimator mMonthValueAnimator;//月日历动画
    private OnMonthSelectListener mOnMonthSelectListener;
    private OnMonthAnimatorListener mOnMonthAnimatorListener;

    public MyMonthCalendar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Attrs mAttr = AttrsUtil.getAttrs(context, attributeSet);
        MyPainter painter = new MyPainter(mAttr,context);
        setCalendarPainter(painter);
    }

    @Override
    protected BaseCalendarAdapter getCalendarAdapter(Context context, Attrs attrs, LocalDate initializeDate) {
        return new MonthCalendarAdapter(context, attrs, initializeDate, this);
    }

    public MyMonthCalendar(Context context, Attrs attrs, CalendarPainter calendarPainter, int duration, OnMonthAnimatorListener onMonthAnimatorListener) {
        super(context, attrs, calendarPainter);
        this.mOnMonthAnimatorListener = onMonthAnimatorListener;
        mMonthValueAnimator = new ValueAnimator();
        mMonthValueAnimator.setDuration(duration);
        mMonthValueAnimator.addUpdateListener(this);
    }

    @Override
    protected int getTwoDateCount(LocalDate startDate, LocalDate endDate, int type) {
        return Util.getIntervalMonths(startDate, endDate);
    }

    @Override
    protected LocalDate getDate(LocalDate localDate, int count) {
        LocalDate date = localDate.plusMonths(count);
        return date;
    }

    @Override
    protected LocalDate getLastSelectDate(LocalDate currectSelectDate) {
        return currectSelectDate.plusMonths(-1);
    }

    @Override
    protected LocalDate getNextSelectDate(LocalDate currectSelectDate) {
        return currectSelectDate.plusMonths(1);
    }

    @Override
    protected void onSelcetDate(NDate nDate, boolean isClick) {
        if (mOnMonthSelectListener != null) {
            mOnMonthSelectListener.onMonthSelect(nDate, isClick);
        }
    }


    @Override
    public void onClickCurrentMonth(LocalDate localDate) {
        if (isClickDateEnable(localDate)) {
            onClickDate(localDate, 0);
        } else {
            onClickDisableDate(localDate);
        }

    }

    @Override
    public void onClickLastMonth(LocalDate localDate) {
        if (isClickDateEnable(localDate)) {
            onClickDate(localDate, -1);
        } else {
            onClickDisableDate(localDate);
        }
    }

    @Override
    public void onClickNextMonth(LocalDate localDate) {
        if (isClickDateEnable(localDate)) {
            onClickDate(localDate, 1);
        } else {
            onClickDisableDate(localDate);
        }
    }

    public void setOnMonthSelectListener(OnMonthSelectListener onMonthSelectListener) {
        this.mOnMonthSelectListener = onMonthSelectListener;
    }


    public int getMonthCalendarOffset() {
        if (mCurrView != null) {
            return mCurrView.getMonthCalendarOffset();
        }
        return 0;
    }

    public void autoToMonth() {
        float top = getY();//起始位置
        int end = 0;
        mMonthValueAnimator.setFloatValues(top, end);
        mMonthValueAnimator.start();
    }


    public void autoToMIUIWeek() {
        float top = getY();//起始位置
        int end = -getMonthCalendarOffset(); //结束位置
        mMonthValueAnimator.setFloatValues(top, end);
        mMonthValueAnimator.start();
    }

    public void autoToEMUIWeek() {
        float top = getY();//起始位置
        int end = -getHeight() * 4 / 5; //结束位置
        mMonthValueAnimator.setFloatValues(top, end);
        mMonthValueAnimator.start();
    }


    public boolean isMonthState() {
        return getY() >= 0;
    }

    public boolean isWeekState() {
        return getY() <= -getMonthCalendarOffset();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float animatedValue = (float) animation.getAnimatedValue();
        float top = getY();
        float i = animatedValue - top;
        float y = getY();
        setY(i + y);

        if (mOnMonthAnimatorListener != null) {
            //回调遵循>0向上，<0向下
            mOnMonthAnimatorListener.onMonthAnimatorChanged((int) -i);
        }
    }

}
