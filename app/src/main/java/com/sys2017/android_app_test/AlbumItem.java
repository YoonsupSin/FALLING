package com.sys2017.android_app_test;

import java.io.Serializable;

/**
 * Created by N552VW on 2017-08-16.
 */

public class AlbumItem implements Serializable {

    String img;
    String memo;
    String date;

    public AlbumItem(String imgUrl, String memo, String date) {
        this.img = imgUrl;
        this.memo = memo;
        this.date = date;
    }

    public String getImgUrl() {
        return img;
    }

    public void setImgUrl(String imgUrl) {
        this.img = imgUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
