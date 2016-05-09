package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Defines the activity that displays the closet.
 */
public class ClosetActivity extends AppCompatActivity {

    private ListView mClosetListView;
    private ViewGroup mClosetParentLayout;
    private int mClosetListViewIndex;
    private Closet mCurrentCloset = Account.currentAccountInstance.getCloset();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        //Find all the views
        mClosetParentLayout = (ViewGroup) findViewById(R.id.closet_parent_layout);
        mClosetListView = (ListView) findViewById(R.id.closet_list_view);
        mClosetListViewIndex = mClosetParentLayout.indexOfChild(mClosetListView);

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

        //Recreate bottom bar here because the account has not been created
        View bottomBarView = findViewById(R.id.closet_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //update closet
        mCurrentCloset = Account.currentAccountInstance.getCloset();

        //check if we have any clothing
        if(mCurrentCloset.getList().isEmpty()){
            //replace the linear layout with the no_elements_layout
            mClosetParentLayout.removeViewAt(mClosetListViewIndex);
            View noElementsLayout = getLayoutInflater().inflate(
                    R.layout.no_elements_layout, mClosetParentLayout,   false);
            mClosetParentLayout.addView(noElementsLayout, mClosetListViewIndex);

            //change the text of the no_elements_layout
            TextView noElementsTextView = (TextView) findViewById(R.id.no_elements_default_text);
            if (noElementsTextView != null) {
                noElementsTextView.setText(R.string.closet_no_elements_text);
            }
        }
        else{
            //refresh the amount of clothing we have
            //TODO: delegate this to closet.java
            List<Clothing> clothingList = mCurrentCloset.getList();
            List<Clothing> topList = new ArrayList<Clothing>();
            List<Clothing> bottomList = new ArrayList<Clothing>();
            List<Clothing> hatList = new ArrayList<Clothing>();
            List<Clothing> shoeList = new ArrayList<Clothing>();

            /**
             * Add all of the objects in the clothing list to the new list
             */
            for (int index = 0; index < clothingList.size(); index++){
                if (clothingList.get(index).getCategory().equals(Clothing.TOP)){
                    topList.add(clothingList.get(index));
                } else if (clothingList.get(index).getCategory().equals(Clothing.BOTTOM)){
                    bottomList.add(clothingList.get(index));
                } else if (clothingList.get(index).getCategory().equals(Clothing.HAT)){
                    hatList.add(clothingList.get(index));
                } else if (clothingList.get(index).getCategory().equals(Clothing.SHOE)){
                    shoeList.add(clothingList.get(index));
                }
            }

            //Add all of these lists into a single list of lists
            List<List<Clothing>> listOfLists = new ArrayList<List<Clothing>>();
            listOfLists.add(topList);
            listOfLists.add(bottomList);
            listOfLists.add(hatList);
            listOfLists.add(shoeList);

            //add stuff to the closet list view
            ClosetCategoryAdapter adapter = new ClosetCategoryAdapter(getApplicationContext(),
                    R.id.closet_list_view, listOfLists);
            ListView closetListView = (ListView) findViewById(R.id.closet_list_view);
            if (closetListView != null) {
                closetListView.setAdapter(adapter);
            }

            //add the linear layout back in
            mClosetParentLayout.removeViewAt(mClosetListViewIndex);
            mClosetParentLayout.addView(mClosetListView, mClosetListViewIndex);
        }
    }



    /**
     * ClosetCategoryAdapter that represents the large list views
     */
    private class ClosetCategoryAdapter extends ArrayAdapter<String> {

        private List<List<Clothing>> mListOfClothingLists;

        /**
         * Constructor
         * @param context - context of this activity
         * @param resource - resource layout ID. Should be a horizontal linearLayout
         * @param clothingLists - list of clothing lists
         */
        public ClosetCategoryAdapter(Context context, int resource,
                                     List<List<Clothing>> clothingLists) {
            super(context, resource);
            mListOfClothingLists = clothingLists;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //Get the view to inflate
            View view = inflater.inflate(R.layout.closet_category, parent, false);

            //Add the horizontal adapter into the view
            ClosetCategoryHorizontalAdapter adapter = new ClosetCategoryHorizontalAdapter(
                    getContext(), R.id.closet_category_clothing_slider,
                    mListOfClothingLists.get(position));
            ListView listView = (ListView) view.findViewById(R.id.closet_category_clothing_slider);
            listView.setAdapter(adapter);

            return view;
        }

        /**
         * Adapter for the horizontal view.
         * Adds the images to thisf
         */
        private class ClosetCategoryHorizontalAdapter extends ArrayAdapter<Clothing>{

            private List<Clothing> mClothingList;

            /**
             * Constructor
             * @param context - context of this activity
             * @param resource - resource layout ID. Should be a horizontal linearLayout
             * @param clothing - arraylist of all clothing that fits this
             */
            public ClosetCategoryHorizontalAdapter(Context context, int resource, List<Clothing> clothing) {
                super(context, resource);
                mClothingList = clothing;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(getContext());

                //Get the view to inflate
                View view = inflater.inflate(R.layout.clothing_image_fragment, parent, false);

                //Add the bitmap to the view
                Bitmap currentBitmap = mClothingList.get(position).getBitmap();
                ImageView imageView = (ImageView) view.findViewById(R.id.clothing_image_view);
                imageView.setImageBitmap(currentBitmap);

                return view;
            }
        }
    }

}//end class ClosetActivity
