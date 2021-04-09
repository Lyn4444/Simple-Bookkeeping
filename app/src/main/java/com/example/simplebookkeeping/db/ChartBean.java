package com.example.simplebookkeeping.db;

/*
 *
 * Chart数据对象封装
 *
 */
public class ChartBean {

    int selectedImageId;
    String typeName;
    float ratio;
    float totalMoney;

    public ChartBean() {
    }

    public ChartBean(int selectedImageId, String typeName, float ratio, float totalMoney) {
        this.selectedImageId = selectedImageId;
        this.typeName = typeName;
        this.ratio = ratio;
        this.totalMoney = totalMoney;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }
}
