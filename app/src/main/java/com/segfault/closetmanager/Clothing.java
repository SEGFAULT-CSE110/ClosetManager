package com.segfault.closetmanager;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Cabreros on 03-May-16.
 * This is a temporary class that contains images
 */
public class Clothing implements Parcelable{

    public static final String TOP = "Top";
    public static final String BOTTOM = "Bottom";
    public static final String ACCESSORY = "Accessory";
    public static final String SHOE = "Shoe";
    public static final String BODY = "Body";
    public static final String HAT = "Hat";
    public static final String JACKET = "Jacket";

    private boolean mWorn;

    private String mCategory;

    private String mColor;
    private String mSize;
    private List<String> mOccasion;
    private String mStyle;
    private String mWeather;
    private String mNotes; //might change implementation
    private String mSecondaryColor;

    private Bitmap mBitmap;

    public Clothing(){
        mOccasion = new ArrayList<String>();
    }

    protected Clothing(Parcel in) {
        mWorn = in.readByte() != 0;
        mCategory = in.readString();
        mColor = in.readString();
        mSize = in.readString();
        mOccasion = in.createStringArrayList();
        mStyle = in.readString();
        mWeather = in.readString();
        mNotes = in.readString();
        mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        mSecondaryColor = in.readString();
    }

    public static final Creator<Clothing> CREATOR = new Creator<Clothing>() {
        @Override
        public Clothing createFromParcel(Parcel in) {
            return new Clothing(in);
        }

        @Override
        public Clothing[] newArray(int size) {
            return new Clothing[size];
        }
    };

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public boolean isWorn() {
        return mWorn;
    }

    public void setWorn(boolean worn) {
        mWorn = worn;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public List<String> getOccasion() {
        return mOccasion;
    }

    public void setOccasion(List<String> occasion) {
        mOccasion = occasion;
    }

    public String getStyle() {
        return mStyle;
    }

    public void setStyle(String style) {
        mStyle = style;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String weather) {
        mWeather = weather;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public String getSecondaryColor() {return mSecondaryColor;}

    public void setSecondaryColor(String SecondaryColor) { mSecondaryColor = SecondaryColor; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mWorn ? 1 : 0));
        dest.writeString(mCategory);
        dest.writeString(mColor);
        dest.writeString(mSize);
        dest.writeStringList(mOccasion);
        dest.writeString(mStyle);
        dest.writeString(mWeather);
        dest.writeString(mNotes);
        dest.writeParcelable(mBitmap, flags);
        dest.writeString(mSecondaryColor);
    }
}
