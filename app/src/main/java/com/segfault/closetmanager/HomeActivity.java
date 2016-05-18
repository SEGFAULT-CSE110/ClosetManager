package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * THIS IS THE ACTIVITY THAT RUNS
 */
public class HomeActivity extends AppCompatActivity {

    private LinearLayout mClosetButton;
    private LinearLayout mOutfitCreatorButton;
    private LinearLayout mLookbookButton;
    private static boolean mLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //assign buttons
        mClosetButton = (LinearLayout) findViewById(R.id.closet_layout);
        mOutfitCreatorButton = (LinearLayout) findViewById(R.id.outfitgen_layout);
        mLookbookButton = (LinearLayout) findViewById(R.id.lookbook_layout);

        //load items
        //TODO: why does it run onCreate every time we start the activity? is this an android thing?
        try {
            if (!mLoaded) {
                Account.currentAccountInstance = new Account("AUTH TOKEN");
                loadPictures(getApplicationContext(), Account.currentAccountInstance.getCloset().getList());
                mLoaded = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TESTING_CLASS tester = new TESTING_CLASS();
        tester.testMethod();
    }


    /**
     * Button method to go to closet
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

    public void goToSettings(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public void loadPictures(Context context, List<Clothing> clothingList) throws IOException{
        //get root directory
        String root = "images/";
        System.out.println(root);

        //Create an asset manager
        AssetManager assetManager = context.getAssets();
        //Create a list of all the file names in the folder 'images'
        String[] files = assetManager.list("images");

        for (String file : files) {
            if (file.contains(".jpg")) {
                InputStream istr = assetManager.open(root + file);
                Bitmap firstBitmap = BitmapFactory.decodeStream(istr);

                //scale down first bitmap
                final float densityMultiplier = context.getResources().getDisplayMetrics().density;
                int h= (int) (50 * densityMultiplier); //TODO revise size
                int w= (int) (h * firstBitmap.getWidth()/((double) firstBitmap.getHeight()));
                Bitmap secondBitmap = Bitmap.createScaledBitmap(firstBitmap, w, h, true);

                //Recycle the bitmap to preserve memory
                firstBitmap.recycle();

                //separate into types
                if (file.contains("hat")) {
                    Clothing newHat = new Clothing();
                    newHat.setBitmap(secondBitmap);
                    newHat.setCategory(Clothing.HAT);
                    clothingList.add(newHat);
                } else if (file.contains("pants")) {
                    Clothing newPants = new Clothing();
                    newPants.setBitmap(secondBitmap);
                    newPants.setCategory(Clothing.BOTTOM);
                    clothingList.add(newPants);
                } else if (file.contains("shirt")) {
                    Clothing newShirt = new Clothing();
                    newShirt.setBitmap(secondBitmap);
                    newShirt.setCategory(Clothing.TOP);
                    clothingList.add(newShirt);
                } else if (file.contains("shoes")) {
                    Clothing newShoes = new Clothing();
                    newShoes.setBitmap(secondBitmap);
                    newShoes.setCategory(Clothing.SHOE);
                    clothingList.add(newShoes);
                }

                istr.close();
                System.out.println("Loaded " + file);
            }
        }
        return;
    }

}
