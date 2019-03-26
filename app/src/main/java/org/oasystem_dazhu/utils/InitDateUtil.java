package org.oasystem_dazhu.utils;

import android.text.format.DateFormat;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by www on 2017/11/17.
 */

public class InitDateUtil {
    //初始化日期
    public static void initDate(TextView dateTV) {
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日", sysTime);
        dateTV.setText(sysTimeStr);
    }

    //初始化日期
    public static CharSequence getDate(long time) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", time);
    }

    //初始化时钟
    public static CharSequence getTime(long time) {
        return DateFormat.format("HH:mm", time);
    }

    //初始化日期
    public static CharSequence getDate2(long time) {
        return DateFormat.format("yyyy-MM-dd", time);
    }


    //初始化时钟
    public static String initClock(TextView clockTv) {
        long sysTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(sysTime);
        String dateStr = simpleDateFormat.format(date);
        if (clockTv != null)
            clockTv.setText(dateStr);
        return dateStr;
    }


    //初始化时钟
    public static String initHours(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    //得到月份
    public static String getMonth(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    public static String getLastMonth(int length) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -length);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(c.getTime());
    }

    //初始化时钟
    public static String initMin(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    // 日期转毫秒 
    public static String getMillionSeconds(String date) {
        long millionSeconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            millionSeconds = sdf.parse(date).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(millionSeconds);
    }

    public static Date ConverToDate(String strDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String ConverToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //向前走一天
        return calendar.getTime();
    }

    public static boolean isWeekend(String date) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bDate = null;
        try {
            bDate = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(bDate);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
}
