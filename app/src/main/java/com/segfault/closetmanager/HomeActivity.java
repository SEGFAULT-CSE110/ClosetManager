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
    private Closet mCurrentCloset;
    SharedPreferences mPrefs;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        mPrefs = getPreferences(MODE_PRIVATE);

        // Load closet
        mCurrentCloset = Account.currentAccountInstance.getCloset();

        // set pref_layout toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //assign buttons
        mClosetButton = (LinearLayout) findViewById(R.id.closet_layout);
        mOutfitCreatorButton = (LinearLayout) findViewById(R.id.outfitgen_layout);
        mLookbookButton = (LinearLayout) findViewById(R.id.lookbook_layout);

        //load items
        //TODO: why does it run onCreate every time we start the activity? is this an android thing?
        try {
            if (!mLoaded) {
                Account.currentAccountInstance = new Account("AUTH TOKEN");
                loadPictures(getApplicationContext(), mCurrentCloset.getList(), mCurrentCloset.getId());
                Account.currentAccountInstance.getLookbook().assignBelongingCloset(Account.currentAccountInstance.getCloset());
                mLoaded = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void loadPictures(Context context, List<Clothing> clothingList, List<String> id) throws IOException {
        //Create an asset manager
        AssetManager assetManager = context.getAssets();
        //Create a list of all the file names in the folder 'images'
        String[] files = assetManager.list("images");

        for (int i = 0; i < id.size() - 1; i++) {
            if (id.get(i).contains(".jpg") || id.get(i).contains(".png")) {
                InputStream istr = assetManager.open(id.get(i));
                Bitmap firstBitmap = BitmapFactory.decodeStream(istr);

                //scale down first bitmap
                final float densityMultiplier = context.getResources().getDisplayMetrics().density;
                int h = (int) (50 * densityMultiplier); //TODO revise size
                int w = (int) (h * firstBitmap.getWidth() / ((double) firstBitmap.getHeight()));
                Bitmap secondBitmap = Bitmap.createScaledBitmap(firstBitmap, w, h, true);

                //Recycle the bitmap to preserve memory
                firstBitmap.recycle();

                String json = mPrefs.getString(id.get(i), "");
                Clothing currClothing = gson.fromJson(json, Clothing.class);

                if (currClothing.getCategory() == "Hat") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Bottom") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Top") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Shoe") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                }

                istr.close();
                System.out.println("Loaded");
            }
        }
    }
}

