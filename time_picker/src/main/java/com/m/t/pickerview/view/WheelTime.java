package com.m.t.pickerview.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.m.t.pickerview.adapter.NumericWheelAdapter;
import com.m.t.pickerview.lib.WheelView;
import com.m.t.pickerview.listener.OnItemSelectedListener;
import com.time_picker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * 自定义时间选择器滚轮
 * Created by micro on 2017/2/23.
 */

public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view, parent;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_week;
    private WheelView wv_season;

    private int tabDrawableNormalId = R.drawable.bg_date_picker_btn, tabDrawableSelectId = R.drawable.bg_date_picker_btn1;

    private TextView tv_type;
    private String tabStr[] = {"按日查询","按周查询","按月查询","按季查询","按年查询"};
    public int timeType;
    public static int btnIndex = 1;
    private TextView itemDay, itemWeek, itemMonth, itemSeason, itemYear;

//    private int type;
    public static final int DEFULT_START_YEAR = 1990;
    public static final int DEFULT_END_YEAR = 2100;
    public static final int TIME_TYPE_DAY = 0;
    public static final int TIME_TYPE_WEEK = 1;
    public static final int TIME_TYPE_MONTH = 2;
    public static final int TIME_TYPE_SEASON = 3;
    public static final int TIME_TYPE_YEAR = 4;


    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private int maxWeek;
    private boolean hasTab;

    public WheelTime(View view) {
        super();
        this.view = view;
        timeType = TIME_TYPE_WEEK;
        setView(view);
    }

    public WheelTime(boolean hasTab, View view, int type, int tabDrawableNormalId, int tabDrawableSelectId,String tabStr[]) {
        super();
        this.view = view;
        this.timeType = type;
        if (type == 0) {
            this.timeType = TIME_TYPE_WEEK;
        }
        this.hasTab = hasTab;
        if (tabDrawableNormalId != 0) {
            this.tabDrawableNormalId = tabDrawableNormalId;
        }
        if (tabDrawableSelectId != 0) {
            this.tabDrawableSelectId = tabDrawableSelectId;
        }
        if(null!=tabStr&&tabStr.length>0){
            this.tabStr = tabStr;
        }
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker(int year, int month, int day, int week, int season) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        parent = view.findViewById(R.id.btnParent);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        itemDay = (TextView) view.findViewById(R.id.itemDay);
        itemWeek = (TextView) view.findViewById(R.id.itemWeek);
        itemMonth = (TextView) view.findViewById(R.id.itemMonth);
        itemSeason = (TextView) view.findViewById(R.id.itemSeason);
        itemYear = (TextView) view.findViewById(R.id.itemYear);

        itemDay.setOnClickListener(v -> setBtnView(TIME_TYPE_DAY));
        itemWeek.setOnClickListener(v -> setBtnView(TIME_TYPE_WEEK));
        itemMonth.setOnClickListener(v -> setBtnView(TIME_TYPE_MONTH));
        itemSeason.setOnClickListener(v -> setBtnView(TIME_TYPE_SEASON));
        itemYear.setOnClickListener(v -> setBtnView(TIME_TYPE_YEAR));

        Context context = view.getContext();
        // 年
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setCurrentItem(day - 1);


        wv_week = (WheelView) view.findViewById(R.id.week);
        wv_week.setAdapter(new NumericWheelAdapter(1, maxWeek, "第%d周"));
        wv_week.setCurrentItem(week - 1);

        wv_season = (WheelView) view.findViewById(R.id.season);
        wv_season.setAdapter(new NumericWheelAdapter(1, 4, "第%d季度"));
        wv_season.setCurrentItem(season);
//        LogUtils.e(getTime());
        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
//                wv_year.setCurrentItem(index);
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem = 30;
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int maxItem = 30;
                wv_month.setCurrentItem(index);
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((getYearValue() % 4 == 0 && (wv_year
                            .getCurrentItem() + startYear) % 100 != 0)
                            || getYearValue() % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
//        LogUtils.e(getTime());
        setBtnView(timeType);
    }

    public void setBtnView(int type) {
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        wv_week.setVisibility(View.VISIBLE);
        wv_month.setVisibility(View.VISIBLE);
        wv_day.setVisibility(View.VISIBLE);
        wv_season.setVisibility(View.VISIBLE);
        wv_year.setVisibility(View.VISIBLE);
        itemDay.setBackgroundResource(tabDrawableNormalId);
        itemWeek.setBackgroundResource(tabDrawableNormalId);
        itemMonth.setBackgroundResource(tabDrawableNormalId);
        itemSeason.setBackgroundResource(tabDrawableNormalId);
        itemYear.setBackgroundResource(tabDrawableNormalId);
        int textSize = 6;
        String tvTypeString = "";
        if (hasTab) {
            parent.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.GONE);
        }
        switch (type) {
            case TIME_TYPE_YEAR:
                textSize = textSize * 4;
                wv_week.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_season.setVisibility(View.GONE);
                btnIndex = 4;
                timeType = TIME_TYPE_YEAR;
                tvTypeString = tabStr[type];
                itemYear.setBackgroundResource(tabDrawableSelectId);
                break;
            case TIME_TYPE_DAY:
                textSize = textSize * 4;
                wv_week.setVisibility(View.GONE);
                wv_season.setVisibility(View.GONE);
                btnIndex = 0;
                timeType = TIME_TYPE_DAY;
                tvTypeString = tabStr[type];
                itemDay.setBackgroundResource(tabDrawableSelectId);
                break;
            case TIME_TYPE_SEASON:
                textSize = textSize * 4;
                wv_week.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                btnIndex = 3;
                tvTypeString = tabStr[type];
                timeType = TIME_TYPE_SEASON;
                itemSeason.setBackgroundResource(tabDrawableSelectId);
                break;
            case TIME_TYPE_WEEK:
                textSize = textSize * 4;
                wv_season.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                btnIndex = 1;
                timeType = TIME_TYPE_WEEK;
                tvTypeString = tabStr[type];
                itemWeek.setBackgroundResource(tabDrawableSelectId);
                break;
            case TIME_TYPE_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_week.setVisibility(View.GONE);
                wv_season.setVisibility(View.GONE);
                btnIndex = 2;
                timeType = TIME_TYPE_MONTH;
                tvTypeString = tabStr[type];
                itemMonth.setBackgroundResource(tabDrawableSelectId);
        }
        if (null != tv_type&&null!=tvTypeString&&!"".equals(tvTypeString)) {
            tv_type.setText(tvTypeString);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_week.setTextSize(textSize);
        wv_season.setTextSize(textSize);
    }

    public void setMaxWeek(int maxWeek) {
        this.maxWeek = maxWeek;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_week.setCyclic(cyclic);
        wv_season.setCyclic(cyclic);
    }

    public String getTitle() {
        StringBuffer sb = new StringBuffer();
        switch (btnIndex) {
            case 0:
                sb.append(getYearValue()).append("年")
                        .append(getMonthValue()).append("月")
                        .append(getDayValue()).append("日");
                break;
            case 1:
                sb.append(getYearValue())
                        .append(String.format("年第%s周", getWeekValue()));
                break;
            case 2:
                sb.append(getYearValue())
                        .append(String.format("年%s月", getMonthValue()));
                break;
            case 3:
                sb.append(getYearValue())
                        .append(String.format("年第%s季度", getSeasonValue()));
                break;
            case 4:
                sb.append(getYearValue());
                break;
        }
        return sb.toString();
    }

    public int getWeekValue() {
        int value = wv_week.getCurrentItem() + 1;
        if (btnIndex != 1 && wv_week.getCurrentItem() < wv_week.getInitPosition()) {
            value = wv_week.getInitPosition() + 1;
        }
        return value;
    }

    public int getSeasonValue() {
        int value = wv_season.getCurrentItem() + 1;
        if (btnIndex != 3 && wv_season.getCurrentItem() < wv_season.getInitPosition()) {
            value = wv_season.getInitPosition() + 1;
        }
        return value;
    }

    public String getValue() {
        StringBuffer sb = new StringBuffer();
        switch (btnIndex) {
            case 0:
                sb.append(getYearValue()).append("-")
                        .append(getMonthValue()).append("-")
                        .append(getDayValue());
                break;
            case 1:
                sb.append(getYearValue()).append("-")
                        .append(getWeekValue());
                break;
            case 2:
                sb.append(getYearValue()).append("-")
                        .append(getMonthValue());
                break;
            case 3:
                sb.append(getYearValue()).append("-")
                        .append(getSeasonValue());
                break;
            case 4:
                sb.append(getYearValue());
                break;
        }


        return sb.toString();
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        sb.append(getYearValue()).append("-")
                .append(getMonthValue()).append("-")
                .append(getDayValue()).append(" ")
                .append(calendar.get(Calendar.HOUR_OF_DAY)).append(":")
                .append(calendar.get(Calendar.MINUTE));
        return sb.toString();
    }

    public String getMonthValue() {
        int month = wv_month.getCurrentItem() + 1;
        if (btnIndex != 2 && wv_month.getCurrentItem() < wv_month.getInitPosition()) {
            month = wv_month.getInitPosition() + 1;
        }
//        return wv_month.getCurrentItem() + 1;
        if (month < 10) {
            return "0" + month;
        } else {
            return String.valueOf(month);
        }
    }

    public String getDayValue() {
        int day = wv_day.getCurrentItem() + 1;

        if (btnIndex != 0 && wv_day.getCurrentItem() < wv_day.getInitPosition()) {
            day = wv_day.getInitPosition() + 1;
        }

        if (day < 10) {
            return "0" + day;
        } else {
            return String.valueOf(day);
        }
//        return wv_day.getCurrentItem() + 1;
    }

    public int getYearValue() {
        return wv_year.getCurrentItem() + startYear;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
