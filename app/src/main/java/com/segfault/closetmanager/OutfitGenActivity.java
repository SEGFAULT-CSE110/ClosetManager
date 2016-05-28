package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Creates the outfit
 */
public class OutfitGenActivity extends BaseActivity {

    public static final String OUTFIT_GEN_REMOVED_CLOTHING_EXTRA = "REMOVED CLOTHING";
    private static final int CHOOSE_CLOTHING_REQUEST = 1;

    private Account mAccount = Account.currentAccountInstance;
    private Lookbook mLookbook = mAccount.getLookbook();

    private ImageButton mAccessoriesButton;
    private ImageButton mTopButton;
    private ImageButton mBottomButton;
    private ImageButton mShoesButton;
    private LinearLayout mAccessoriesLayout;
    private LinearLayout mTopLayout;
    private LinearLayout mBottomLayout;

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

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.outfit_gen_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAddedOutfitAlready = false;
        mOutfitGeneratedAlready = false;


    }


    /**
     * Saves the outfit when the button is pressed
     *
     * @param view - deprecated
     */
    public void outfitDone(View view) {
        //Add the outfit only if it hasnt been added in already
        if (!mAddedOutfitAlready && mOutfitGeneratedAlready) {
            mLookbook.addOutfit(mCurrentOutfit);
            Toast newToast = Toast.makeText(this, "Saving outfit to Lookbook.", Toast.LENGTH_SHORT);
            newToast.show();
            mAddedOutfitAlready = true;
        } else if (mOutfitGeneratedAlready) {
            Toast newToast = Toast.makeText(this, "You have already saved this outfit.", Toast.LENGTH_SHORT);
            newToast.show();
        } else {
            //TODO: when user adds in a piece of clothing, set outfitGeneratedAlready to true.
            Toast newToast = Toast.makeText(this, "You have not yet created an outfit.", Toast.LENGTH_SHORT);
            newToast.show();
        }
    }


    /**
     * Generates a random outfit
     *
     * @param view - deprecated
     */
    public void generateOutfit(View view) {
        //Clear all of the buttons
        mAccessoriesButton.setImageResource(R.drawable.cap);
        mTopButton.setImageResource(R.drawable.nylon_jacket);
        mBottomButton.setImageResource(R.drawable.bag_pants);
        mShoesButton.setImageResource(R.drawable.sneaker);

        //create a random outfit
        mCurrentOutfit = mLookbook.generateRandomOutfit();
        //TODO: generate outfit with a layout manager side by side

        if (mCurrentOutfit.getHat() != null) {
            mAccessoriesButton.setImageBitmap(mCurrentOutfit.getHat().getBitmap());
        }
        if (mCurrentOutfit.getFirstTop() != null) {
            mTopButton.setImageBitmap(mCurrentOutfit.getFirstTop().getBitmap());
        }
        if (mCurrentOutfit.getFirstBottom() != null) {
            mBottomButton.setImageBitmap(mCurrentOutfit.getFirstBottom().getBitmap());
        }
        if (mCurrentOutfit.getShoes() != null) {
            mShoesButton.setImageBitmap(mCurrentOutfit.getShoes().getBitmap());
        }

        mOutfitGeneratedAlready = true;
        mAddedOutfitAlready = false;
        Toast newToast = Toast.makeText(this, "Generated a random outfit", Toast.LENGTH_SHORT);
        newToast.show();
    }


    private class OutfitGenLinearAdapter extends ArrayAdapter<Clothing>{

        private PreferenceList mPreferenceList;
        
        public OutfitGenLinearAdapter(Context context, List<Clothing> objects, String clothingType) {
            super(context, R.layout.closet_category_clothing_image, objects);
            
            if (objects.size() == 0){
                //handle case of nothing
            }
            
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
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.closet_category_clothing_image, parent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        //Handle adding, removing clothing
        if (requestCode == CHOOSE_CLOTHING_REQUEST){
            if (resultCode == Activity.RESULT_OK){

            } else if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }

}//end class OutfitGenActivity
