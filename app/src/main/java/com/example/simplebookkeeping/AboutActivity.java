package com.example.simplebookkeeping;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView returnView;
    TextView versionView;

    String versionName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        returnView = findViewById(R.id.about_return);
        versionView = findViewById(R.id.about_version);

        versionName = getVersionName(this);

        versionView.setText("Version " + versionName);


        returnView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.about_return) {
            finish();
        }
    }


    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;

        try {
            PackageManager manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }
}