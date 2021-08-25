package com.wellstech.tpictest.list_code;

import android.graphics.drawable.Drawable;

public class ListItemCustomToy {
    private Drawable imgDrawable;
    private String productName;
    private String predictNumber;
    private boolean isFavorite;
    private String imgUrl;

    public void setImgDrawable(Drawable img) {
        imgDrawable = img;
    }

    public void setProductName(String pName) {
        productName = pName;
    }

    public void setPredictNumber(String pNumber) {
        predictNumber = pNumber;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public String getProductName() {
        return productName;
    }

    public String getPredictNumber() {
        return predictNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

}
