package com.example.simplebookkeeping.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.simplebookkeeping.R;

public class RemarkDialog extends Dialog implements View.OnClickListener {

    EditText remarkEditText;
    Button cancelButton, ensureButton;
    OnEnsureListener onEnsureListener;

    //    设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remark_dialog);
        remarkEditText = findViewById(R.id.remark_dialog_edit);
        cancelButton = findViewById(R.id.remark_dialog_btn_cancel);
        ensureButton = findViewById(R.id.remark_dialog_btn_ensure);
        cancelButton.setOnClickListener(this);
        ensureButton.setOnClickListener(this);
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remark_dialog_btn_cancel:
                cancel();
                break;
            case R.id.remark_dialog_btn_ensure:
//                接口回调
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

    /*
     *
     * 获取已输入的备注并显示
     */
    public void setEditText(String editText) {
        if (!TextUtils.isEmpty(editText)) {
            remarkEditText.setText(editText);
        }
    }

    /*
     *
     * 获取输入的备注
     */
    public String getEditText() {
        return remarkEditText.getText().toString().trim();
    }

    /*
     *
     * 设置dialog大小样式
     */
    public void setRemarkDialog() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = (int) display.getWidth() * 3 / 4;
        attributes.gravity = Gravity.CENTER;
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(attributes);
    }

}
