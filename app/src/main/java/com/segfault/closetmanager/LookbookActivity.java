package com.segfault.closetmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Class that defines the lookbook activity
 */
public class LookbookActivity extends BaseActivity {

    private RecyclerView mLookbookRecyclerView;
    private ViewGroup mLookbookParentLayout;
    private int mLookbookGridViewIndex;
    private Lookbook mCurrentLookbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook);

        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Find all the views
        mLookbookParentLayout = (ViewGroup) findViewById(R.id.lookbook_parent_layout);
        mLookbookRecyclerView = (RecyclerView) findViewById(R.id.lookbook_recycler_view);
        mLookbookGridViewIndex = mLookbookParentLayout.indexOfChild(mLookbookRecyclerView);

        //Recreate bottom bar
        View bottomBarView = findViewById(R.id.lookbook_bottom_bar);
        BottomBar mBottomBar = new BottomBar(bottomBarView, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Update lookbook
        mCurrentLookbook = Account.currentAccountInstance.getLookbook();

        //check if we have any clothing
        if(mCurrentLookbook.getOutfitList().isEmpty()){
            //replace the linear layout with the no_elements_layout
            mLookbookParentLayout.removeViewAt(mLookbookGridViewIndex);
            View noElementsLayout = getLayoutInflater().inflate(
                    R.layout.no_elements_layout, mLookbookParentLayout, false);
            mLookbookParentLayout.addView(noElementsLayout, mLookbookGridViewIndex);

            //change the text of the no_elements_layout
            TextView noElementsTextView = (TextView) findViewById(R.id.no_elements_default_text);
            if (noElementsTextView != null) {
                noElementsTextView.setText(R.string.lookbook_no_elements_text);
            }
        }
        else{
            //Remove the content view and add in the regular lookbook scroll view
            mLookbookParentLayout.removeViewAt(mLookbookGridViewIndex);
            mLookbookParentLayout.addView(mLookbookRecyclerView, mLookbookGridViewIndex);

            //Create the adapter and add stuff to the closet view
            List<Outfit> outfitList = mCurrentLookbook.getOutfitList();
            //Set and Create Adapter
            OutfitListingAdapter recyclerListAdapter = new OutfitListingAdapter(outfitList);
            mLookbookRecyclerView.setAdapter(recyclerListAdapter);
            //Set and create layout manager
            LinearLayoutManager recyclerListManager = new LinearLayoutManager(this);
            recyclerListManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerListManager.scrollToPosition(0);
            mLookbookRecyclerView.setLayoutManager(recyclerListManager);

        }//end else
    }


    /**
     * Inner class for the recycler view
     * Are you confused? Check out the tutorial at
     *      https://guides.codepath.com/android/using-the-recyclerview#components-of-a-recyclerview
     *      because srsly this is pretty cool
     */
    private class OutfitListingAdapter extends RecyclerView.Adapter<OutfitListingAdapter.ViewHolder>{

        //Member variables
        private List<Outfit> mOutfitList;

        /**
         * Inner inner class to represent the view holder
         */
        public class ViewHolder extends RecyclerView.ViewHolder{

            //Members
            private ImageView mHatView; //TODO change this to a horizontal layout
            private ClothingStackLayout mShirtView;
            private ClothingStackLayout mPantsView;
            private ImageView mShoesView;

            /**
             * Constructor
             * @param itemView - the item to view
             */
            public ViewHolder(View itemView) {
                super(itemView);

                //Get all of the layouts
                mHatView = (ImageView) itemView.findViewById(R.id.outfit_fragment_accessories_view);
                mShirtView = (ClothingStackLayout) itemView.findViewById(R.id.shirt_clothing_stack_layout);
                mPantsView = (ClothingStackLayout) itemView.findViewById(R.id.pants_clothing_stack_layout);
                mShoesView = (ImageView) itemView.findViewById(R.id.outfit_fragment_shoes_view);
            }

            public ImageView getHatView() {
                return mHatView;
            }

            public ClothingStackLayout getShirtView() {
                return mShirtView;
            }

            public ClothingStackLayout getPantsView() {
                return mPantsView;
            }

            public ImageView getShoesView() {
                return mShoesView;
            }
        }

//        /**
//         * Private class usedd to handle the stackView in outfits
//         */
//        private class OutfitStackViewAdapter extends ArrayAdapter<Clothing>{
//
//            public OutfitStackViewAdapter(Context context, List<Clothing> clothes) {
//                super(context, R.layout.outfit_fragment_stack_object, clothes);
//            }
//
//
//            /**
//             * Gets the view for the outpit
//             * @param position - position in list
//             * @param view - view to reset/add to
//             * @param parent - parent of the view
//             * @return - view that was edited
//             */
//            public View getView(int position, View view, ViewGroup parent) {
//                //Get view to inflate
//                if (view == null) {
//                    LayoutInflater inflater = LayoutInflater.from(getContext());
//                    view = inflater.inflate(R.layout.outfit_fragment_stack_object, parent, false);
//                } else {
//                    //TODO: implement recycling
//                }
//
//                ImageView imageView = (ImageView) view.findViewById(R.id.outfit_fragment_stack_object_image);
//                imageView.setImageBitmap(getItem(position).getBitmap());
//
//                return view;
//            }
//        }//end class OutfitStackViewAdapter


        /**
         * Constructor
         * @param outfitList - outfit list to load into
         */
        public OutfitListingAdapter(List<Outfit> outfitList) {
            mOutfitList = outfitList;
        }

        @Override
        /**
         * Inflates the layout and returns the viewHolder
         */
        public OutfitListingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            //Inflate our custom layout
            View outfitView = inflater.inflate(R.layout.outfit_fragment, parent, false);

            //Return a new holder instance
            return new ViewHolder(outfitView);
        }

        @Override
        /**
         * Populate the data through the holder
         */
        public void onBindViewHolder(OutfitListingAdapter.ViewHolder holder, int position) {
            //Get the data model based on position
            Outfit currentOutfit = mOutfitList.get(position);

            //Set item views based on the data model
            //Add image bitmap for hat
            ImageView hatView = holder.getHatView();
            if (currentOutfit.getHat() != null) {
                hatView.setImageBitmap(currentOutfit.getHat().getBitmap());
            } else{
                hatView.setImageResource(android.R.color.transparent);
            }

            //Add image bitmaps for shirts
            ClothingStackLayout shirtViewGroup = holder.getShirtView();
            List<Clothing> topList = currentOutfit.getTops();
            for (int index = 0; index < topList.size(); index++){
                //Get view to inflate
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                View view = inflater.inflate(R.layout.outfit_fragment_stack_object, shirtViewGroup, false);

                //Set the image view to the bitmap desired
                ImageView imageView = (ImageView) view.findViewById(R.id.outfit_fragment_stack_object_image);
                imageView.setImageBitmap(topList.get(index).getBitmap());

                //Add the view to the viewGroup
                shirtViewGroup.addView(view);
            }

            //Add image bitmaps for pants
            ClothingStackLayout pantsViewGroup = holder.getPantsView();
            List<Clothing> bottomList = currentOutfit.getBottoms();
            for (int index = 0; index < bottomList.size(); index++){
                //Get view to inflate
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                View view = inflater.inflate(R.layout.outfit_fragment_stack_object, pantsViewGroup, false);

                //Set the image view to the bitmap desired
                ImageView imageView = (ImageView) view.findViewById(R.id.outfit_fragment_stack_object_image);
                imageView.setImageBitmap(bottomList.get(index).getBitmap());

                //Add the view to the viewGroup
                pantsViewGroup.addView(view);
            }

            //Add image bitmaps for shoes
            ImageView shoesView = holder.getShoesView();
            if (currentOutfit.getShoes() != null) {
                shoesView.setImageBitmap(currentOutfit.getShoes().getBitmap());
            } else{
                shoesView.setImageResource(android.R.color.transparent);
            }

        }

        @Override
        public int getItemCount() {
            return mOutfitList.size();
        }
    }




}//end class LookbookActivity
