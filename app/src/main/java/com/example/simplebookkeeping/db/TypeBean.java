package com.example.simplebookkeeping.db;

/*
 *
 * typeName 类型名称
 * imageId  未被选中图片id
 * selectedImageId  被选中图片id
 * kind 支出 0    收入 1
 *
 */

public class TypeBean {

    private int id;
    private String typeName;
    private int imageId;
    private int selectedImageId;
    private int kind;

    public TypeBean() {
    }

    public TypeBean(int id, String typeName, int imageId, int selectedImageId, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.imageId = imageId;
        this.selectedImageId = selectedImageId;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSelectedImageId() {
        return selectedImageId;
    }

    public void setSelectedImageId(int selectedImageId) {
        this.selectedImageId = selectedImageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
