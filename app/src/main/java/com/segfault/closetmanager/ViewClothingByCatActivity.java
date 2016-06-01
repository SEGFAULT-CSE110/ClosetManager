package com.segfault.closetmanager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Defines the activity when user decides to view clothing by category.
 */
public class ViewClothingByCatActivity extends BaseActivity{

    public static final String CAME_FROM_CLOSET_STRING = "yes it did come from the closet";

    private List<Clothing> mClothingList;
    private Account mCurrentAccount = Account.currentAccountInstance;
    private Closet mCurrentCloset = mCurrentAccount.getCloset();
    private boolean mCameFromCloset;
    private boolean mRemovedClothing;

    private Clothing mRemoveClothingSymbol;

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

        //Create remove clothing
        mRemoveClothingSymbol = new Clothing();
        mRemoveClothingSymbol.setCategory(Clothing.MINUS);
        //Get the bitmap from the layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View closetPreferenceMinusView = inflater.inflate(R.layout.closet_preference_minus_image, null);
        Bitmap minusBitmap = ((ImageView)closetPreferenceMinusView.findViewById(R.id.clothing_image_view)).getDrawingCache();
        mRemoveClothingSymbol.setBitmap(minusBitmap);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Create essential references. this is needed for onItemClick
        final Activity currentActivity = this;
        Intent previousIntent = getIntent();

        //Get the activity it came from
        mCameFromCloset = previousIntent.getBooleanExtra(CAME_FROM_CLOSET_STRING, true);

        //Get the extra preference
        PreferenceList getPreference;

        //Check if it actually exists
        if (previousIntent.hasExtra(PreferenceList.EXTRA_STRING)) {
            getPreference = (PreferenceList) previousIntent.
                    getSerializableExtra(PreferenceList.EXTRA_STRING);
        } else {
            getPreference = new PreferenceList(false, null, null, null, null, null, null, null);
        };
        //Get the correct list
        mClothingList = mCurrentCloset.filter(getPreference);
        //Depending on the parent activity, add in the minus option
        if (!mCameFromCloset){ //this will always be the last object in here
            mClothingList.add(mRemoveClothingSymbol);
        }

        GridImageAdapter theAdapter = new GridImageAdapter(this, mClothingList);
        GridView theListView = (GridView) findViewById(R.id.gridView);
        if (theListView != null) { //requires nullptr check
            theListView.setAdapter(theAdapter);
            //catch any clicks
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Two different actions based on the activity it came from
                    if (mCameFromCloset){
                        Intent intent = new Intent(currentActivity, ViewClothingActivity.class);
                        intent.putExtra("Clothing", mClothingList.get(position));
                        currentActivity.finish();
                        currentActivity.startActivity(intent);
                    }
                    else {
                        //set to remove clothing if the specific view was clicked
                        if (position == mClothingList.size() - 1){ //last clothing object will be the minus sign
                            mRemovedClothing = true;
                        }

                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(OutfitGenActivity.OUTFIT_GEN_REMOVED_CLOTHING_EXTRA, mRemovedClothing);
                        returnIntent.putExtra(Clothing.EXTRA_STRING, mClothingList.get(position));
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }
            });
        }

        //TODO: if list size is 0, then add in another view
    }


    /**
     * Image Adapter class for clothing
     */
    private class GridImageAdapter extends ArrayAdapter<Clothing>{

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
            if (convertView != null){
                view = convertView;
            } else{
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
