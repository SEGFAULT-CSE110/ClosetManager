package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Defines the activity that displays the closet.
 */
public class ClosetActivity extends AppCompatActivity {

    private LinearLayout mClosetLinearLayout;
    private ViewGroup mClosetParentLayout;
    private int mClosetLinearLayoutIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        //FInd all the views
        mClosetParentLayout = (ViewGroup) findViewById(R.id.closet_parent_layout);
        mClosetLinearLayout = (LinearLayout) findViewById(R.id.closet_display_layout);
        mClosetLinearLayoutIndex = mClosetParentLayout.indexOfChild(mClosetLinearLayout);

        //Create and set the code for the bottom bar
        View bottomBarView = findViewById(R.id.closet_bottom_bar);
        BottomBar bottomBar = new BottomBar(bottomBarView, this);

        //Create Floating Action Bar Viewer and intent
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.closet_fab);
        if (myFab != null) {
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), AddClothingActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //referesh the amount of clothing we have

        //check if we have any clothing
        //TODO: implement actual account info here
        if(true){
            //replace the linear layout with the no_elements_layout
            mClosetParentLayout.removeViewAt(mClosetLinearLayoutIndex);
            View noElementsLayout = getLayoutInflater().inflate(
                    R.layout.no_elements_layout, mClosetParentLayout,   false);
            mClosetParentLayout.addView(noElementsLayout, mClosetLinearLayoutIndex);

            //change the text of the no_elements_layout
            TextView noElementsTextView = (TextView) findViewById(R.id.no_elements_default_text);
            if (noElementsTextView != null) {
                noElementsTextView.setText(R.string.closet_no_elements_text);
            }
        }
        else{
            //add the linear layout back in
            mClosetParentLayout.removeViewAt(mClosetLinearLayoutIndex);
            mClosetParentLayout.addView(mClosetLinearLayout, mClosetLinearLayoutIndex);
        }
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
