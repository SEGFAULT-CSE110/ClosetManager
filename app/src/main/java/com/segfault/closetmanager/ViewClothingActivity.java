package com.segfault.closetmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewClothingActivity extends BaseActivity {
    Context context = this;
    Button delete_button;
    Button update_button;

    private Closet currCloset = IClosetApplication.getAccount().getCloset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing);
        setToolbar((Toolbar) findViewById(R.id.toolbar));

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.view_clothing_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        //get intent extra
        String id = (String) getIntent().getSerializableExtra("clothing_id");
        final Clothing currentClothing = currCloset.findClothingByID(id);

        //get the picture from the view, and set the picture
        ImageView clothingView = (ImageView) findViewById(R.id.view_clothing_picture);
        if (clothingView != null) {
            clothingView.setImageBitmap(currentClothing.getBitmap());
        }

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

        Button update_button = (Button) findViewById(R.id.update);
        if (update_button != null) {
            update_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateClothingActivity.class);
                    System.err.println("RENU: Passing in id to update clothing activity: " + currentClothing.getId());
                    intent.putExtra("clothing_id", currentClothing.getId());
                    startActivity(intent);
                }
            });
        }

        Button delete_button = (Button) findViewById(R.id.delete);
        if (delete_button != null) {
            delete_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //IMPLEMENT DELETE
                }
            });
        }//end of button

    } //end of initView method

}
