package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Christopher Cabreros on 05-May-16.
 */
public class AddClothingActivity extends BaseActivity {

    private Spinner category;
    private Spinner weather;
    private Spinner occasion;
    private Spinner color;

    private CheckBox worn;
    private CheckBox shared;
    private CheckBox lost;

    private Button doneButton;
    private Button deleteButton;

    private EditText notes;

    private Clothing mCurrClothing;
    private Closet mCurrCloset = Account.currentAccountInstance.getCloset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clothing);



        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String [] cat_array = new String[]{"Select","Top", "Bottom", "Outerwear", "Shoes", "Accessory", "Hat", "Undergarment","Socks"};
        initSpinner(category, R.id.Category, cat_array);

        String [] weat_array = new String[]{"Select","Snow","Rain","Cold", "Cool","Warm","Hot","Select All"};
        initSpinner(weather, R.id.Weather, weat_array);

        String[] occ_array = new String[]{"Select","Casual", "Work", "Semi-formal","Formal", "Fitness","Party", "Business"};
        initSpinner(occasion, R.id.Occasion, occ_array);

        //eventually make this colored sqaures
        String[] col_array = new String[]{"Select","Red", "Orange", "Yellow", "Green", "Blue","Purple", "Pink","Brown", "Black","White","Gray"};
        initSpinner(color, R.id.Color, col_array);

        //get edit text notes and check boxes
        notes = (EditText) findViewById(R.id.Notes);
        worn = (CheckBox) findViewById(R.id.Worn);
        shared = (CheckBox) findViewById(R.id.Shared);
        lost = (CheckBox) findViewById(R.id.Lost);

        //done button
        doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get all selections
                String selected_category = category.getSelectedItem().toString();
                String selected_weather = weather.getSelectedItem().toString();
                String selected_occasion = occasion.getSelectedItem().toString();
                String selected_color = color.getSelectedItem().toString();
                String input_notes = notes.getText().toString();

                boolean validSelections = validateClothingAttributes(selected_category,selected_weather,selected_occasion,selected_color);

                boolean isWorn = false;
                if(worn.isChecked())
                    isWorn=true;
                boolean isShared = false;
                if(shared.isChecked())
                    isShared=true;
                boolean isLost = false;
                if(lost.isChecked())
                    isLost=true;

                //create new clothing object - set to currClothing and add to closet
                if(validSelections) {
                    mCurrClothing = new Clothing(selected_category, selected_color, selected_weather, selected_occasion, input_notes, isWorn, isShared, isLost);
                    mCurrCloset.addClothing(mCurrClothing);
                    goBackToCloset();
                }

            }
        });
        //delete button
        deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(mCurrClothing == null) {
                    //pop up window to discard, clear all date fields
                }
                if(mCurrClothing != null) {
                    //delete clothing in database
                    //set currClothing to null
                }
            }
        });
    }

    protected boolean validateClothingAttributes (String cat, String weath, String occ, String col) {
        if(cat.equals("Select") || weath.equals("Select") || occ.equals("Select") || col.equals("Select") ) {
            Toast newToast = Toast.makeText(this, "Invalid attriu", Toast.LENGTH_SHORT);
            newToast.show();
            return false;
        }
        return true;

    }

    protected void goBackToCloset() {
        Intent intent = new Intent(this, ClosetActivity.class);
        this.finish();
        startActivity(intent);
    }
    //creates dropdowns given a string and spinner object
    protected void initSpinner (Spinner sp, int resource, String []arr){
        sp = (Spinner) findViewById(resource);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);

        // Specify the layout to use when the list of choices appears
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

    }

    public void goToCloset(View view) {
        finish();
    }

    public void addClothingCamera(View view) {
    }

    public void addClothingTags(View view) {
    }

    public void addClothingNext(View view) {
    }
}
