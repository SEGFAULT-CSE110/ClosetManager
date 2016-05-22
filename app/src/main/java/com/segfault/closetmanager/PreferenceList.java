package com.segfault.closetmanager;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class PreferenceList {

    private Boolean mWorn;

    private String mCategory;

    private String mColor;
    private String mSecondaryColor;
    private String mSize;
    private List<String> mOccasion;
    private String mStyle;
    private String mWeather;

    public PreferenceList(Boolean mWorn, String mCategory, String mColor, String mSize, List<String> mOccasion, String mStyle, String mWeather) {
        this.mWorn = mWorn;
        this.mCategory = mCategory;
        this.mColor = mColor;
        this.mSize = mSize;
        this.mOccasion = mOccasion;
        this.mStyle = mStyle;
        this.mWeather = mWeather;
    }

    public PreferenceList(boolean mWorn, String mCategory, String mColor, String mSize, List<String> mOccasion, String mStyle, String mWeather) {
        this(Boolean.valueOf(mWorn), mCategory, mColor, mSize, mOccasion, mStyle, mWeather);
    }

    /* Copy Constructor */
    public PreferenceList(PreferenceList pref) {
        pref.mWorn = mWorn;
        pref.mCategory = mCategory;
        pref.mColor = mColor;
        pref.mSize = mSize;
        pref.mOccasion = mOccasion;
        pref.mStyle = mStyle;
        pref.mWeather = mWeather;
    }

    /* Constructor that modifies one preference */
    public PreferenceList(PreferenceList pref, String attributeType, Object attribute) {
        this(pref);
        switch(attributeType){
            case "worn":
                mWorn = (Boolean)attribute;
                break;
            case "category":
                mCategory = (String)attribute;
                break;
            case "color":
                mColor = (String)attribute;
                break;
            case "size":
                mSize = (String)attribute;
                break;
            case "occasion":
                mOccasion = (List<String>)attribute;
                break;
            case "style":
                mStyle = (String)attribute;
                break;
            case "weather":
                mStyle = (String)attribute;
                break;
            default: break;
        }
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

    public Boolean isWorn() {
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
