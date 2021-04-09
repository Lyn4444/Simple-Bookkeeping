package com.example.simplebookkeeping.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simplebookkeeping.AboutActivity;
import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.db.DBManager;

import java.util.Objects;

/**
 * The Setting Fragment Of Main Fragment.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    TextView clearText, aboutText;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);


        initView(view);


        return view;
    }


    private void initView(View view) {

        clearText = view.findViewById(R.id.setting_clear);
        aboutText = view.findViewById(R.id.setting_about);

        clearText.setOnClickListener(this);
        aboutText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_clear:
                showDeleteDialog();
                break;
            case R.id.setting_about:
                goToAboutActivity();
                break;
        }
    }

    private void goToAboutActivity() {
        Intent intent = new Intent(getContext(), AboutActivity.class);
        startActivity(intent);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));

        builder.setTitle("提示").setMessage("确定删除所有记录？\n该操作无法回溯，记录无法找回！")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        删除数据库中所有记录
                        DBManager.deleteAllInAccountTable();
                    }
                });
//        显示Dialog
        builder.create().show();
    }
}