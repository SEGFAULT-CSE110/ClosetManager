package com.segfault.closetmanager;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class Outfit {

    private List<Clothing> mTops;
    private List<Clothing> mBottoms;
    private Clothing mShoes;
    private List<Clothing> mAccessories;
    private Clothing mSocks;
    private List<Clothing> mUnderwear;

    private String mName;
    private String mOccasion;
    private String mWeather;

    private Bitmap mBitmap;


    public void markAsWorn(){
        //mark everything as worn
    }

}
