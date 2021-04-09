package com.example.simplebookkeeping.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * 数据库操作管理类
 *
 * 直接调用
 *
 * 由于本项目insert数据比较少时直接使用主线程
 *
 * insert数据比较多时直接使用多线程
 *
 */
public class DBManager {

    private static SQLiteDatabase database;

    public static void initDatabase(Context context) {
//        得到DBOpenHelper对象和数据库对象
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }

    /*
     *
     * 从数据库读取typeTable的内容
     * 填充进GridView
     *
     * @Return List<TypeBean>
     */
    public static List<TypeBean> getTypeBeanList(int kind) {
        List<TypeBean> typeBeanList = new ArrayList<>();

        String sql = "select * from typeTable where kind = " + kind;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, null);
//        循环读取游标内容并存储到对象中
        while (cursor.moveToNext()) {
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"));
            int _kind = cursor.getInt(cursor.getColumnIndex("kind"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));

            TypeBean typeBean = new TypeBean(id, typeName, imageId, selectedImageId, _kind);
            typeBeanList.add(typeBean);
        }

        return typeBeanList;
    }

    /*
     *
     * 向AccountTable表插入元素
     */
    public static void insertToAccountTable(AccountBean accountBean) {

        ContentValues values = new ContentValues();
        values.put("typeName", accountBean.getTypeName());
        values.put("selectedImage", accountBean.getSelectedImage());
        values.put("remark", accountBean.getRemark());
        values.put("money", accountBean.getMoney());
        values.put("time", accountBean.getTime());
        values.put("year", accountBean.getYear());
        values.put("month", accountBean.getMonth());
        values.put("day", accountBean.getDay());
        values.put("kind", accountBean.getKind());

        System.out.println(accountBean.getRemark());
        System.out.println(accountBean.getTime());

        database.insert("accountTable", null, values);

    }

    /*
     *
     * 根据时间获取记录
     * @Params: int year, int month, int day
     *
     * 获取存储在数据库的支出和收入记录
     */
    public static List<AccountBean> getDayAccountBeanFromAccountTable(int year, int month, int day) {
        List<AccountBean> accountBeanList = new ArrayList<>();

        String sql = "select * from accountTable where year = ? and month = ? and day = ? order by id desc";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int selectedImage = cursor.getInt(cursor.getColumnIndex("selectedImage"));
            int year1 = cursor.getInt(cursor.getColumnIndex("year"));
            int month1 = cursor.getInt(cursor.getColumnIndex("month"));
            int day1 = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));

            AccountBean accountBean = new AccountBean(id, selectedImage, money, typeName, remark, time, year1, month1, day1, kind);
            accountBeanList.add(accountBean);
        }

        return accountBeanList;
    }

    /*
     *
     * 根据时间获取记录
     * @Params: int year, int month
     *
     * 获取存储在数据库的支出和收入记录
     */
    public static List<AccountBean> getDayAccountBeanFromAccountTable(int year, int month) {
        List<AccountBean> accountBeanList = new ArrayList<>();

        String sql = "select * from accountTable where year = ? and month = ? order by day asc";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int selectedImage = cursor.getInt(cursor.getColumnIndex("selectedImage"));
            int year1 = cursor.getInt(cursor.getColumnIndex("year"));
            int month1 = cursor.getInt(cursor.getColumnIndex("month"));
            int day1 = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));

            AccountBean accountBean = new AccountBean(id, selectedImage, money, typeName, remark, time, year1, month1, day1, kind);
            accountBeanList.add(accountBean);
        }

        return accountBeanList;
    }
    /*
     *
     * 获取某天的支出或收入的总金额
     */
    public static float getDaySumMoney(int year, int month, int day, int kind) {
        float total = 0.0f;

        String sql = "select sum(money) from accountTable where year = ? and month = ? and day = ? and kind = ?";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(kind)});
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }

        return total;
    }

    /*
     *
     * 获取某月的支出或收入的总金额
     */
    public static float getMonthSumMoney(int year, int month, int kind) {
        float total = 0.0f;

        String sql = "select sum(money) from accountTable where year = ? and month = ? and kind = ?";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(kind)});
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }

        return total;
    }

    /*
     *
     * 获取某月的支出或收入的记录数
     */
    public static int getMonthNumMoney(int year, int month, int kind) {
        int total = 0;

        String sql = "select count(money) from accountTable where year = ? and month = ? and kind = ?";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(kind)});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("count(money)"));
        }

        return total;
    }

    /*
     *
     * 获取某年的支出或收入的总金额
     */
    public static float getYearSumMoney(int year, int kind) {
        float total = 0.0f;

        String sql = "select sum(money) from accountTable where year = ? and kind = ?";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(kind)});
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }

        return total;
    }

    /*
     *
     * 根据Id删除accountTable表中的记录
     */
    public static int deleteFromAccountTableById(int id) {

        return database.delete("accountTable", "id = ?", new String[]{String.valueOf(id)});
    }

    /*
     *
     * 从数据库进行搜索
     *
     * 模糊搜索
     *
     * @Return List<AccountBean>
     */
    public static List<AccountBean> searchDataFromAccountTable(String search) {
        List<AccountBean> resBeanList = new ArrayList<>();

        String sql = "select * from accountTable where typeName like '%" + search + "%' or remark like '%" + search + "%'";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int selectedImage = cursor.getInt(cursor.getColumnIndex("selectedImage"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));

            AccountBean accountBean = new AccountBean(id, selectedImage, money, typeName, remark, time, year, month, day, kind);
            resBeanList.add(accountBean);
        }

        return resBeanList;
    }

    /*
     *
     * 查询指定年月的某一类型的总钱数
     *
     */
    public static List<ChartBean> getMothTotalMoney(int year, int month, int kind) {
        List<ChartBean> res = new ArrayList<>();

        float monthSumMoney = getMonthSumMoney(year, month, kind);

        String sql = "select selectedImage, typeName, sum(money) as totalMoney from accountTable where year = ? and month = ? and kind = ? group by typeName order by totalMoney desc";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(kind)});

        while (cursor.moveToNext()) {
            int selectedImage = cursor.getInt(cursor.getColumnIndex("selectedImage"));
            String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            float totalMoney = cursor.getFloat(cursor.getColumnIndex("totalMoney"));
            float ratio = totalMoney / monthSumMoney;

            res.add(new ChartBean(selectedImage, typeName, ratio, totalMoney));
        }
        return res;
    }

    public static float getMaxMoneyByDayInMonth(int year, int month, int kind) {
        String sql = "select sum(money) from accountTable where year = ? and month = ? and kind = ? group by day order by sum(money) desc";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(kind)});
        if (cursor.moveToFirst()) {
            return cursor.getFloat(cursor.getColumnIndex("sum(money)"));
        }
        return 0;
    }

    public static List<LineChartBean> getDaySumMoneyInMonth(int year, int month, int kind) {
        String sql = "select day, sum(money) from accountTable where year = ? and month = ? and kind = ? group by day order by day asc";

        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(kind)});

        List<LineChartBean> res = new ArrayList<>();

        while (cursor.moveToNext()) {
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));

            LineChartBean lineChartBean = new LineChartBean(year, month, day, money);

            res.add(lineChartBean);
        }

        return res;
    }

    public static void deleteAllInAccountTable() {
        String sql = "delete from accountTable";

        database.execSQL(sql);
    }
}
