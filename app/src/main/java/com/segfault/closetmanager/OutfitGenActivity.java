package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Creates the outfit
 */
public class OutfitGenActivity extends BaseActivity {

    public static final String OUTFIT_GEN_REMOVED_CLOTHING_EXTRA = "REMOVED CLOTHING";
    private static final int CHOOSE_CLOTHING_REQUEST = 1;

    private Account mAccount = IClosetApplication.getAccount();
    private Lookbook mLookbook = mAccount.getLookbook();

    private ImageButton mAccessoriesButton;
    private ImageButton mTopButton;
    private ImageButton mBottomButton;
    private ImageButton mShoesButton;
    private LinearLayout mAccessoriesLayout;
    private LinearLayout mTopLayout;
    private LinearLayout mBottomLayout;
    private OutfitGenLinearAdapter mAccessoriesAdapter;
    private OutfitGenLinearAdapter mTopAdapter;
    private OutfitGenLinearAdapter mBottomAdapter;
    private LinearLayout.LayoutParams mAccessoriesParameters;
    private LinearLayout.LayoutParams mTopParameters;
    private LinearLayout.LayoutParams mBottomParameters;

    //Outfit and tracking variable to prevent duplicates
    private boolean mAddedOutfitAlready;
    private boolean mOutfitGeneratedAlready;
    private Outfit mCurrentOutfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_gen);

        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Get the required views
        mAccessoriesButton = (ImageButton) findViewById(R.id.accessoriesButton);
        mTopButton = (ImageButton) findViewById(R.id.topButton);
        mBottomButton = (ImageButton) findViewById(R.id.bottomButton);
        mShoesButton = (ImageButton) findViewById(R.id.outfit_gen_shoes_button);
        mAccessoriesLayout = (LinearLayout) findViewById(R.id.outfit_gen_accessories_layout);
        mTopLayout = (LinearLayout) findViewById(R.id.outfit_gen_top_layout);
        mBottomLayout = (LinearLayout) findViewById(R.id.outfit_gen_bottom_layout);

        //Set the adapters
        mAccessoriesAdapter = new OutfitGenLinearAdapter(this, Clothing.ACCESSORY);
        mTopAdapter = new OutfitGenLinearAdapter(this, Clothing.TOP);
        mBottomAdapter = new OutfitGenLinearAdapter(this, Clothing.BOTTOM);

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.outfit_gen_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //set default variable values
        mAddedOutfitAlready = false;
        mOutfitGeneratedAlready = false;

        //Set the layout parameters
        //Calculate in post because we need to get the actual height post creation
        //otherwise the height is 0dp
        mAccessoriesParameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mAccessoriesLayout.getHeight());
        mAccessoriesLayout.post(new Runnable() {
            @Override
            public void run() {
                mAccessoriesParameters.height = mAccessoriesLayout.getHeight();
            }
        });
        mTopParameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mTopLayout.getHeight());
        mTopLayout.post(new Runnable() {
            @Override
            public void run() {
                mTopParameters.height = mTopLayout.getHeight();
            }
        });
        mBottomParameters = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, mBottomLayout.getHeight());
        mBottomLayout.post(new Runnable() {
            @Override
            public void run() {
                mBottomParameters.height = mBottomLayout.getHeight();
            }
        });
    }


    /**
     * Saves the outfit when the button is pressed
     *
     * @param view - deprecated
     */
    public void outfitDone(View view) {
        //Add the outfit only if it hasn't been added in already
        if (!mAddedOutfitAlready && mOutfitGeneratedAlready) {
            mLookbook.addOutfit(mCurrentOutfit);
            Toast newToast = Toast.makeText(this, R.string.save_outfit_to_lookbook_toast_text,
                    Toast.LENGTH_SHORT);
            newToast.show();
            mAddedOutfitAlready = true;
        }
        else if (mOutfitGeneratedAlready) {
            Toast newToast = Toast.makeText(this, R.string.already_saved_outfit_toast_text,
                    Toast.LENGTH_SHORT);
            newToast.show();
        }
        else {
            Toast newToast = Toast.makeText(this, R.string.not_created_outfit_toast_text,
                    Toast.LENGTH_SHORT);
            newToast.show();
        }
    }


    /**
     * Generates a random outfit
     *
     * @param view - deprecated
     */
    public void generateOutfit(View view) {
        //Clear all of the views
        clearLayouts();

        //create a random outfit
        mCurrentOutfit = mLookbook.generateRandomOutfit();
        //Add components of outfits to layouts
        if (mCurrentOutfit.getHat() != null) {
            mAccessoriesAdapter.add(mCurrentOutfit.getHat());
        }
        if (mCurrentOutfit.getFirstTop() != null) {
            mTopAdapter.addAll(mCurrentOutfit.getTops());
        }
        if (mCurrentOutfit.getFirstBottom() != null) {
            mBottomAdapter.addAll(mCurrentOutfit.getBottoms());
        }
        if (mCurrentOutfit.getShoes() != null) {
            mShoesButton.setImageBitmap(mCurrentOutfit.getShoes().getBitmap());
        }
        updateLayouts();

        mOutfitGeneratedAlready = true;
        mAddedOutfitAlready = false;
        Toast newToast = Toast.makeText(this, "Generated a random outfit", Toast.LENGTH_SHORT);
        newToast.show();
    }


    private class OutfitGenLinearAdapter extends ArrayAdapter<Clothing> {

        private PreferenceList mPreferenceList;

        public OutfitGenLinearAdapter(Context context, String clothingType) {
            super(context, R.layout.closet_category_clothing_image, new ArrayList<Clothing>());

            //we can't get the category from objects because sometimes the list will be of size 0
            //Set the picture and the text for the closet Category
            switch (clothingType) {
                case Clothing.HAT:
                    mPreferenceList = new PreferenceList(false, Clothing.HAT, null, null, null, null, null, null);
                    break;
                case Clothing.ACCESSORY:
                    mPreferenceList = new PreferenceList(false, Clothing.ACCESSORY, null, null, null, null, null, null);
                    break;
                case Clothing.BODY:
                    mPreferenceList = new PreferenceList(false, Clothing.BODY, null, null, null, null, null, null);
                    break;
                case Clothing.BOTTOM:
                    mPreferenceList = new PreferenceList(false, Clothing.BOTTOM, null, null, null, null, null, null);
                    break;
                case Clothing.JACKET:
                    mPreferenceList = new PreferenceList(false, Clothing.JACKET, null, null, null, null, null, null);
                    break;
                case Clothing.SHOE:
                    mPreferenceList = new PreferenceList(false, Clothing.SHOE, null, null, null, null, null, null);
                    break;
                case Clothing.TOP:
                    mPreferenceList = new PreferenceList(false, Clothing.TOP, null, null, null, null, null, null);
                    break;
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Recycle views
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.closet_category_clothing_image, null);
            } else {
                //Remove the previous parent
                parent.removeView(convertView);
            }

            //Add picture to view
            ImageView clothingImageView = (ImageView) convertView.findViewById(R.id.clothing_image_view);
            clothingImageView.setImageBitmap(getItem(position).getBitmap());

            //Add action listener to change the piece of clothing
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewClothingByCatActivity.class);
                    intent.putExtra(PreferenceList.EXTRA_STRING, mPreferenceList);
                    intent.putExtra(Clothing.EXTRA_STRING, getItem(position));
                    intent.putExtra(ViewClothingByCatActivity.CAME_FROM_CLOSET_STRING, false);
                    startActivityForResult(intent, CHOOSE_CLOTHING_REQUEST);
                }
            });

            return convertView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Handle adding, removing clothing
        if (requestCode == CHOOSE_CLOTHING_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                //get the intended
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //nothing is done
                System.out.println("Nothing was done");
            }
        }
    }


    /**
     * Clears the three linear layouts, plus the shoes button
     */
    private void clearLayouts() {
        mAccessoriesAdapter.clear();
        mAccessoriesAdapter.notifyDataSetChanged();
        mAccessoriesLayout.removeAllViews();

        mTopAdapter.clear();
        mTopAdapter.notifyDataSetChanged();
        mTopLayout.removeAllViews();

        mBottomAdapter.clear();
        mBottomAdapter.notifyDataSetChanged();
        mBottomLayout.removeAllViews();

        mShoesButton.setImageResource(R.drawable.sneaker);
    }

    /**
     * Helper method to update each layout
     */
    private void updateLayouts() {
        //for each category, update the adapter, then update the layout
        updateSpecificLayout(Clothing.ACCESSORY, mAccessoriesAdapter, mAccessoriesLayout, mAccessoriesParameters);
        updateSpecificLayout(Clothing.TOP, mTopAdapter, mTopLayout, mTopParameters);
        updateSpecificLayout(Clothing.BOTTOM, mBottomAdapter, mBottomLayout, mBottomParameters);
    }


    /**
     * Updates the specific layout given with the items in the adapter
     *
     * @param adapter - adapter to receive views from
     * @param layout  - layout to place items into
     */
    private void updateSpecificLayout(String type, ArrayAdapter<?> adapter, LinearLayout layout, LinearLayout.LayoutParams params) {
        adapter.notifyDataSetChanged();

        //Add in an image only if the adapter has more than 0 objects
        if (adapter.getCount() > 0) {
            for (int index = 0; index < adapter.getCount(); index++) {
                layout.addView(adapter.getView(index, null, layout), params);
            }
        } else {
            //Add in stock image based on what the category is
            ImageView view = new ImageView(this);
//            LayoutInflater inflater = LayoutInflater.from(this);
//            LinearLayout linearLayoutParent = (LinearLayout) inflater.inflate(R.layout.outfit_gen_category_button, layout);
//            ImageView view = (ImageView) linearLayoutParent.findViewById(R.id.outfit_gen_category_button);
//            //remove the view from the parent
//            linearLayoutParent.removeView(view);
            if (type.equals(Clothing.ACCESSORY)) {
                view.setImageResource(R.drawable.accessory);
            } else if (type.equals(Clothing.TOP)) {
                view.setImageResource(R.drawable.nylon_jacket);
            } else if (type.equals(Clothing.BOTTOM)) {
                view.setImageResource(R.drawable.bag_pants);
            } else {
                System.err.println("ERROR: TYPE " + type + " IS INVALID TYPE IN OUTFIT GEN ACTIVITY");
            }

            //Add the view into the layout
            layout.addView(view);
        }
    }

}//end class OutfitGenActivity
