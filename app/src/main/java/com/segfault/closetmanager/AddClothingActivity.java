package com.segfault.closetmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

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
    private Closet mCurrCloset;

    ///Gson gson = new Gson();
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clothing);

        mCurrCloset = Account.currentAccountInstance.getCloset();

        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        String [] cat_array = new String[]{"Select","Top", "Bottom", "Outerwear", "Shoes", "Accessory", "Hat", "Undergarment","Socks"};
        category = initSpinner(R.id.Category, cat_array);

        String [] weat_array = new String[]{"Select","Snow","Rain","Cold", "Cool","Warm","Hot","Select All"};
        weather = initSpinner(R.id.Weather, weat_array);

        String[] occ_array = new String[]{"Select","Casual", "Work", "Semi-formal","Formal", "Fitness","Party", "Business"};
        occasion = initSpinner(R.id.Occasion, occ_array);

        //eventually make this colored sqaures
        String[] col_array = new String[]{"Select","Red", "Orange", "Yellow", "Green", "Blue","Purple", "Pink","Brown", "Black","White","Gray"};
        color = initSpinner(R.id.Color, col_array);

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
                    String id = (String) getIntent().getSerializableExtra("photo_id");

                    mCurrClothing = new Clothing(selected_category, selected_color, selected_weather, selected_occasion, input_notes,
                            isWorn, isShared, isLost, id);

                    mCurrCloset.addClothing(mCurrClothing);


                //String json = gson.toJson(mCurrClothing);
                //prefsEditor.putString(mCurrClothing.getId(), json);
                //prefsEditor.commit();
                    Bitmap firstBitmap = BitmapFactory.decodeFile(mCurrClothing.getId());

                    //scale down first bitmap
                    final float densityMultiplier = getBaseContext().getResources().getDisplayMetrics().density;
                    int h = (int) (50 * densityMultiplier); //TODO revise size
                    int w = (int) (h * firstBitmap.getWidth() / ((double) firstBitmap.getHeight()));
                    Bitmap secondBitmap = Bitmap.createScaledBitmap(firstBitmap, w, h, true);

                    //Recycle the bitmap to preserve memory
                    firstBitmap.recycle();

                    mCurrClothing.setBitmap(secondBitmap);

                    // Store id and data
                    mCurrCloset.addId(mCurrClothing.getId());

                    mPrefs = getPreferences(MODE_PRIVATE);
                    prefsEditor = mPrefs.edit();
                    gson = new Gson();

                    // Store clothing object
                    String clothing = gson.toJson(mCurrClothing);
                    prefsEditor.putString(mCurrClothing.getId(), clothing);

                    // Store id list
                    String id_list = gson.toJson(mCurrCloset.getIdList());
                    prefsEditor.putString("id_list",id_list);

                    prefsEditor.commit();
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

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        if (intent.hasExtra("Clothing")) {
            mCurrClothing = (Clothing)intent.getSerializableExtra("Clothing");

        }
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
    protected Spinner initSpinner (int resource, String []arr){
        Spinner sp = (Spinner) findViewById(resource);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);

        // Specify the layout to use when the list of choices appears
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        sp.setAdapter(adapter);

        return sp;

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

    public void retakePicture(View view) {
        //TODO: remove picture, go to camera, retrieve bitmap from camera
        //TODO: delete old picture if it was saved
    }

    public void addClothing(View view) {
        //TODO: take the values from all the spinners and add the clothing to the closet
    }
}
