package com.auroraworld.toypic.list_code;

import org.json.JSONObject;

public class ListItemNotice {
    private JSONObject noticeData;
    private String noticeTitle;
    private String noticeContent;
    private String noticeDate;


    public JSONObject getNoticeData() {
        return noticeData;
    }

    public void setNoticeData(JSONObject noticeData) {
        this.noticeData = noticeData;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }
}
