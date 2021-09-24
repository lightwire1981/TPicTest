package com.auroraworld.toypic.list_code;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ListItemLikeGoods {
    private JSONObject item;
    private String goodsId;
    private String goodsName;
    private String goodsPrice;
    private String goodsImgUrl;
    private String goodsRate;
    private String goodsLike;


    public JSONObject getItem() {
        return item;
    }

    public void setItem(JSONObject item) {
        this.item = item;

        setGoodsId();
        setGoodsName();
        setGoodsPrice();
        setGoodsImgUrl();
        setGoodsRate();
        setGoodsLike();
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId() {
        try {
            this.goodsId = item.getString("goodsNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName() {
        try {
            this.goodsName = item.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice() {
        try {
            int value = (int)Float.parseFloat(item.getString("fixedPrice"));
            DecimalFormat decimalFormat = new DecimalFormat("###,###");
            String price = decimalFormat.format(value);
            this.goodsPrice = price + "원";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl() {
        try {
            this.goodsImgUrl = item.getString("detailImageData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGoodsRate() {
        return goodsRate;
    }

    public void setGoodsRate() {
        try {
            String temp = item.getString("evaluate_avg");
            if(temp.equals("null")) {
                temp = "3.0";
            }
            this.goodsRate = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGoodsLike() {
        return goodsLike;
    }

    public void setGoodsLike() {
        try {
            String temp = item.getString("goods_like");
            if(temp.equals("null")) {
                temp = "0";
            }
            this.goodsLike = temp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsLike(String goodsLike) {
        this.goodsLike = goodsLike;
    }
}
