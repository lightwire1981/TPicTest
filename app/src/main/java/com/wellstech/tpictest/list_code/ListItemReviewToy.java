package com.wellstech.tpictest.list_code;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemReviewToy {
    private Drawable imgProduct;
    private String numberRate, numberLike;
    private String commentReview;
    private String urlImg1, urlImg2, urlImg3, urlImg4, urlImg5;
    private String reviewerName;
    private String date;
    private String photoCount;

    private JSONObject item;

    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;
        setNumberRate();
        setNumberLike();
        setReviewerName();
        setDate();
        setPhotoCount();
        setCommentReview();
        switch (Integer.parseInt(photoCount)) {
            case 5:
                setUrlImg5();
            case 4:
                setUrlImg4();
            case 3:
                setUrlImg3();
            case 2:
                setUrlImg2();
            case 1:
                setUrlImg1();
                break;
        }
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

    public String getUrlImg2() {
        return urlImg2;
    }
    public void setUrlImg2() {
        try {
            this.urlImg2 = item.getString("review_img2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUrlImg2(String urlImg2) {
        this.urlImg2 = urlImg2;
    }

    public String getUrlImg3() {
        return urlImg3;
    }
    public void setUrlImg3() {
        try {
            this.urlImg3 = item.getString("review_img3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUrlImg3(String urlImg3) {
        this.urlImg3 = urlImg3;
    }

    public String getUrlImg4() {
        return urlImg4;
    }
    public void setUrlImg4() {
        try {
            this.urlImg4 = item.getString("review_img4");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUrlImg4(String urlImg4) {
        this.urlImg4 = urlImg4;
    }

    public String getUrlImg5() {
        return urlImg5;
    }
    public void setUrlImg5() {
        try {
            this.urlImg5 = item.getString("review_img5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setUrlImg5(String urlImg5) {
        this.urlImg5 = urlImg5;
    }
}
