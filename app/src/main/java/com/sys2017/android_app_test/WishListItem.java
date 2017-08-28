package com.sys2017.android_app_test;

/**
 * Created by N552VW on 2017-08-28.
 */

public class WishListItem {

    String img;
    String wish;
    String date;
    String memo;

    public WishListItem(String img, String wish, String date, String memo) {
        this.img = img;
        this.wish = wish;
        this.date = date;
        this.memo = memo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
