package com.segfault.closetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class OutfitGenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_gen);

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.outfit_gen_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
