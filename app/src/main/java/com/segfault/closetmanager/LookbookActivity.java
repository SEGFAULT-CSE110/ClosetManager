package com.segfault.closetmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Class that defines the lookbook activity
 */
public class LookbookActivity extends AppCompatActivity {


    private GridView mLookbookGridView;
    private ViewGroup mLookbookParentLayout;
    private int mLookbookGridViewIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook);

        //FInd all the views
        mLookbookParentLayout = (ViewGroup) findViewById(R.id.lookbook_parent_layout);
        mLookbookGridView = (GridView) findViewById(R.id.lookbook_grid_view);
        mLookbookGridViewIndex = mLookbookParentLayout.indexOfChild(mLookbookGridView);

        //Create and set the code for the bottom bar
        View bottomBarView = findViewById(R.id.lookbook_bottom_bar);
        BottomBar bottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //referesh the amount of clothing we have

        //check if we have any clothing
        //TODO: implement actual account info here
        if(true){
            //replace the linear layout with the no_elements_layout
            mLookbookParentLayout.removeViewAt(mLookbookGridViewIndex);
            View noElementsLayout = getLayoutInflater().inflate(
                    R.layout.no_elements_layout, mLookbookParentLayout,   false);
            mLookbookParentLayout.addView(noElementsLayout, mLookbookGridViewIndex);

            //change the text of the no_elements_layout
            TextView noElementsTextView = (TextView) findViewById(R.id.no_elements_default_text);
            if (noElementsTextView != null) {
                noElementsTextView.setText(R.string.lookbook_no_elements_text);
            }
        }
        else{
            mLookbookParentLayout.removeViewAt(mLookbookGridViewIndex);
            mLookbookParentLayout.addView(mLookbookGridView, mLookbookGridViewIndex);

        }
    }
}
