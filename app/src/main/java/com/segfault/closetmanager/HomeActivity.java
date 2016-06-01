package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * THIS IS THE ACTIVITY THAT RUNS
 */
public class HomeActivity extends BaseActivity {

    private LinearLayout mClosetButton;
    private LinearLayout mOutfitCreatorButton;
    private LinearLayout mLookbookButton;
    private static boolean mLoaded = false;
    private boolean backButtonPressed;
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // set pref_layout toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //assign buttons
        mClosetButton = (LinearLayout) findViewById(R.id.closet_layout);
        mOutfitCreatorButton = (LinearLayout) findViewById(R.id.outfitgen_layout);
        mLookbookButton = (LinearLayout) findViewById(R.id.lookbook_layout);

        //load items
        //TODO: why does it run onCreate every time we start the activity? is this an android thing?


    }

    @Override
    protected void onStart() {
        super.onStart();
        backButtonPressed = false;
    }

    /**
     * Button method to go to closet
     *
     * @param view - view that called this?
     */
    public void goToCloset(View view) {
        Intent intent = new Intent(this, ClosetActivity.class);
        startActivity(intent);
    }

    public void goToOutfitGenerator(View view) {
        Intent intent = new Intent(this, OutfitGenActivity.class);
        startActivity(intent);
    }

    public void goToLookbook(View view) {
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }


    public void onMorePressed(View view) {
    }

    @Override
    /**
     * Defines closing the app through the home activity
     */
    public void onBackPressed() {
        //leave app if backButton was pressed twice
        if (!backButtonPressed) {
            backButtonPressed = true;
            Toast newToast = Toast.makeText(this, "Press the back button again to leave.",
                    Toast.LENGTH_SHORT);
            newToast.show();
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }


}

