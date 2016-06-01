package com.segfault.closetmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
public class ClosetActivity extends BaseActivity {

    private ListView mClosetListView;
    private ViewGroup mClosetParentLayout;
    private int mClosetListViewIndex;
    private Closet mCurrentCloset;

    //Lists
    private List<Clothing> clothingList;
    private List<Clothing> topList;
    private List<Clothing> bottomList;
    private List<Clothing> hatList;
    private List<Clothing> shoeList;
    private List<List<Clothing>> listOfLists;

    //Self
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPrefTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        mCurrentCloset = IClosetApplication.getAccount().getCloset();

        clothingList = mCurrentCloset.getList();
        topList = new ArrayList<Clothing>();
        bottomList = new ArrayList<Clothing>();
        hatList = new ArrayList<Clothing>();
        shoeList = new ArrayList<Clothing>();
        listOfLists = new ArrayList<List<Clothing>>();
        // set pref_layout toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Find all the views
        mClosetParentLayout = (ViewGroup) findViewById(R.id.closet_vertical_linear_layout);
        mClosetListView = (ListView) findViewById(R.id.closet_list_view);
        mClosetListViewIndex = mClosetParentLayout.indexOfChild(mClosetListView);

        //Create Floating Action Bar Viewer and intent
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.closet_fab);
        if (myFab != null) {
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //Create a new dialog
                    final Dialog add_clothing_dialog = new Dialog(context);
                    add_clothing_dialog.setContentView(R.layout.add_clothing_type);
                    add_clothing_dialog.setTitle("Select Clothing Type");

                    //Add in action listeners for all the buttons
                    ImageButton addAccessoryButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_accessory_button);
                    if (addAccessoryButton != null) {
                        addAccessoryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.ACCESSORY);
                                startActivity(intent);
                            }
                        });
                    }
                    ImageButton addShirtButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_shirt_button);
                    if (addShirtButton != null) {
                        addShirtButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.TOP);
                                startActivity(intent);
                            }
                        });
                    }
                    ImageButton addPantsButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_pants_button);
                    if (addPantsButton != null) {
                        addPantsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.BOTTOM);
                                startActivity(intent);
                            }
                        });
                    }
                    ImageButton addShoeButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_shoe_button);
                    if (addShoeButton != null) {
                        addShoeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.SHOE);
                                startActivity(intent);
                            }
                        });
                    }
                    ImageButton addHatButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_hat_button);
                    if (addHatButton != null) {
                        addHatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.HAT);
                                startActivity(intent);
                                System.out.println("HIHIHI");
                            }
                        });
                    }
                    ImageButton addDressButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_dress_button);
                    if (addDressButton != null) {
                        addDressButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.BODY);
                                startActivity(intent);
                                System.out.println("THI");
                            }
                        });
                    }
                    ImageButton addJacketButton = (ImageButton) add_clothing_dialog.findViewById(R.id.add_jacket_button);
                    if (addJacketButton != null) {
                        addJacketButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, CameraActivity.class);
                                intent.putExtra(Clothing.EXTRA_TYPE_STRING, Clothing.JACKET);
                                startActivity(intent);
                                System.out.println("HI");
                            }
                        });
                    }


                    add_clothing_dialog.show();
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
        mCurrentCloset = IClosetApplication.getAccount().getCloset();

        //check if we have any clothing
        if (mCurrentCloset.getList().isEmpty()) {
            //replace the linear layout with the no_elements_layout
            mClosetParentLayout.removeViewAt(mClosetListViewIndex);
            View noElementsLayout = getLayoutInflater().inflate(
                    R.layout.no_elements_layout, mClosetParentLayout, false);
            mClosetParentLayout.addView(noElementsLayout, mClosetListViewIndex);

            //change the text of the no_elements_layout
            TextView noElementsTextView = (TextView) findViewById(R.id.no_elements_default_text);
            if (noElementsTextView != null) {
                noElementsTextView.setText(R.string.closet_no_elements_text);
            }
        } else { //refresh the amount of clothing we have
            //TODO: delegate this to closet.java
            if (true) {
                //Clear the lists
                topList.clear();
                bottomList.clear();
                hatList.clear();
                shoeList.clear();
                listOfLists.clear();

                //Add all of the clothing into the lists
                for (int index = 0; index < clothingList.size(); index++) {
                    if (clothingList.get(index) == null){
                        System.err.println("NULL");
                    }
                    else if (clothingList.get(index).getCategory().equals(Clothing.TOP)) {
                        topList.add(clothingList.get(index));
                    } else if (clothingList.get(index).getCategory().equals(Clothing.BOTTOM)) {
                        bottomList.add(clothingList.get(index));
                    } else if (clothingList.get(index).getCategory().equals(Clothing.HAT)) {
                        hatList.add(clothingList.get(index));
                    } else if (clothingList.get(index).getCategory().equals(Clothing.SHOE)) {
                        shoeList.add(clothingList.get(index));
                    }
                }

                //Add all of these lists into a single list of lists
                listOfLists.add(topList);
                listOfLists.add(bottomList);
                listOfLists.add(hatList);
                listOfLists.add(shoeList);

                //add stuff to the closet list view
                ClosetCategoryAdapter adapter = new ClosetCategoryAdapter(this, listOfLists);
                ListView closetListView = (ListView) findViewById(R.id.closet_list_view);
                if (closetListView != null) {
                    closetListView.setAdapter(adapter);
                }

                //once done updating, set updated to false
                mCurrentCloset.setUpdated(false);
            }

            //add the linear layout back in
            mClosetParentLayout.removeViewAt(mClosetListViewIndex);
            mClosetParentLayout.addView(mClosetListView, mClosetListViewIndex);
        }
    }



    /**
     * ClosetCategoryAdapter that represents the large list views
     * Only does this for a specific view
     */
    private class ClosetCategoryAdapter extends ArrayAdapter<List<Clothing>> {

        /**
         * Constructor
         *
         * @param context       - context of this activity
         * @param clothingLists - list of clothing lists
         */
        public ClosetCategoryAdapter(Context context, List<List<Clothing>> clothingLists) {
            super(context, R.layout.closet_category, clothingLists);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            //Get the view to inflate
            View categoryView = inflater.inflate(R.layout.closet_category, parent, false);

            //Get the linear layout
            final LinearLayout linearLayout = (LinearLayout) categoryView.findViewById(R.id.closet_category_clothing_slider_layout);
            //add stuff to the linearLayout
            final List<Clothing> currentList = getItem(position);

            //Get each closet category
            LinearLayout categoryLeftBox = (LinearLayout) categoryView.findViewById(R.id.closet_category_left_layout);
            ImageView categoryViewImage = (ImageView) categoryView.findViewById(R.id.closet_category_picture);
            TextView categoryTextView = (TextView) categoryView.findViewById(R.id.closet_category_text);
            PreferenceList categoryPreferenceList = null;

            //Set the picture and the text for the closet Category
            if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.HAT)) {
                categoryViewImage.setImageResource(R.drawable.cap);
                categoryTextView.setText("Hat");
                categoryPreferenceList = new PreferenceList(false, Clothing.HAT, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.ACCESSORY)) {
                //set image to accessory
                categoryViewImage.setImageResource(R.drawable.accessory);
                categoryTextView.setText("Accessory");
                categoryPreferenceList = new PreferenceList(false, Clothing.ACCESSORY, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.BODY)) {
                //set image to body
                categoryViewImage.setImageResource(R.drawable.dress);
                categoryTextView.setText("Body");
                categoryPreferenceList = new PreferenceList(false, Clothing.BODY, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.BOTTOM)) {
                //set image to bottom
                categoryViewImage.setImageResource(R.drawable.bag_pants);
                categoryTextView.setText("Bottom");
                categoryPreferenceList = new PreferenceList(false, Clothing.BOTTOM, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.JACKET)) {
                //set image to jacket
                categoryViewImage.setImageResource(R.drawable.nylon_jacket);
                categoryTextView.setText("Jacket");
                categoryPreferenceList = new PreferenceList(false, Clothing.JACKET, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.SHOE)) {
                //set image to shoes
                categoryViewImage.setImageResource(R.drawable.sneaker);
                categoryTextView.setText("Shoes");
                categoryPreferenceList = new PreferenceList(false, Clothing.SHOE, null, null, null, null, null, null);
            } else if (!currentList.isEmpty() && currentList.get(0).getCategory().equals(Clothing.TOP)) {
                //set image to top
                categoryViewImage.setImageResource(R.drawable.top);
                categoryTextView.setText("Top");
                categoryPreferenceList = new PreferenceList(false, Clothing.TOP, null, null, null, null, null, null);
            }
            final PreferenceList clickPreference = categoryPreferenceList;

            //Set the pictures to each have an on click listener
            categoryLeftBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewClothingByCatActivity.class);
                    intent.putExtra(PreferenceList.EXTRA_STRING, clickPreference);
                    intent.putExtra(ViewClothingByCatActivity.CAME_FROM_CLOSET_STRING, true);
                    startActivity(intent);
                }
            });

            for (int index = 0; index < currentList.size(); index++) {
                //get clothing bitmap and the appropriate view
                Bitmap currentBitmap = currentList.get(index).getBitmap();

                //get the view and add a click listener to go to the correct view
                View clothingFrame = inflater.inflate(R.layout.closet_category_clothing_image, linearLayout, false);
                final int finalIndex = index; //required to be a final variable
                clothingFrame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ViewClothingActivity.class);
                        intent.putExtra("Clothing", currentList.get(finalIndex));
                        getContext().startActivity(intent);
                    }
                });

                //set the image
                ImageView imageView = (ImageView) clothingFrame.findViewById(R.id.clothing_image_view);
                imageView.setImageBitmap(currentBitmap);

                //add this to the linearLayout
                linearLayout.addView(clothingFrame);
            }

            return categoryView;
        }

    }

}//end class ClosetActivity
