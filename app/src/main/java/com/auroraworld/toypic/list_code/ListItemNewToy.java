package com.auroraworld.toypic.list_code;

import org.json.JSONException;
import org.json.JSONObject;

public class ListItemNewToy {
    private JSONObject item1, item2;
    private String goodsId1, goodsId2;
    private String goodsName1, goodsName2;
    private String goodsImgUrl1, goodsImgUrl2;


    public JSONObject getItem1() {
        return item1;
    }

    public void setItem1(JSONObject item1) {
        this.item1 = item1;
        setGoodsId1();
        setGoodsName1();
        setGoodsImgUrl1();
    }

    public JSONObject getItem2() {
        return item2;
    }

    public void setItem2(JSONObject item2) {
        this.item2 = item2;
        setGoodsId2();
        setGoodsName2();
        setGoodsImgUrl2();
    }


    public String getGoodsId1() {
        return goodsId1;
    }

    public void setGoodsId1() {
        try {
            this.goodsId1 = item1.getString("goodsNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsId1(String goodsId1) {
        this.goodsId1 = goodsId1;
    }

    public String getGoodsId2() {
        return goodsId2;
    }

    public void setGoodsId2() {
        try {
            this.goodsId2 = item2.getString("goodsNo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsId2(String goodsId2) {
        this.goodsId2 = goodsId2;
    }

    public String getGoodsName1() {
        return goodsName1;
    }

    public void setGoodsName1() {
        try {
            this.goodsName1 = item1.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsName1(String goodsName1) {
        this.goodsName1 = goodsName1;
    }

    public String getGoodsName2() {
        return goodsName2;
    }

    public void setGoodsName2() {
        try {
            this.goodsName2 = item2.getString("goodsNm");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsName2(String goodsName2) {
        this.goodsName2 = goodsName2;
    }

    public String getGoodsImgUrl1() {
        return goodsImgUrl1;
    }

    public void setGoodsImgUrl1() {
        try {
            this.goodsImgUrl1 = item1.getString("detailImageData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsImgUrl1(String goodsImgUrl1) {
        this.goodsImgUrl1 = goodsImgUrl1;
    }

    public String getGoodsImgUrl2() {
        return goodsImgUrl2;
    }

    public void setGoodsImgUrl2() {
        try {
            this.goodsImgUrl2 = item2.getString("detailImageData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setGoodsImgUrl2(String goodsImgUrl2) {
        this.goodsImgUrl2 = goodsImgUrl2;
    }


}
