package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class UpdateClothingActivity extends BaseActivity {

    private Spinner category;
    private Spinner weather;
    private Spinner occasion;
    private Spinner color;

    private CheckBox dirty;
    private CheckBox shared;
    private CheckBox lost;

    private Button doneButton;
    private Button deleteButton;

    //private Clothing currClothing; TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_clothing);

        String [] cat_array = new String[]{"Select","Top", "Bottom", "Outerwear", "Shoes", "Accessory", "Hat", "Undergarment","Socks"};
        initSpinner(category,R.id.Category,cat_array);

        String [] weat_array = new String[]{"Select","Snow","Rain","Cold", "Cool","Warm","Hot","Select All"};
        initSpinner(weather,R.id.Weather,weat_array);

        String[] occ_array = new String[]{"Select","Casual", "Work", "Semi-formal","Formal", "Fitness","Party", "Business"};
        initSpinner(occasion,R.id.Occasion,occ_array);

        //eventually make this colored sqaures
        String[] col_array = new String[]{"Select","Red", "Orange", "Yellow", "Green", "Blue","Purple", "Pink","Brown", "Black","White","Gray"};
        initSpinner(color,R.id.Color,col_array);

        //color = (Spinner) findViewById(R.id.Color);
        //color.setAdapter(new MyAdapter(this, R.layout.row, getAllList()));

        //Recreate bottom bar here because the account has not been created TODO
        //View bottomBarView = findViewById(R.id.view_clothing_bottom_bar);
        //BottomBar mBottomBar = new BottomBar(bottomBarView, this);

        //done button
        doneButton = (Button) findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get all selections
                String selected_category = category.getSelectedItem().toString();
                String selected_weather = weather.getSelectedItem().toString();
                String selected_occasion = occasion.getSelectedItem().toString();
                String selected_color = color.getSelectedItem().toString();

                //create new clothing object - set to currClothing TODO
                //currClothing  = newClothing()
            }
        });

        //delete button
        deleteButton = (Button) findViewById(R.id.done);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*if(currClothing == null) { TODO
                    //pop up window to discard, clear all date fields
                }
                if(currClothing != null) {
                    //delete clothing in database
                    //set currClothing to null
                }*/
            }
        });

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
    /*
    //return list for color spinner
    public ArrayList<ListItem> getAllList() {

        ArrayList<ListItem> allList = new ArrayList<ListItem>();

        //"Select","Red", "Orange", "Yellow", "Green", "Blue","Purple", "Pink","Brown", "Black","White","Gray"

        ListItem item = new ListItem();
        item.setData("Select", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Red", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Orange", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Yellow", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Green", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Blue", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Purple", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Pink", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Brown", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Black", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("White", R.drawable.white);
        allList.add(item);

        item = new ListItem();
        item.setData("Gray", R.drawable.white);
        allList.add(item);


        for (int i = 0; i < 10000; i++) {
            item = new ListItem();
            item.setData("Test " + i, R.drawable.white);
            allList.add(item);
        }

        return allList;
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_clothing, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //class to hold color image and string
    public class ListItem {
        String name;
        int logo;

        public void setData(String name, int logo) {
            this.name = name;
            this.logo = logo;
        }
    }

    //adaptor to display images in spinner
    public class MyAdapter extends ArrayAdapter<ListItem> {

        LayoutInflater inflater;
        ArrayList<ListItem> objects;
        ViewHolder holder = null;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<ListItem> objects) {
            super(context, textViewResourceId, objects);
            inflater = ((Activity) context).getLayoutInflater();
            this.objects = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            ListItem listItem = objects.get(position);
            View row = convertView;

            if (null == row) {
                holder = new ViewHolder();
                row = inflater.inflate(R.layout.row, parent, false);
                holder.name = (TextView) row.findViewById(R.id.name);
                holder.imgThumb = (ImageView) row.findViewById(R.id.imgThumb);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.name.setText(listItem.name);
            holder.imgThumb.setBackgroundResource(listItem.logo);

            return row;
        }

        static class ViewHolder {
            TextView name;
            ImageView imgThumb;
        }
    } */
}



