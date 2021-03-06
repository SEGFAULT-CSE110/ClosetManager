package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * This class defines the code to run the bottom bar, and is personalized for each class.
 * We do this to reduce code copying.
 */
public final class BottomBar {

    private Activity mActivity;
    private View mBottomBarView;

    /**
     * Creates a bottom bar action class
     *
     * @param bottomBar       - bottom bar to modify
     * @param callingActivity - the activity that is calling this
     */
    public BottomBar(View bottomBar, final Activity callingActivity) {
        //assign parameters. These are currently unused
        mActivity = callingActivity;
        mBottomBarView = bottomBar;

        //For the three buttons, find the view and set the onn click behaviour

        LinearLayout closetButton = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_closet_button);
        closetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != ClosetActivity.class) {
                    Intent intent = new Intent(callingActivity, ClosetActivity.class);
                    callingActivity.finish();
                    callingActivity.startActivity(intent);
                }
            }
        });

        LinearLayout outfitButton = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_outfit_generator_button);
        outfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != OutfitGenActivity.class) {
                    Intent intent = new Intent(callingActivity, OutfitGenActivity.class);
                    callingActivity.finish();
                    callingActivity.startActivity(intent);
                }
            }
        });

        LinearLayout lookbookButton = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_lookbook_button);
        lookbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != LookbookActivity.class) {
                    Intent intent = new Intent(callingActivity, LookbookActivity.class);
                    callingActivity.finish();
                    callingActivity.startActivity(intent);
                }
            }
        });

        LinearLayout laundryButton = (LinearLayout) bottomBar.findViewById(R.id.bottom_bar_more_layout);
        laundryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != PreferencesActivity.class) {
                    Intent intent = new Intent(callingActivity, LaundryActivity.class);
                    callingActivity.startActivity(intent);
                }
            }
        });

    }

}
