package com.example.simplebookkeeping.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.simplebookkeeping.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "bookkeeping.db", null, 1);
    }

    //    项目第一次运行时调用，创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table typeTable(id integer primary key autoincrement, typeName varchar(10), imageId integer, selectedImageId integer, kind integer)";
        db.execSQL(sql);

        insertType(db);

        sql = "create table accountTable (id integer primary key autoincrement, typeName varchar(10), selectedImage integer, remark varchar(255), money float, time varchar(255), year integer, month integer, day integer, kind integer)";
        db.execSQL(sql);

    }

    private void insertType(SQLiteDatabase db) {
        String sql = "insert into typeTable (typeName, imageId, selectedImageId, kind) values (?, ?, ?, ?)";
        db.execSQL(sql, new Object[]{"餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0});
        db.execSQL(sql, new Object[]{"学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0});
        db.execSQL(sql, new Object[]{"住房", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0});
        db.execSQL(sql, new Object[]{"娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0});
        db.execSQL(sql, new Object[]{"通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0});
        db.execSQL(sql, new Object[]{"烟酒", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0});
        db.execSQL(sql, new Object[]{"医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0});
        db.execSQL(sql, new Object[]{"交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0});
        db.execSQL(sql, new Object[]{"零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0});
        db.execSQL(sql, new Object[]{"送礼", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0});
        db.execSQL(sql, new Object[]{"购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0});
        db.execSQL(sql, new Object[]{"服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0});
        db.execSQL(sql, new Object[]{"日用", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0});
        db.execSQL(sql, new Object[]{"水电", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0});
        db.execSQL(sql, new Object[]{"其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0});

        db.execSQL(sql, new Object[]{"薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1});
        db.execSQL(sql, new Object[]{"奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1});
        db.execSQL(sql, new Object[]{"利息", R.mipmap.in_lixifuji, R.mipmap.in_lixifuji_fs, 1});
        db.execSQL(sql, new Object[]{"借债", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1});
        db.execSQL(sql, new Object[]{"收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1});
        db.execSQL(sql, new Object[]{"投资", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1});
        db.execSQL(sql, new Object[]{"二手", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1});
        db.execSQL(sql, new Object[]{"其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1});

    }

    //    数据库版本更新时发生变化时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
