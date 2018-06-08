package com.freegeek.android.materialbanner.simple;

import android.support.annotation.DrawableRes;

/**
 * @author Jack Fu <rtugeek@gmail.com>
 * @date 2016/9/22
 */
public class SimpleBannerData {
    private String title;
    private int resId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getResId() {
        return resId;
    }

    public void setResId(@DrawableRes int resId) {
        this.resId = resId;
    }
}
