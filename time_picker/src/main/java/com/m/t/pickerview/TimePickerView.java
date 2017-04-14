package com.m.t.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m.t.pickerview.lib.PickerViewUtil;
import com.m.t.pickerview.view.BasePickerView;
import com.m.t.pickerview.view.WheelTime;
import com.time_picker.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 自定义时间选择器
 * Created by micro on 2017/2/23.
 */

public class TimePickerView extends BasePickerView implements View.OnClickListener {

    public int mWeek, mSeason;
    private OnTimeOutListener outListener;
    WheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private TimePickerView.OnTimeSelectListener timeSelectListener;
    private boolean hasTab;//是否有选择类型按钮,true表示有，false的话隐藏，只显示年
    private Context mContext;
    private int tabDrawableNormalId, tabDrawableSelectId;
    private int rootViewId = R.layout.time_picker_view;
    private String tabStr[] = null;//选项类型tab的值，无值，取默认，注意日周月季年的顺序

    /**
     * @param context
     * @param date    默认选中时间，为空表示当前系统时间
     * @param range   时间滚轮年份与当前时间上下浮动值
     */
    public TimePickerView(Context context, Date date, int range) {
        super(context);
        initView(context, hasTab, date, -1, 0, 0, range);
    }

    /**
     * @param context
     * @param date    默认选中时间，为空表示当前系统时间
     * @param range   时间滚轮年份与当前时间上下浮动值
     * @param type    默认选择时间类型
     */
    public TimePickerView(Context context, Date date, int type, int range) {
        super(context);
        initView(context, hasTab, date, -1, 0, type, range);
    }

    /**
     * @param context
     * @param hasTab  是否显示事件类型选择tab
     * @param date    默认选中时间，为空表示当前系统时间
     * @param range   时间滚轮年份与当前时间上下浮动值
     * @param type    默认选择时间类型
     */
    public TimePickerView(Context context, boolean hasTab, Date date, int type, int range) {
        super(context);
        initView(context, hasTab, date, type, 0, 0, range);
    }

    /**
     * @param context
     * @param hasTab  是否显示事件类型选择tab
     * @param date    默认选中时间，为空表示当前系统时间
     * @param range   时间滚轮年份与当前时间上下浮动值
     * @param type    默认选择时间类型
     * @param mWeek   默认选中第几周
     * @param mSeason 默认选中第几季度
     */
    public TimePickerView(Context context, boolean hasTab, Date date, int type, int mWeek, int mSeason, int range) {
        super(context);
        initView(context, hasTab, date, type, mWeek, mSeason, range);
    }

    /**
     * @param context
     * @param hasTab  是否显示事件类型选择tab
     * @param date    默认选中时间，为空表示当前系统时间
     * @param range   时间滚轮年份与当前时间上下浮动值
     * @param type    默认选择时间类型
     * @param mWeek   默认选中第几周
     * @param mSeason 默认选中第几季度
     */
    protected void initView(Context context, boolean hasTab, Date date, int type, int mWeek, int mSeason, int range) {
        mContext = context;
        this.hasTab = hasTab;
        LayoutInflater.from(context).inflate(rootViewId, contentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        if (null != btnCancel) {
            btnCancel.setTag(TAG_CANCEL);
            btnCancel.setOnClickListener(this);
        }
        if (null != btnSubmit) {
            btnSubmit.setTag(TAG_SUBMIT);
            btnSubmit.setOnClickListener(this);
        }
        // ----时间转轮
        final View timePickerView = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(hasTab, timePickerView, type, tabDrawableNormalId, tabDrawableSelectId, tabStr);

        if (0 == range) {
            range = 50;
        }
        //当前时间
        Calendar calendar = Calendar.getInstance();
        //控制时间范围
        setRange(calendar.get(Calendar.YEAR) - range, calendar.get(Calendar.YEAR) + range);

        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = null == date ? calendar.get(Calendar.MONTH) + 1 : calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        LogUtils.e("mWeek:"+mWeek+",mSeason:"+mSeason);
        int week = mWeek > 0 ? mWeek : PickerViewUtil.getWeekOfYear(date);
//        LogUtils.e("year:" + year);
        int season = mSeason > 0 ? mSeason - 1 : month / 3;
//        LogUtils.i(year + "_" + season + "_" + month + "_" + day + "_" + week);
        wheelTime.setMaxWeek(calendar.getActualMaximum(Calendar.WEEK_OF_YEAR));
        wheelTime.setPicker(year, month, day, week, season);
    }

    /**
     * 设置可以选择的时间范围
     *
     * @param startYear
     * @param endYear
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date, int mWeek, int mSeason) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = null == date ? calendar.get(Calendar.MONTH) + 1 : calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        LogUtils.e("mWeek:"+mWeek+",mSeason:"+mSeason);
        int week = mWeek > 0 ? mWeek : PickerViewUtil.getWeekOfYear(date);
//        LogUtils.e("year:" + year);
        int season = mSeason > 0 ? mSeason - 1 : month / 3;
//        LogUtils.i(year + "_" + season + "_" + month + "_" + day + "_" + week);
        wheelTime.setMaxWeek(calendar.getActualMaximum(Calendar.WEEK_OF_YEAR));
        wheelTime.setPicker(year, month, day, week, season);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int week = PickerViewUtil.getWeekOfYear(null);
                    int season = month / 3;
                    if (season * 3 < month) {
                        season = season + 1;
                    }

                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(date);
                    int year1 = calendar1.get(Calendar.YEAR);
                    int month1 = calendar1.get(Calendar.MONTH) + 1;
                    int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
                    int week1 = wheelTime.getWeekValue();

                    int season1 = wheelTime.getSeasonValue();

                    boolean isOut = false;
//                    LogUtils.e("mWeek:" + mWeek + ",mSeason:" + mSeason);
                    switch (wheelTime.timeType) {
                        case WheelTime.TIME_TYPE_YEAR:
                            if (year1 > year) {
                                isOut = true;
                            }
                            break;
                        case WheelTime.TIME_TYPE_SEASON:
                            mSeason = season1;
                            if (year1 > year) {
                                isOut = true;
                            } else if (year1 == year && season1 > season) {
                                isOut = true;
                            }
                            break;
                        case WheelTime.TIME_TYPE_MONTH:
                            if (year1 > year) {
                                isOut = true;
                            } else if (year1 == year && month1 > month) {
                                isOut = true;
                            }
                            break;
                        case WheelTime.TIME_TYPE_DAY:
                            if (year1 > year) {
                                isOut = true;
                            } else if (year1 == year && month1 > month) {
                                isOut = true;
                            } else if (year1 == year && month1 == month && day1 > day) {
                                isOut = true;
                            }
                            break;
                        case WheelTime.TIME_TYPE_WEEK:
                            mWeek = week1;
                            if (year1 > year) {
                                isOut = true;
                            } else if (year1 == year && week1 > week) {
                                isOut = true;
                            }
                            break;
                    }
                    if (isOut) {
                        showTimeOutDialog();
                    } else {
                        timeSelectListener.onTimeSelect(date, wheelTime.timeType, wheelTime.getTitle(), wheelTime.getValue());
                        dismiss();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showTimeOutDialog() {
        if (null == outListener) {
            outListener.onTimeOut();
        } else {
            Toast.makeText(mContext, "所选时间不能大于当前时间！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置选中时间超过当前时间事件
     *
     * @param outListener
     */
    public void setTimeOutListener(OnTimeOutListener outListener) {
        this.outListener = outListener;
    }

    /**
     * 选中时间超过当前时间事件监听
     */
    public interface OnTimeOutListener {
        public void onTimeOut();
    }

    public interface OnTimeSelectListener {
        /**
         * @param date0 选择的时间
         * @param type  选择时间类型
         * @param title
         * @param value
         */
        public void onTimeSelect(Date date0, int type, String title, String value);
    }

    public void setOnTimeSelectListener(TimePickerView.OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setBtnView(int type) {
        wheelTime.setBtnView(type);
    }

    /**
     * 设置类型选项默认样式
     * @param tabDrawableNormalId
     */
    public void setTabDrawableNormalId(int tabDrawableNormalId) {
        this.tabDrawableNormalId = tabDrawableNormalId;
    }

    /**
     * 设置类型选项被选中样式
     * @param tabDrawableSelectId
     */
    public void setTabDrawableSelectId(int tabDrawableSelectId) {
        this.tabDrawableSelectId = tabDrawableSelectId;
    }

    /**
     * 自定义根布局，注意布局的控件必须要与默认一致
     * @param rootViewId
     */
    public void setRootViewId(int rootViewId) {
        this.rootViewId = rootViewId;
    }

    /**
     * 设置类型选项的内容，注意顺序
     * @param tabStr
     */
    public void setTabStr(String[] tabStr) {
        this.tabStr = tabStr;
    }
}
