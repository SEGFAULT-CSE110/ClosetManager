package com.segfault.closetmanager;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewClothingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing);

        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.view_clothing_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //get intent extra
        Clothing currentClothing = getIntent().getParcelableExtra("Clothing");

        //get the picture from the view, and set the picture
        ImageView clothingView = (ImageView) findViewById(R.id.view_clothing_picture);
        clothingView.setImageBitmap(currentClothing.getBitmap());

        // get the appropriate text views
        TextView categoryTextView = (TextView) findViewById(R.id.closet_item_category_entry);
        TextView colorTextView = (TextView) findViewById(R.id.closet_item_color_entry);
        TextView weatherTextView = (TextView) findViewById(R.id.closet_item_weather_entry);
        TextView occasionTextView = (TextView) findViewById(R.id.closet_item_occasion_entry);
        TextView notesTextView = (TextView) findViewById(R.id.closet_item_notes_entry);

        // set the appropriate text views
        categoryTextView.setText(currentClothing.getCategory());
        colorTextView.setText(currentClothing.getColor());
        weatherTextView.setText(currentClothing.getWeather());
        occasionTextView.setText(currentClothing.getOccasion());
        notesTextView.setText(currentClothing.getNotes());
    }
}
