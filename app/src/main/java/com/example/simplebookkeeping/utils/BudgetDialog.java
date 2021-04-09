package com.example.simplebookkeeping.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.simplebookkeeping.R;

public class BudgetDialog extends Dialog implements View.OnClickListener {

    EditText budgetEditText;
    Button cancelButton, ensureButton;
    OnEnsureListener onEnsureListener;

    //    设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.budget_dialog);
        budgetEditText = findViewById(R.id.budget_dialog_edit);
        cancelButton = findViewById(R.id.budget_dialog_btn_cancel);
        ensureButton = findViewById(R.id.budget_dialog_btn_ensure);
        cancelButton.setOnClickListener(this);
        ensureButton.setOnClickListener(this);
    }

    public interface OnEnsureListener {
        public void onEnsure(float budget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.budget_dialog_btn_cancel:
                cancel();
                break;
            case R.id.budget_dialog_btn_ensure:
                String budgetStr = budgetEditText.getText().toString();
                if (TextUtils.isEmpty(budgetStr)) {
                    Toast.makeText(getContext(), "设置预算不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                float budget = Float.parseFloat(budgetStr);
                if ((budget <= 0)) {
                    Toast.makeText(getContext(), "设置预算要不小于0", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure(budget);
                }
                cancel();
                break;
        }
    }


    /*
     *
     * 获取已输入的备注并显示
     */
    public void setEditText(String editText) {
        if (!TextUtils.isEmpty(editText)) {
            budgetEditText.setText(editText);
        }
    }

    /*
     *
     * 获取输入的备注
     */
    public String getEditText() {
        return budgetEditText.getText().toString().trim();
    }

    /*
     *
     * 设置dialog大小样式
     */
    public void setBudgetDialog() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = (int) display.getWidth() * 3 / 4;
        attributes.gravity = Gravity.CENTER;
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(attributes);
    }

}
