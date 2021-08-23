package com.example.tpictest.list_code;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.tpictest.R;

public class ListItemChildInfo {
    private int itemCount;
    private boolean isAddAction1, isAddAction2, isAddAction3;
    private Drawable drawableAddChild;
    private String userId;
    private String childOrder1, childOrder2, childOrder3;
    private String childNick1, childNick2, childNick3;


    public Drawable getDrawableAddChild() {
        return drawableAddChild;
    }

    public void setDrawableAddChild(Drawable drawableAddChild) {
        this.drawableAddChild = drawableAddChild;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public boolean isAddAction1() {
        return isAddAction1;
    }

    public void setAddAction1(boolean addAction1) {
        isAddAction1 = addAction1;
    }

    public boolean isAddAction2() {
        return isAddAction2;
    }

    public void setAddAction2(boolean addAction2) {
        isAddAction2 = addAction2;
    }

    public boolean isAddAction3() {
        return isAddAction3;
    }

    public void setAddAction3(boolean addAction3) {
        isAddAction3 = addAction3;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChildOrder1() {
        return childOrder1;
    }

    public void setChildOrder1(String childOrder1) {
        this.childOrder1 = childOrder1;
    }

    public String getChildOrder2() {
        return childOrder2;
    }

    public void setChildOrder2(String childOrder2) {
        this.childOrder2 = childOrder2;
    }

    public String getChildOrder3() {
        return childOrder3;
    }

    public void setChildOrder3(String childOrder3) {
        this.childOrder3 = childOrder3;
    }

    public String getChildNick1() {
        return childNick1;
    }

    public void setChildNick1(String childNick1) {
        this.childNick1 = childNick1;
    }

    public String getChildNick2() {
        return childNick2;
    }

    public void setChildNick2(String childNick2) {
        this.childNick2 = childNick2;
    }

    public String getChildNick3() {
        return childNick3;
    }

    public void setChildNick3(String childNick3) {
        this.childNick3 = childNick3;
    }
}
