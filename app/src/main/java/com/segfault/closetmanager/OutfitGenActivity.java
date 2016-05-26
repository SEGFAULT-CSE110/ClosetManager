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
    }

    public void outfitDone(View view) {
        Toast newToast = Toast.makeText(this, "Saving outfit to lookbook (not implemented)", Toast.LENGTH_SHORT);
        newToast.show();
    }

    public void generateOutfit(View view) {
        //create a random outfit
        Outfit randomOutfit = mLookbook.generateRandomOutfit();
        //TODO: check if we get some clothing
        mAccessoriesButton.setImageBitmap(randomOutfit.getAccessory().getBitmap());
        mTopButton.setImageBitmap(randomOutfit.getTop().getBitmap());
        mBottomButton.setImageBitmap(randomOutfit.getBottom().getBitmap());
        mShoesButton.setImageBitmap(randomOutfit.getShoes().getBitmap());

        Toast newToast = Toast.makeText(this, "Generated a random outfit (not implemented)", Toast.LENGTH_SHORT);
        newToast.show();
    }
}
