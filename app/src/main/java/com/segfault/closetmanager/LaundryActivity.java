package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the activity when user decides to view clothing by category.
 */
public class LaundryActivity extends BaseActivity {

    private List<Clothing> mLaundryList;
    private Account mCurrentAccount = IClosetApplication.getAccount();
    private Closet mCurrentCloset = mCurrentAccount.getCloset();
    private GridImageAdapter theAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing_by_cat);

        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Recreate bottom bar and listener
        View bottomBarView = findViewById(R.id.view_clothing_by_cat_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Create essential references. this is needed for onItemClick
        final Activity currentActivity = this;

        //Get the correct list
        mLaundryList = mCurrentCloset.filter(new PreferenceList(true, null, null, null, null, null, null, null));
        theAdapter = new GridImageAdapter(this, mLaundryList);
        GridView theListView = (GridView) findViewById(R.id.gridView);
        if (theListView != null) { //requires nullptr check
            theListView.setAdapter(theAdapter);
            //catch any clicks
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(currentActivity, ViewClothingActivity.class);
                    intent.putExtra("Clothing", mLaundryList.get(position));
                    currentActivity.finish();
                    currentActivity.startActivity(intent);

                }
            });
        }

        //TODO: if list size is 0, then add in another view to show that we have no laundry
    }

    /**
     * Does laundry when the view is pressed
     * @param view - do nothing with this
     */
    public void doLaundry(View view) {

        //do nothing if the laundry list is empty
        if (mLaundryList.isEmpty()){
            Toast newToast = Toast.makeText(this, "There is no laundry to clean.",
                    Toast.LENGTH_SHORT);
            newToast.show();
        } else{
            for (int index = 0; index < mLaundryList.size(); index++){
                mLaundryList.get(index).setWorn(false);
                mLaundryList = mCurrentCloset.filter(new PreferenceList(true, null, null, null, null, null, null, null));
            }

            //notify
            theAdapter.notifyDataSetChanged();

            //signify to user that we have done laundry
            Toast newToast = Toast.makeText(this, "Marked all clothing as clean (not worn).",
                    Toast.LENGTH_SHORT);
            newToast.show();
        }

    }

    /**
     * Image Adapter class for clothing
     */
    private class GridImageAdapter extends ArrayAdapter<Clothing> {

        public GridImageAdapter(Context context, List<Clothing> clothingList) {
            super(context, R.layout.closet_preference_clothing_image, clothingList);
        }

        @Override
        /**
         * convert view is a reference to other reusable views
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            //Recycling views
            View view;
            if (convertView != null) {
                view = convertView;
            } else {
                //Get the correct view to inflate
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.closet_preference_clothing_image, parent, false);
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
