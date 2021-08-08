package com.example.tpictest.list_code;

import android.graphics.drawable.Drawable;

public class ListItemReviewToy {
    private Drawable imgProduct;
    private String numberRate, numberLike;
    private String commentReview;
    private String urlProduct;

    public Drawable getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(Drawable imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getNumberRate() {
        return numberRate;
    }

    public void setNumberRate(String numberRate) {
        this.numberRate = numberRate;
    }

    public String getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(String numberLike) {
        this.numberLike = numberLike;
    }

    public String getCommentReview() {
        return commentReview;
    }

    public void setCommentReview(String commentReview) {
        this.commentReview = commentReview;
    }

    public String getUrlProduct() {
        return urlProduct;
    }

    public void setUrlProduct(String urlProduct) {
        this.urlProduct = urlProduct;
    }
}
