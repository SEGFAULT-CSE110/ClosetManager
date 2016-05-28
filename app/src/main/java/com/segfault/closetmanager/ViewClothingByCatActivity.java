package com.segfault.closetmanager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines the activity when user decides to view clothing by category.
 */
public class ViewClothingByCatActivity extends FragmentActivity{

    private List<Clothing> mClothingList;
    private Account mCurrentAccount = Account.currentAccountInstance;
    private Closet mCurrentCloset = mCurrentAccount.getCloset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing_by_cat);

        //Get the extra preference
        Intent previousIntent = getIntent();
        PreferenceList getPreference;
        //Check if it actually exists
        if (previousIntent.hasExtra(PreferenceList.EXTRA_STRING)) {
            getPreference = (PreferenceList) getIntent().
                    getSerializableExtra(PreferenceList.EXTRA_STRING);
        } else {
            getPreference = new PreferenceList(false, null, null, null, null, null, null, null);
        };
        //Get the correct list
        mClothingList = mCurrentCloset.filter(getPreference);

        GridImageAdapter theAdapter = new GridImageAdapter(this, mClothingList);
        GridView theListView = (GridView) findViewById(R.id.gridView);
        theListView.setAdapter(theAdapter);

        //TODO: if list size is 0, then add in another view

        //catch any clicks
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bitmapSelectedText = "You selected " + position;
            }
        });

    }


    /**
     * Image Adapter class for clothing
     */
    private class GridImageAdapter extends ArrayAdapter<Clothing>{

        public GridImageAdapter(Context context, List<Clothing> clothingList) {
            super(context, R.layout.clothing_image_fragment, clothingList);
        }

        @Override
        /**
         * convert view is a reference to other reusable views
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            //Recycling views
            View view;
            if (convertView != null){
                view = convertView;
            } else{
                //Get the correct view to inflate
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.clothing_image_fragment, parent, false);
            }

            //Get the desired bitmap
            Clothing currentClothing = getItem(position);
            Bitmap currentBitmap = currentClothing.getBitmap();

            //Set the image view to have the bitmap
            ImageView imageView = (ImageView) view.findViewById(R.id.clothing_image_view);
            imageView.setImageBitmap(currentBitmap);

            return view;
        }

    }

}//end class viewByCategoryActivity
