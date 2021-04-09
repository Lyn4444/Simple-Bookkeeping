package com.example.simplebookkeeping.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.example.simplebookkeeping.R;

import java.lang.reflect.Field;


public class CalenderDialog extends Dialog implements View.OnClickListener  {

    DatePicker datePicker;
    Button cancelBtn, ensureBtn;
    OnEnsureListener onEnsureListener;
    public int year, month;

    public interface OnEnsureListener {
        public void onEnsure();
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public CalenderDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_dialog);

        datePicker = findViewById(R.id.calender_dialog_date);
        cancelBtn = findViewById(R.id.calender_dialog_cancel);
        ensureBtn = findViewById(R.id.calender_dialog_ensure);

        setDatePicker();

        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);

    }

    private void setDatePicker() {
        try {
            /*
             *
             * 处理android5.0以上的特殊情况
             *
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = datePicker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerFields = datePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerFields) {
                    if ("daySpinner".equals(datePickerField.getName()) || ("dayPicker").equals(datePickerField.getName())) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerField.get(datePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calender_dialog_cancel:
                cancel();
                break;
            case R.id.calender_dialog_ensure:
                this.year = datePicker.getYear();
                this.month = datePicker.getMonth() + 1;
                String monthStr = String.valueOf(month);
                if (month < 10) {
                    monthStr = "0" + monthStr;
                }
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                cancel();
                break;
        }
    }

    public void setCalenderDialog() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = (int) display.getWidth() * 3 / 4;
        attributes.gravity = Gravity.CENTER;
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(attributes);
    }
}
