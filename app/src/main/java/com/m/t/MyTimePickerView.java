package com.m.t;

import android.content.Context;

import com.m.t.pickerview.TimePickerView;

import java.util.Date;

/**
 * 深度自定义的话，扩展例子
 * Created by micro on 2017/4/14.
 */

public class MyTimePickerView extends TimePickerView {

    public MyTimePickerView(Context context, Date date, int range) {
        super(context, date, range);
    }

    public MyTimePickerView(Context context, Date date, int type, int range) {
        super(context, date, type, range);
    }

    public MyTimePickerView(Context context, boolean hasTab, Date date, int type, int range) {
        super(context, hasTab, date, type, range);
    }

    public MyTimePickerView(Context context, boolean hasTab, Date date, int type, int mWeek, int mSeason, int range) {
        super(context, hasTab, date, type, mWeek, mSeason, range);
    }

    @Override
    protected void initView(Context context, boolean hasTab, Date date, int type, int mWeek, int mSeason, int range) {
        //自定义
        setRootViewId(R.layout.time_picker_view_t);
        setTabDrawableNormalId(R.drawable.bg_date_picker_btn);
        setTabDrawableSelectId(R.drawable.bg_date_picker_btn1);
        super.initView(context, hasTab, date, type, mWeek, mSeason, range);
    }
}
