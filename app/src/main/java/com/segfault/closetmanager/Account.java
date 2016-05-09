package com.segfault.closetmanager;

import java.io.Serializable;

/**
 * Created by Christopher Cabreros on 08-May-16.
 */
public class Account{

    private String mAuthToken;
    private Closet mCloset;
    private Lookbook mLookbook;
    public static Account currentAccountInstance;

    public Account(String authToken) {
        mAuthToken = authToken;
        mCloset = new Closet();
        mLookbook = new Lookbook();
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public Closet getCloset() {
        return mCloset;
    }

    public void setCloset(Closet closet) {
        mCloset = closet;
    }

    public Lookbook getLookbook() {
        return mLookbook;
    }

    public void setLookbook(Lookbook lookbook) {
        mLookbook = lookbook;
    }
}
