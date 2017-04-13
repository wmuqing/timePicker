package com.m.t.pickerview.lib;

import android.graphics.Color;
import android.view.Gravity;

import com.m.t.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by micro on 17/1/19.
 */
public class PickerViewUtil {
    private static final int INVALID = -1;
    /**
     * Get default animation resource when not defined by the user
     *
     * @param gravity       the gravity of the dialog
     * @param isInAnimation determine if is in or out animation. true when is is
     * @return the id of the animation resource
     */
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
        }
        return INVALID;
    }
    /**
     * 获取某年的周数
     *
     * @param year
     * @return
     */
    public static int getWeekNum(int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year); // 2010年
        c.set(Calendar.MONTH, 5); // 6 月
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
        return c.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }
    /**
     * 获取time为第几周，传null获取当前系统时间周数
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        if (null == date) {
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
        calendar.setFirstDayOfWeek(Calendar.MONDAY);// 设置每周的第一天为星期一
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
        calendar.setMinimalDaysInFirstWeek(1);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
