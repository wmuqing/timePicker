package com.m.t;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.m.t.pickerview.TimePickerView;
import com.m.t.pickerview.view.WheelTime;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    public String mTitle;
    public String date = null;
    public Date dateTime;
    public int mWeek;
    public int mSeason;
    private int queryType = WheelTime.TIME_TYPE_WEEK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTestClick();
            }
        });
    }

    MyTimePickerView timePickerView;

    void onTestClick() {
        if (null == dateTime) {
            dateTime = new Date();
        }
        if (null == timePickerView) {
            //时间选择器
            timePickerView = new MyTimePickerView(mContext, true, dateTime, queryType, mWeek, mSeason, 0);

            timePickerView.setCyclic(false);
            timePickerView.setCancelable(true);
            //时间选择后回调
            timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

                @Override
                public void onTimeSelect(Date date0, int type, String title, String value) {
                    dateTime = date0;
                    date = value;
                    mTitle = title;
                    queryType = type;
                    mWeek = timePickerView.mWeek;
                    mSeason = timePickerView.mSeason;
                }
            });
        }
        if (timePickerView.isShowing()) {
            timePickerView.dismiss();
        } else {
            timePickerView.setTime(dateTime, mWeek, mSeason);
            timePickerView.show();
        }

    }
//    TimePickerView timePickerView;
//
//    void onTestClick() {
//        if (null == dateTime) {
//            dateTime = new Date();
//        }
//        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//
//        //时间选择器
//        timePickerView = new TimePickerView(mContext, true, dateTime, queryType, mWeek, mSeason,0);
//
//        timePickerView.setCyclic(false);
//        timePickerView.setCancelable(true);
//        //时间选择后回调
//        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//            @Override
//            public void onTimeSelect(Date date0, int type, String title, String value) {
//                dateTime = date0;
//                date = value;
//                mTitle = title;
//                queryType = type;
//                mWeek = timePickerView.mWeek;
//                mSeason = timePickerView.mSeason;
//            }
//        });
//        if (timePickerView.isShowing()) {
//            timePickerView.dismiss();
//        } else {
//            timePickerView.setTime(dateTime, mWeek, mSeason);
//            timePickerView.show();
//        }
//
//    }
}
