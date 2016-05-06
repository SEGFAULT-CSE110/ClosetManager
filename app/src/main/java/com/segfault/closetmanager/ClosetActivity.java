package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Defines the activity that displays the closet.
 */
public class ClosetActivity extends Activity {

    //create separate array lists for each type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //referesh the amount of clothing we have

    }

    /**
     * ClosetCategoryAdapter that represents the views
     */
    private class ClosetCategoryAdapter extends ArrayAdapter<String> {

        public ClosetCategoryAdapter(Context context, int resource) {
            super(context, resource);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            return null;
        }
    }

    /**
     * Adapter for the horizontal view
     */
    private class ClosetCategoryHorizontalAdapter extends ArrayAdapter<Clothing>{

        public ClosetCategoryHorizontalAdapter(Context context, int resource, ArrayList<Clothing> clothing) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }

}
