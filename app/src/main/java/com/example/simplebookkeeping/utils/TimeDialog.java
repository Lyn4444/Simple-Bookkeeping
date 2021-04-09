package com.example.simplebookkeeping.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.example.simplebookkeeping.R;

public class TimeDialog extends Dialog implements View.OnClickListener {

    DatePicker datePicker;
    Button cancelBtn, ensureBtn;
    OnEnsureListener onEnsureListener;
    public int year, month, day;
    public String time;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public TimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_dialog);

        datePicker = findViewById(R.id.time_dialog_date);
        cancelBtn = findViewById(R.id.time_dialog_cancel);
        ensureBtn = findViewById(R.id.time_dialog_ensure);

        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
//        setDatePicker();
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_dialog_cancel:
                cancel();
                break;
            case R.id.time_dialog_ensure:
                this.year = datePicker.getYear();
                this.month = datePicker.getMonth() + 1;
                this.day = datePicker.getDayOfMonth();
                String monthStr = String.valueOf(month);
                String dayStr = String.valueOf(day);
                if (month < 10) {
                    monthStr = "0" + monthStr;
                }
                if (day < 10) {
                    dayStr = "0" + dayStr;
                }
                this.time = String.valueOf(year) + "/" + monthStr + "/" + dayStr;
//                接口回调
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                cancel();
                break;
        }
    }

    /*
     *
     * 这是去处DatePicker的头部
     */
    private void setDatePicker() {
        ViewGroup viewGroup = (ViewGroup) datePicker.getChildAt(0);
        if (viewGroup == null) {
            return;
        }
        View headerView = viewGroup.getChildAt(0);
        if (headerView == null) {
            return;
        }
//        int id = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
//        if (id == headerView.getId()) {
//            headerView.setVisibility(View.GONE);
//            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
//            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            viewGroup.setLayoutParams(layoutParams);
//            ViewGroup animator = (ViewGroup) viewGroup.getChildAt(1);
//            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
//            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            animator.setLayoutParams(layoutParamsAnimator);
//            View child = animator.getChildAt(0);
//            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
//            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            child.setLayoutParams(layoutParamsChild);
//            return;
//        }
        int id = getContext().getResources().getIdentifier("date_picker_header", "id", "android");
        if (id == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }
}
