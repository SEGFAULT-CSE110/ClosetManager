package com.segfault.closetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Creates the outfit
 */
public class OutfitGenActivity extends BaseActivity {

    private Account mAccount = Account.currentAccountInstance;
    private Lookbook mLookbook = mAccount.getLookbook();

    private ImageButton mAccessoriesButton;
    private ImageButton mTopButton;
    private ImageButton mBottomButton;
    private ImageButton mShoesButton;

    //Outfit and tracking variable to prevent duplicates
    private boolean mAddedOutfitAlready;
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
        mShoesButton = (ImageButton) findViewById(R.id.shoesButton);

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.outfit_gen_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAddedOutfitAlready = false;
    }


    /**
     * Saves the outfit when the button is pressed
     * @param view - deprecated
     */
    public void outfitDone(View view) {
        //Add the outfit only if it hasnt been added in already
        if (!mAddedOutfitAlready) {
            mLookbook.addOutfit(mCurrentOutfit);
            Toast newToast = Toast.makeText(this, "Saving outfit to Lookbook.", Toast.LENGTH_SHORT);
            newToast.show();
            mAddedOutfitAlready = true;
        } else{
            Toast newToast = Toast.makeText(this, "You have already saved this outfit.", Toast.LENGTH_SHORT);
            newToast.show();
        }
    }


    /**
     * Generates a random outfit
     * @param view - deprecated
     */
    public void generateOutfit(View view) {
        //create a random outfit
        mCurrentOutfit = mLookbook.generateRandomOutfit();
        //TODO: check if we get some clothing
        mAccessoriesButton.setImageBitmap(mCurrentOutfit.getFirstAccessory().getBitmap());
        mTopButton.setImageBitmap(mCurrentOutfit.getFirstTop().getBitmap());
        mBottomButton.setImageBitmap(mCurrentOutfit.getFirstBottom().getBitmap());
        mShoesButton.setImageBitmap(mCurrentOutfit.getShoes().getBitmap());

        Toast newToast = Toast.makeText(this, "Generated a random outfit", Toast.LENGTH_SHORT);
        newToast.show();
    }
}//end class OutfitGenActivity
