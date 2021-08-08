package com.example.tpictest.list_code;

import android.graphics.drawable.Drawable;

public class ListItemRankingToy {
    private String numberRank;
    private Drawable imgProduct;
    private String rankCategory;
    private String nameProduct;
    private String priceProduct;
    private String numberRate, reviewCount;


    public String getNumberRank() {
        return numberRank;
    }

    public void setNumberRank(String numberRank) {
        this.numberRank = numberRank;
    }

    public Drawable getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(Drawable imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getRankCategory() {
        return rankCategory;
    }

    public void setRankCategory(String rankCategory) {
        this.rankCategory = rankCategory;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getNumberRate() {
        return numberRate;
    }

    public void setNumberRate(String numberRate) {
        this.numberRate = numberRate;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }
}
