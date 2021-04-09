package com.example.simplebookkeeping.db;

/*
 *
 * 描述记录对象具体内容类
 *
 * id
 * selectedImage    被选中图片id
 * money    金额
 * typeName 类型
 * remark   备注
 * time     保存时间
 *
 * 方便在数据库中进行检索
 * year     年
 * month    月
 * day      日
 *
 * kind     记录类型    支出 0    收入 1
 *
 */
public class AccountBean {

    int id;
    int selectedImage;
    float money;
    String typeName;
    String remark;
    String time;

    int year;
    int month;
    int day;

    int kind;

    public AccountBean() {
    }

    public AccountBean(int id, int selectedImage, float money, String typeName, String remark, String time, int year, int month, int day, int kind) {
        this.id = id;
        this.selectedImage = selectedImage;
        this.money = money;
        this.typeName = typeName;
        this.remark = remark;
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(int selectedImage) {
        this.selectedImage = selectedImage;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
