package com.segfault.closetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class LookbookActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook);

        //Create and set the code for the bottom bar
        View bottomBarView = findViewById(R.id.lookbook_bottom_bar);
        BottomBar bottomBar = new BottomBar(bottomBarView, this);
    }
}
