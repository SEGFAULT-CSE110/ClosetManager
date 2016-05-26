package com.segfault.closetmanager;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class Outfit{

    private List<Clothing> mTops = new ArrayList<>();
    private List<Clothing> mBottoms = new ArrayList<>();
    private Clothing mShoes;
    private List<Clothing> mAccessories = new ArrayList<>();
    private Clothing mSocks;
    private List<Clothing> mUnderwear = new ArrayList<>();
    private Clothing mHat;

    private String mName;
    private String mOccasion;
    private String mWeather;

    private Bitmap mBitmap;



    public void markAsWorn(){
        //mark everything as worn
    }

    public void addAccessory(Clothing clothing){
        mAccessories.add(clothing);
    }

    public void addTop(Clothing clothing){
        mTops.add(clothing);
    }

    public void addBottom(Clothing clothing){
        mBottoms.add(clothing);
    }

    public void setHat(Clothing clothing) { mHat = clothing;}

    public void setShoes(Clothing clothing){
        mShoes = clothing;
    }

    public Clothing getHat() {return mHat;}

    public Clothing getFirstTop(){
        return mTops.get(0);
    }

    public Clothing getFirstBottom(){
        return mBottoms.get(0);
    }

    public Clothing getFirstAccessory(){
        return mAccessories.get(0);
    }

    public Clothing getShoes(){
        return mShoes;
    }

    public List<Clothing> getTops() {
        return mTops;
    }

    public List<Clothing> getBottoms() {
        return mBottoms;
    }

    public List<Clothing> getAccessories() {
        return mAccessories;
    }
}
