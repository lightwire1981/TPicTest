package com.wellstech.tpictest.list_code;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemReviewToy {
    private Drawable imgProduct;
    private String numberRate, numberLike;
    private String commentReview;
    private String urlImg1;
    private String reviewerName;
    private String date;
    private String photoCount;

    private JSONObject item;

    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;
        setUrlImg1();
        setNumberRate();
        setNumberLike();
        setReviewerName();
        setDate();
        setPhotoCount();
        setCommentReview();
    }

    public String getUrlImg1() {
        return urlImg1;
    }

    public void setUrlImg1() {
        try {
            this.urlImg1 = item.getString("review_img1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUrlImg1(String urlImg1) {
        this.urlImg1 = urlImg1;
    }

    public String getNumberRate() {
        return numberRate;
    }

    public void setNumberRate() {
        try {
            this.numberRate = item.getString("goods_evaluate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNumberRate(String numberRate) {
        this.numberRate = numberRate;
    }

    public String getNumberLike() {
        return numberLike;
    }

    public void setNumberLike() {
        try {
            this.numberLike = item.getString("like_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNumberLike(String numberLike) {
        this.numberLike = numberLike;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName() {
        try {
            this.reviewerName = item.getString("user_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        try {
            String dateValue = item.getString("u_date");
            if (dateValue.isEmpty() || dateValue.equals("null")) {
                dateValue = item.getString("c_date");
            }
            String[] temp = dateValue.substring(0,10).split("-");
            this.date = temp[0]+"."+temp[1]+"."+temp[2];
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount() {
        try {
            this.photoCount = item.getString("count_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public String getCommentReview() {
        return commentReview;
    }

    public void setCommentReview() {
        try {
            this.commentReview = item.getString("review");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setCommentReview(String commentReview) {
        this.commentReview = commentReview;
    }


    public Drawable getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(Drawable imgProduct) {
        this.imgProduct = imgProduct;
    }
}
