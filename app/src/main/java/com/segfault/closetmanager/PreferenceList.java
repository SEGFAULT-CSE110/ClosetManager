package com.segfault.closetmanager;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class PreferenceList {

    private boolean mWorn;

    private String mCategory;

    private String mColor;
    private String mSize;
    private List<String> mOccasion;
    private String mStyle;
    private String mWeather;

    public PreferenceList(boolean mWorn, String mCategory, String mColor, String mSize, List<String> mOccasion, String mStyle, String mWeather) {
        this.mWorn = mWorn;
        this.mCategory = mCategory;
        this.mColor = mColor;
        this.mSize = mSize;
        this.mOccasion = mOccasion;
        this.mStyle = mStyle;
        this.mWeather = mWeather;
    }

    // TODO: constructor that modifies one preference
    public PreferenceList(PreferenceList pref, boolean mWorn, String mCategory, String mColor, String mSize, List<String> mOccasion, String mStyle, String mWeather) {
    }

    public PreferenceList(Clothing clothing) {
        this.mWorn = clothing.isWorn();
        this.mCategory = clothing.getCategory();
        this.mColor = clothing.getColor();
        this.mSize = clothing.getSize();
        this.mOccasion = clothing.getOccasion();
        this.mStyle = clothing.getStyle();
        this.mWeather = clothing.getWeather();
    }

    public boolean isWorn() {
        return mWorn;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getColor() {
        return mColor;
    }

    public String getSize() {
        return mSize;
    }

    public List<String> getOccasion() {
        return mOccasion;
    }

    public String getStyle() {
        return mStyle;
    }

    public String getWeather() {
        return mWeather;
    }

}
