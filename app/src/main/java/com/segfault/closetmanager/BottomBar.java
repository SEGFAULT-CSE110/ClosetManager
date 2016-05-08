package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class BottomBar {

    private Activity mActivity;
    private View mBottomBarView;

    public BottomBar(Activity activity){
        mActivity = activity;
        mBottomBarView = activity.findViewById(R.id.bottom_bar_linear_layout);
    }

}
