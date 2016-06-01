package com.segfault.closetmanager;

import android.app.Application;

/**
 * Created by Christopher Cabreros on 01-Jun-16.
 */
public class IClosetApplication extends Application {

    private static IClosetApplication singleton;
    private static Account mAccount;

    private static int counter;

    public static IClosetApplication getInstance() {
        return singleton;
    }

    public static Account getAccount(){
        return mAccount;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mAccount = new Account("AUTHORITY");

        System.err.println("ON CREATE" + (counter++));
    }

}
