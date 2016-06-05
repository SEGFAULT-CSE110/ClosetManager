package com.segfault.closetmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Adds the clothing into the database
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

    private ImageButton addClothingPreview;
    private Bitmap currentBitmap;

    private Clothing mCurrClothing;
    private Closet mCurrCloset = IClosetApplication.getAccount().getCloset();

    private AlertDialog imagePreviewDialog;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clothing);
        setToolbar((Toolbar) findViewById(R.id.toolbar));

        //Setup spinners
        String[] cat_array = new String[]{"Select", Clothing.ACCESSORY, Clothing.TOP, Clothing.BOTTOM, Clothing.SHOE, Clothing.BODY, Clothing.HAT, Clothing.JACKET};
        category = initSpinner(R.id.Category, cat_array);
        String[] weat_array = new String[]{"Select", "Snow", "Rain", "Cold", "Cool", "Warm", "Hot", "Select All"};
        weather = initSpinner(R.id.Weather, weat_array);
        String[] occ_array = new String[]{"Select", "Casual", "Work", "Semi-formal", "Formal", "Fitness", "Party", "Business"};
        occasion = initSpinner(R.id.Occasion, occ_array);

        //eventually make this colored squares
        String[] col_array = new String[]{"Select", "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink", "Brown", "Black", "White", "Gray"};
        color = initSpinner(R.id.Color, col_array);

        //get edit text notes and check boxes
        notes = (EditText) findViewById(R.id.Notes);
        worn = (CheckBox) findViewById(R.id.Worn);
        shared = (CheckBox) findViewById(R.id.Shared);
        lost = (CheckBox) findViewById(R.id.Lost);

        //set the image preview
        final String id = (String) getIntent().getSerializableExtra("photo_id");

        //scale down first bitmap
        final float densityMultiplier = getBaseContext().getResources().getDisplayMetrics().density;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize  = 8; //make the image 1/4 the size
        Bitmap firstBitmap = BitmapFactory.decodeFile(id, options);

        //Rotate the bitmap and remove first bitmap
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        currentBitmap = Bitmap.createBitmap(firstBitmap, 0, 0,
                firstBitmap.getWidth(), firstBitmap.getHeight(), matrix, true);
        firstBitmap.recycle();

        //Get the image preview
        addClothingPreview = (ImageButton) findViewById(R.id.add_clothing_image_preview);
        if (addClothingPreview != null) {
            addClothingPreview.setImageBitmap(currentBitmap);
        }

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
                //check whether it is a valid condition
                boolean validSelections = validateClothingAttributes(selected_category, selected_weather, selected_occasion, selected_color);

                //receive the checkmarks
                boolean isWorn = false;
                if (worn.isChecked())
                    isWorn = true;
                boolean isShared = false;
                if (shared.isChecked())
                    isShared = true;
                boolean isLost = false;
                if (lost.isChecked())
                    isLost = true;

                //create new clothing object - set to currClothing and add to closet
                if (validSelections) {

                    //Create the clothing
                    mCurrClothing = new Clothing(selected_category, selected_color, selected_weather, selected_occasion, input_notes,
                            isWorn, isShared, isLost, id);
                    System.err.println("RENU: ID of Clothing added is "  + id);
                    mCurrCloset.addClothing(mCurrClothing);

                    // Store id and data in clothing
                    mCurrCloset.addId(mCurrClothing.getId());
                    //DO NOT SET BITMAP YET

                    //Receive preferences
                    mPrefs = getPreferences(MODE_PRIVATE);
                    prefsEditor = mPrefs.edit();
                    gson = new Gson();

                    // Store clothing object
                    String clothing = gson.toJson(mCurrClothing); //we are also storing the bitmap as a full thing here. this is a problem.
                    prefsEditor.putString(mCurrClothing.getId(), clothing);
                    prefsEditor.apply();

                    //Now set the bitmap
                    mCurrClothing.setBitmap(currentBitmap);

                    // Store id list
                    String id_list = gson.toJson(mCurrCloset.getIdList());
                    prefsEditor.putString("id_list", id_list); //TODO Tyler check this implementation, maybe we need to have a string array of ids

                    prefsEditor.apply();
                    goBackToCloset();
                }
            }
        });
        //delete button
        deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mCurrClothing == null) {
                    //pop up window to discard, clear all date fields
                }
                if (mCurrClothing != null) {
                    //delete clothing in database
                    //set currClothing to null
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Get the layout and set into the alertDialog
        LayoutInflater inflater = getLayoutInflater();
        View previewLayout = inflater.inflate(R.layout.add_clothing_image_dialog, null);
        AlertDialog.Builder previewBuilder = new AlertDialog.Builder(this);
        previewBuilder.setTitle("Preview");
        previewBuilder.setView(previewLayout);

        //Set the imageView
        ImageView previewView = (ImageView) previewLayout.findViewById(R.id.add_clothing_image_dialog_image_view);
        previewView.setImageBitmap(currentBitmap);

        //Set the button
        previewBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        imagePreviewDialog = previewBuilder.create();
    }


    /**
     * Validates whether the selections are not "Select"
     * @param cat
     * @param weath
     * @param occ
     * @param col
     * @return
     */
    protected boolean validateClothingAttributes(String cat, String weath, String occ, String col) {
        if (cat.equals("Select") || weath.equals("Select") || occ.equals("Select") || col.equals("Select")) {
            Toast newToast = Toast.makeText(this, "Invalid attributes", Toast.LENGTH_SHORT);
            newToast.show();
            return false;
        }
        return true;
    }


    /**
     * We will not create a new intent. Finishing this should go back to closet.
     */
    protected void goBackToCloset() {
        this.finish();
    }

    //creates dropdowns given a string array and spinner object
    protected Spinner initSpinner(int resource, String[] arr) {
        Spinner sp = (Spinner) findViewById(resource);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        if (sp != null) {
            sp.setAdapter(adapter);
        } else {
            System.err.println("SP is null in AddClothingActivity.java");
        }

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

    public void showImagePreview(View view) {
        imagePreviewDialog.show();
    }
}
