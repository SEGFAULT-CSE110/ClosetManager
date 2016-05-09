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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * THIS IS THE ACTIVITY THAT RUNS
 */
public class HomeActivity extends AppCompatActivity {

    private Button mClosetButton;
    private Button mOutfitCreatorButton;
    private Button mLookbookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //assign buttons
        mClosetButton = (Button) findViewById(R.id.closet_button);
        mOutfitCreatorButton = (Button) findViewById(R.id.outfit_creator_button);
        mLookbookButton = (Button) findViewById(R.id.lookbook_button);

        //load items
        Account.currentAccountInstance = new Account("AUTH TOKEN");
        try {
            loadPictures(getApplicationContext(), Account.currentAccountInstance.getCloset().getList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TESTING_CLASS tester = new TESTING_CLASS();
        tester.testMethod();
    }


    /**
     * Button method to go to closet
     * @param view
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
                Bitmap bitmap = BitmapFactory.decodeStream(istr);

                //separate into types
                if (file.contains("hat")) {
                    Clothing newHat = new Clothing();
                    newHat.setBitmap(bitmap);
                    newHat.setCategory(Clothing.HAT);
                    clothingList.add(newHat);
                } else if (file.contains("pants")) {
                    Clothing newPants = new Clothing();
                    newPants.setBitmap(bitmap);
                    newPants.setCategory(Clothing.BOTTOM);
                    clothingList.add(newPants);
                } else if (file.contains("shirt")) {
                    Clothing newShirt = new Clothing();
                    newShirt.setBitmap(bitmap);
                    newShirt.setCategory(Clothing.TOP);
                    clothingList.add(newShirt);
                } else if (file.contains("shoes")) {
                    Clothing newShoes = new Clothing();
                    newShoes.setBitmap(bitmap);
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
