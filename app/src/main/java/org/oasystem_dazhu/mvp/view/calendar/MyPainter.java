package org.oasystem_dazhu.mvp.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.necer.entity.NDate;
import com.necer.painter.CalendarPainter;
import com.necer.utils.Attrs;
import com.necer.utils.Util;

import org.joda.time.LocalDate;
import org.oasystem_dazhu.R;
import org.oasystem_dazhu.utils.InitDateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2019/3/5.
 */

public class MyPainter implements CalendarPainter {
    private Context context;
    private Attrs mAttrs;
    protected Paint mTextPaint;
    protected Paint mCirclePaint;

    private int noAlphaColor = 255;

    protected List<LocalDate> mHolidayList;
    protected List<LocalDate> mWorkdayList;

    private List<LocalDate> mPointList;
    private List<LocalDate> normalList;

    private List<LocalDate> lateOrLeaveList;
    private List<LocalDate> lakeList;
    private List<LocalDate> leaveList;

    public MyPainter(Attrs attrs, Context context) {
        this.mAttrs = attrs;
        this.context = context;
        mTextPaint = getPaint();
        mCirclePaint = getPaint();
        mPointList = new ArrayList<>();
        mHolidayList = new ArrayList<>();
        mWorkdayList = new ArrayList<>();
        normalList = new ArrayList<>();
        lateOrLeaveList = new ArrayList<>();
        lakeList = new ArrayList<>();
        leaveList = new ArrayList<>();
        List<String> holidayList = Util.getHolidayList();
        for (int i = 0; i < holidayList.size(); i++) {
            mHolidayList.add(new LocalDate(holidayList.get(i)));
        }
        List<String> workdayList = Util.getWorkdayList();
        for (int i = 0; i < workdayList.size(); i++) {
            mWorkdayList.add(new LocalDate(workdayList.get(i)));
        }
    }


    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }


    @Override
    public void onDrawToday(Canvas canvas, Rect rect, NDate nDate, boolean isSelect) {
        drawTodaySolar(canvas, rect, false, nDate.localDate);
        drawLunar(canvas, rect, false, noAlphaColor, nDate);
        drawPoint(canvas, rect, false, noAlphaColor, nDate.localDate);
        drawHolidays(canvas, rect, false, noAlphaColor, nDate.localDate);
    }

    @Override
    public void onDrawDisableDate(Canvas canvas, Rect rect, NDate nDate) {
        drawOtherSolar(canvas, rect, mAttrs.disabledAlphaColor, nDate.localDate, false);
        drawLunar(canvas, rect, false, mAttrs.disabledAlphaColor, nDate);
        drawPoint(canvas, rect, false, mAttrs.disabledAlphaColor, nDate.localDate);
        drawHolidays(canvas, rect, false, mAttrs.disabledAlphaColor, nDate.localDate);
    }

    @Override
    public void onDrawCurrentMonthOrWeek(Canvas canvas, Rect rect, NDate nDate, boolean isSelect) {
        drawSolidCircle(canvas, rect, nDate.localDate);
        drawOtherSolar(canvas, rect, noAlphaColor, nDate.localDate, true);
        drawLunar(canvas, rect, false, noAlphaColor, nDate);
        drawPoint(canvas, rect, false, noAlphaColor, nDate.localDate);
        drawHolidays(canvas, rect, false, noAlphaColor, nDate.localDate);
    }


    @Override
    public void onDrawNotCurrentMonth(Canvas canvas, Rect rect, NDate nDate) {
        drawOtherSolar(canvas, rect, mAttrs.alphaColor, nDate.localDate, false);
        drawLunar(canvas, rect, false, mAttrs.alphaColor, nDate);
        drawPoint(canvas, rect, false, mAttrs.alphaColor, nDate.localDate);
        drawHolidays(canvas, rect, false, mAttrs.alphaColor, nDate.localDate);
    }


    //空心圆
    private void drawHollowCircle(Canvas canvas, Rect rect) {
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mAttrs.hollowCircleStroke);
        mCirclePaint.setColor(mAttrs.hollowCircleColor);
        mCirclePaint.setAlpha(noAlphaColor);
        canvas.drawCircle(rect.centerX(), rect.centerY(), mAttrs.selectCircleRadius, mCirclePaint);
    }


    //实心圆
    private void drawSolidCircle(Canvas canvas, Rect rect, LocalDate date) {
        //节假日不管
        if (!mHolidayList.contains(date) && !InitDateUtil.isWeekend(date.toString())) {
            mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mCirclePaint.setStrokeWidth(mAttrs.hollowCircleStroke);
            //先默认缺卡，再根据有打卡记录的来更正数据
            mCirclePaint.setColor(getColor(R.color.color_E4393C));
            if (normalList.contains(date)) {
                mCirclePaint.setColor(getColor(R.color.color_03b318));
            }
            if (lateOrLeaveList.contains(date)) {
                mCirclePaint.setColor(getColor(R.color.color_f7c414));
            }
            if (lakeList.contains(date) && !InitDateUtil.isWeekend(date.toString())) {
                mCirclePaint.setColor(getColor(R.color.color_E4393C));
            }
            if (leaveList.contains(date)) {
                mCirclePaint.setColor(getColor(R.color.color_2196f3));
            }
            mCirclePaint.setAlpha(noAlphaColor);
            canvas.drawCircle(rect.centerX(), rect.centerY(), mAttrs.selectCircleRadius, mCirclePaint);
        }
    }

    public void addNormalData(List<LocalDate> normalBean) {
        normalList.addAll(normalBean);
    }

    public void addAbNormalData(List<LocalDate> abNormalBean) {
        lateOrLeaveList.addAll(abNormalBean);
    }

    public void addLakeData(List<LocalDate> lakeBean) {
        lakeList.addAll(lakeBean);
    }

    public void addLeaveData(List<LocalDate> leaveBean) {
        leaveList.addAll(leaveBean);
    }


    private int getColor(int resId) {
        return context.getResources().getColor(resId);
    }

    //今天的公历
    private void drawTodaySolar(Canvas canvas, Rect rect, boolean isSelect, LocalDate date) {
        drawSolidCircle(canvas, rect, date);
        mTextPaint.setColor(mAttrs.todaySolarTextColor);
        mTextPaint.setAlpha(noAlphaColor);
        mTextPaint.setTextSize(mAttrs.solarTextSize);
        canvas.drawText(date.getDayOfMonth() + "", rect.centerX(), mAttrs.isShowLunar ? rect.centerY() : getBaseLineY(rect), mTextPaint);
    }

    //绘制公历
    private void drawOtherSolar(Canvas canvas, Rect rect, int alphaColor, LocalDate date, Boolean isCurrentMonth) {
        if (isCurrentMonth)
            mTextPaint.setColor(mAttrs.todaySolarTextColor);
        else
            mTextPaint.setColor(mAttrs.solarTextColor);
        mTextPaint.setAlpha(alphaColor);
        mTextPaint.setTextSize(mAttrs.solarTextSize);
        canvas.drawText(date.getDayOfMonth() + "", rect.centerX(), mAttrs.isShowLunar ? rect.centerY() : getBaseLineY(rect), mTextPaint);

    }

    //绘制圆点
    private void drawPoint(Canvas canvas, Rect rect, boolean isTodaySelect, int alphaColor, LocalDate date) {
//        if (mPointList != null && mPointList.contains(date)) {
//            mCirclePaint.setStyle(Paint.Style.FILL);
//            mCirclePaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.pointColor);
//            mCirclePaint.setAlpha(alphaColor);
//            canvas.drawCircle(rect.centerX(), mAttrs.pointLocation == Attrs.DOWN ? (rect.centerY() + mAttrs.pointDistance) : (rect.centerY() - mAttrs.pointDistance), mAttrs.pointSize, mCirclePaint);
//        }
    }

    //绘制农历
    private void drawLunar(Canvas canvas, Rect rec, boolean isTodaySelect, int alphaColor, NDate nDate) {
//        if (mAttrs.isShowLunar) {
//            //优先顺序 替换的文字、农历节日、节气、公历节日、正常农历日期
//            String lunarString;
////            if (lunarString == null) {
//            if (!TextUtils.isEmpty(nDate.lunarHoliday)) {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.lunarHolidayTextColor);
//                lunarString = nDate.lunarHoliday;
//            } else if (!TextUtils.isEmpty(nDate.solarTerm)) {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.solarTermTextColor);
//                lunarString = nDate.solarTerm;
//            } else if (!TextUtils.isEmpty(nDate.solarHoliday)) {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.solarHolidayTextColor);
//                lunarString = nDate.solarHoliday;
//            } else {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.lunarTextColor);
//                lunarString = nDate.lunar.lunarDrawStr;
//            }
////            }
//
////            else {
////                Integer color = mReplaceNormalColorMap.get(nDate.localDate);
////                if (color == null) {
////                    mTextPaint.setColor(mAttrs.lunarTextColor);
////                } else {
////                    mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : color);
////                }
////            }
//            mTextPaint.setTextSize(mAttrs.lunarTextSize);
//            mTextPaint.setAlpha(alphaColor);
//            canvas.drawText(lunarString, rec.centerX(), rec.centerY() + mAttrs.lunarDistance, mTextPaint);
//        }
    }


    //绘制节假日
    private void drawHolidays(Canvas canvas, Rect rect, boolean isTodaySelect, int alphaColor, LocalDate date) {
//        if (mAttrs.isShowHoliday) {
//            int[] holidayLocation = getHolidayLocation(rect.centerX(), rect.centerY());
//            mTextPaint.setTextSize(mAttrs.holidayTextSize);
//            if (mHolidayList.contains(date)) {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.holidayColor);
//                mTextPaint.setAlpha(alphaColor);
//                canvas.drawText("休", holidayLocation[0], holidayLocation[1], mTextPaint);
//            } else if (mWorkdayList.contains(date)) {
//                mTextPaint.setColor(isTodaySelect ? mAttrs.bgCalendarColor : mAttrs.workdayColor);
//                mTextPaint.setAlpha(alphaColor);
//                canvas.drawText("班", holidayLocation[0], holidayLocation[1], mTextPaint);
//            }
//        }
    }

    //Holiday的位置
    private int[] getHolidayLocation(int centerX, int centerY) {
        int[] location = new int[2];
        int solarTexyCenterY = getSolarTextCenterY(centerY);
        switch (mAttrs.holidayLocation) {
            case Attrs.TOP_LEFT:
                location[0] = (int) (centerX - mAttrs.holidayDistance);
                location[1] = solarTexyCenterY;
                break;
            case Attrs.BOTTOM_RIGHT:
                location[0] = (int) (centerX + mAttrs.holidayDistance);
                location[1] = centerY;
                break;
            case Attrs.BOTTOM_LEFT:
                location[0] = (int) (centerX - mAttrs.holidayDistance);
                location[1] = centerY;
                break;
            case Attrs.TOP_RIGHT:
            default:
                location[0] = (int) (centerX + mAttrs.holidayDistance);
                location[1] = solarTexyCenterY;
                break;
        }
        return location;

    }


    //公历文字的竖直中心y
    private int getSolarTextCenterY(int centerY) {
        mTextPaint.setTextSize(mAttrs.solarTextSize);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int ascent = fontMetricsInt.ascent;
        int descent = fontMetricsInt.descent;
        int textCenterY = descent / 2 + centerY + ascent / 2;//文字的中心y
        return textCenterY;
    }


    private int getBaseLineY(Rect rect) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);
        return baseLineY;
    }


    //设置标记
    public void setPointList(List<String> list) {
        List<LocalDate> localDates = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                localDates.add(new LocalDate(list.get(i)));
            } catch (Exception e) {
                throw new RuntimeException("setPointList的参数需要 yyyy-MM-dd 格式的日期");
            }
        }
        mPointList.clear();
        mPointList.addAll(localDates);
    }

//    //设置正常状态的文字
//    public void setReplaceNormalStrMap(Map<String, String> replaceLunarStrMap) {
//        Map<LocalDate, String> map = new HashMap<>();
//        for (String key : replaceLunarStrMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarStrMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarStrMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        normalList.clear();
//        normalList.putAll(map);
//    }
//
//    //设置正常状态的文字颜色
//    public void setReplaceNormalColorMap(Map<String, Integer> replaceLunarColorMap) {
//        Map<LocalDate, Integer> map = new HashMap<>();
//        for (String key : replaceLunarColorMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarColorMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarColorMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        mReplaceNormalColorMap.clear();
//        mReplaceNormalColorMap.putAll(map);
//    }
//
//    //缺卡文字
//    public void setReplaceMissingStrMap(Map<String, String> replaceLunarStrMap) {
//        Map<LocalDate, String> map = new HashMap<>();
//        for (String key : replaceLunarStrMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarStrMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarStrMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        lakeList.clear();
//        lakeList.putAll(map);
//    }
//
//    //缺卡颜色
//    public void setReplaceMissingColorMap(Map<String, Integer> replaceLunarColorMap) {
//        Map<LocalDate, Integer> map = new HashMap<>();
//        for (String key : replaceLunarColorMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarColorMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarColorMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        mReplaceMissingColorMap.clear();
//        mReplaceMissingColorMap.putAll(map);
//    }
//
//    //迟到文字
//    public void setReplaceLateStrMap(Map<String, String> replaceLunarStrMap) {
//        Map<LocalDate, String> map = new HashMap<>();
//        for (String key : replaceLunarStrMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarStrMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarStrMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        lateOrLeaveList.clear();
//        lateOrLeaveList.putAll(map);
//    }
//
//    //迟到颜色
//    public void setReplaceLateColorMap(Map<String, Integer> replaceLunarColorMap) {
//        Map<LocalDate, Integer> map = new HashMap<>();
//        for (String key : replaceLunarColorMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarColorMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarColorMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        mReplaceLateColorMap.clear();
//        mReplaceLateColorMap.putAll(map);
//    }
//
//    //早退文字
//    public void setReplaceLeaveStrMap(Map<String, String> replaceLunarStrMap) {
//        Map<LocalDate, String> map = new HashMap<>();
//        for (String key : replaceLunarStrMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarStrMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarStrMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        mReplaceLeaveStrMap.clear();
//        mReplaceLeaveStrMap.putAll(map);
//    }
//
//    //早退颜色
//    public void setReplaceLeaveColorMap(Map<String, Integer> replaceLunarColorMap) {
//        Map<LocalDate, Integer> map = new HashMap<>();
//        for (String key : replaceLunarColorMap.keySet()) {
//            try {
//                map.put(new LocalDate(key), replaceLunarColorMap.get(key));
//            } catch (Exception e) {
//                throw new RuntimeException("setReplaceLunarColorMap的参数需要 yyyy-MM-dd 格式的日期");
//            }
//        }
//        mReplaceLeaveColorMap.clear();
//        mReplaceLeaveColorMap.putAll(map);
//    }


    //设置法定节假日和补班
    public void setHolidayAndWorkdayList(List<String> holidayList, List<String> workdayList) {
        mHolidayList.clear();
        mWorkdayList.clear();
        try {
            for (int i = 0; i < holidayList.size(); i++) {
                mHolidayList.add(new LocalDate(holidayList.get(i)));
            }
            for (int i = 0; i < workdayList.size(); i++) {
                mWorkdayList.add(new LocalDate(workdayList.get(i)));
            }
        } catch (Exception e) {
            throw new RuntimeException("setHolidayAndWorkdayList集合中的参数需要 yyyy-MM-dd 格式的日期");
        }

    }

}
