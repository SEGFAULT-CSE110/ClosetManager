package com.segfault.closetmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewClothingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing);

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

        //change the appropriate text views
        TextView categoryTextView = (TextView) findViewById(R.id.closet_item_category);
        categoryTextView.setText(new StringBuilder().append(R.string.category).append(
                " ").append(currentClothing.getCategory()).toString());
    }
}
