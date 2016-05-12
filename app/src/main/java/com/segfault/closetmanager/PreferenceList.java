package com.segfault.closetmanager;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class PreferenceList {

    //someone make this
    private boolean mWorn;

    private String mCategory;

    private String mColor;
    private String mSize;
    private List<String> mOccasion;
    private String mStyle;
    private String mWeather;
    private String mNotes;

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

    public String getNotes() {
        return mNotes;
    }
}
