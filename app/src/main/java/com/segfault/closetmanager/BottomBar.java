package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

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
     * @param bottomBar - bottom bar to modify
     * @param callingActivity - the activity that is calling this
     */
    public BottomBar(View bottomBar, final Activity callingActivity){
        //assign parameters. These are currently unused
        mActivity = callingActivity;
        mBottomBarView = bottomBar;

        //For the three buttons, find the view and set the onn click behaviour

        Button closetButton = (Button) bottomBar.findViewById(R.id.bottom_bar_closet_button);
        closetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != ClosetActivity.class) {
                    Intent intent = new Intent(callingActivity, ClosetActivity.class);
                callingActivity.startActivity(intent);
            }
            }
        });

        Button outfitButton = (Button) bottomBar.findViewById(R.id.bottom_bar_outfit_generator_button);
        outfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != OutfitGenActivity.class) {
                    Intent intent = new Intent(callingActivity, OutfitGenActivity.class);
                    callingActivity.startActivity(intent);
                }
            }
        });

        Button lookbookButton = (Button) bottomBar.findViewById(R.id.bottom_bar_lookbook_button);
        lookbookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != LookbookActivity.class) {
                    Intent intent = new Intent(callingActivity, LookbookActivity.class);
                    callingActivity.startActivity(intent);
                }
            }
        });

        Button nextButton = (Button) bottomBar.findViewById(R.id.more);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: more button temporarily set to settings
                if (callingActivity.getClass() != PreferencesActivity.class) {
                    Intent intent = new Intent(callingActivity, PreferencesActivity.class);
                    callingActivity.startActivity(intent);
                }
            }
        });

    }

}
