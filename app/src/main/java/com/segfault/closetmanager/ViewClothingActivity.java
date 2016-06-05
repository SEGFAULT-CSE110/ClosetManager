package com.segfault.closetmanager;

<<<<<<< HEAD
import android.content.Intent;
=======
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
>>>>>>> refs/remotes/origin/Renu
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewClothingActivity extends BaseActivity {
<<<<<<< HEAD
    Clothing currentClothing;
=======
    Context context = this;
    Button delete_button;
    Button update_button;

    private Closet currCloset = IClosetApplication.getAccount().getCloset();
    private Clothing currentClothing;

    SharedPreferences mPrefs;
>>>>>>> refs/remotes/origin/Renu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing);
        setToolbar((Toolbar) findViewById(R.id.toolbar));

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.view_clothing_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);

        // get preferences
        mPrefs = getSharedPreferences("com.segfault.closetmanager", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
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
<<<<<<< HEAD
        currentClothing = getIntent().getParcelableExtra("Clothing");
=======
        String id = (String) getIntent().getSerializableExtra("clothing_id");
        currentClothing = currCloset.findClothingByID(id);
>>>>>>> refs/remotes/origin/Renu

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
                    deleteClothing();
                }
            });
        }//end of button


    } //end of initView method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_clothing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            goToAbout();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_clothing) {
            goToAbout();
            System.out.println("base delete clothing");
            return true;
        }

        if (id == R.id.update_clothing) {
            goToUpdateClothing();
            System.out.println("base update clothing");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToUpdateClothing() {
        Intent intent = new Intent(context, UpdateClothingActivity.class);
        System.err.println("RENU: Passing in id to update clothing activity: " + currentClothing.getId());
        intent.putExtra("clothing_id", currentClothing.getId());
        startActivity(intent);
    }

<<<<<<< HEAD
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_clothing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            goToAbout();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_clothing) {
            goToAbout();
            System.out.println("base delete clothing");
            return true;
        }

        if (id == R.id.update_clothing) {
            goToUpdateClothing();
            System.out.println("base delete clothing");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

<<<<<<< HEAD
    public void goToUpdateClothing() {

        Intent intent = new Intent(getBaseContext(), UpdateClothingActivity.class);
        //String current_clothing = getIntent().getStringExtra(currentClothing.getId()); //TODO different method?
        intent.putExtra("Clothing", currentClothing.getId());
        startActivity(intent);

    }
=======
    public void deleteClothing() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewClothingActivity.this);
        builder.setTitle("Delete Clothing?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currCloset.removeClothing(currentClothing);

                Intent intent = new Intent(context, ClosetActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

>>>>>>> refs/remotes/origin/Renu
=======

>>>>>>> refs/remotes/origin/linkGUI
}
