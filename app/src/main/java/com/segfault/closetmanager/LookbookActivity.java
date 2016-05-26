package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.StackView;
import android.widget.TextView;

import java.lang.reflect.Array;
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
        //update lookbook
        mCurrentLookbook = Account.currentAccountInstance.getLookbook();

        //refresh the amount of clothing we have

        //check if we have any clothing
        if(mCurrentLookbook.getOutfitList().isEmpty()){
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
            //Remove the content view and add in the regular lookbook scroll view
            mLookbookParentLayout.removeViewAt(mLookbookGridViewIndex);
            mLookbookParentLayout.addView(mLookbookRecyclerView, mLookbookGridViewIndex);

            //Create the adapter and add stuff to the closet view
            List<Outfit> outfitList = mCurrentLookbook.getOutfitList();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            mLookbookRecyclerView.setLayoutManager(layoutManager);

            //Go through the lookbook and add all the outfits
            for (int index = 0; index < outfitList.size(); index++){
                View outfitView = R.layout.outfit_fragment;
                Outfit currentOutfit = outfitList.get(index);

                //Add image bitmap for hat
                ImageView hatView = (ImageView) outfitView.findViewById(R.id.outfit_fragment_accessories_view);
                if (currentOutfit.getHat() != null) {
                    hatView.setImageBitmap(currentOutfit.getHat().getBitmap());
                } else{
                    hatView.setImageResource(android.R.color.transparent);
                }

                StackView shirtView = (StackView) outfitView.findViewById(R.id.shirt_stack_view);
                shirtView.setAdapter(new OutfitStackViewAdapter(getBaseContext(), currentOutfit.getTops()));

                StackView pantsView = (StackView) outfitView.findViewById(R.id.pants_stack_view);
                pantsView.setAdapter(new OutfitStackViewAdapter(getBaseContext(), currentOutfit.getBottoms()));

                ImageView shoesView = (ImageView) outfitView.findViewById(R.id.outfit_fragment_shoes_view);
                if (currentOutfit.getShoes() != null) {
                    shoesView.setImageBitmap(currentOutfit.getHat().getBitmap());
                } else{
                    shoesView.setImageResource(android.R.color.transparent);
                }

                layoutManager.addView(outfitView);
            }
        }
    }

    private class OutfitStackViewAdapter extends ArrayAdapter<Clothing>{

        public OutfitStackViewAdapter(Context context, List<Clothing> clothes) {
            super(context, R.layout.outfit_fragment_stack_object, clothes);
        }

        public View getView(int position, View view, ViewGroup parent) {
            //Get view to inflate
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.outfit_fragment_stack_object, parent, false);
            } else {
                //TODO: implement recycling
            }

            ImageView imageView = (ImageView) view.findViewById(R.id.outfit_fragment_stack_object_image);
            imageView.setImageBitmap(getItem(position).getBitmap());

            return view;
        }
    }


}
