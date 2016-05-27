package com.segfault.closetmanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Christopher Cabreros on 26-May-16.
 * This is a custom layout I created for our uses, like LinearLayout or FrameLayout.
 * This will be used for the outfit to correctly display shirts and pants.
 * If you are interested in how I developed this, I recommend you read the article
 * http://javatechig.com/android/how-to-create-custom-layout-in-android-by-extending-viewgroup-class
 */
public class ClothingStackLayout extends ViewGroup {

    private static final float SIZE_MULTIPLIER = 0.85f;
    private static final float SHIFT_MULTIPLIER = 0.1f;
    private static final float SHIFT_RECENTER_MULTIPLIER = SHIFT_MULTIPLIER / 2.0f;

    private static final int CHILD_LEFT_COORDINATE = 0;
    private static final int CHILD_TOP_COORDINATE = 1;
    private static final int CHILD_RIGHT_COORDINATE = 2;
    private static final int CHILD_BOTTOM_COORDINATE = 3;

    public ClothingStackLayout(Context context) {
        super(context);
    }

    public ClothingStackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClothingStackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        //Get the available size of the child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = (int) (SIZE_MULTIPLIER * (childRight - childLeft));
        final int childHeight = (int) (SIZE_MULTIPLIER * (childBottom - childTop));

        int[][] coordinates = new int[count][4];

        //Set the coordinates for each child
        for (int outer = 0; outer < count; outer++) {
            coordinates[outer][CHILD_LEFT_COORDINATE] = childLeft;
            coordinates[outer][CHILD_TOP_COORDINATE] = childTop;
            coordinates[outer][CHILD_RIGHT_COORDINATE] = childRight;
            coordinates[outer][CHILD_BOTTOM_COORDINATE] = childBottom;

            //move the other children back
            for (int inner = 0; inner < outer; inner++) {
                coordinates[outer][CHILD_LEFT_COORDINATE] -= childWidth * SHIFT_MULTIPLIER;
                coordinates[outer][CHILD_TOP_COORDINATE] -= childHeight * SHIFT_MULTIPLIER;
                coordinates[outer][CHILD_RIGHT_COORDINATE] -= childWidth * SHIFT_MULTIPLIER;
                coordinates[outer][CHILD_BOTTOM_COORDINATE] -= childHeight * SHIFT_MULTIPLIER;
            }
        }

        //Set the calculated coordinates for all of the children
        for (int index = 0; index < count; index++) {
            //Center the coordinates
            coordinates[index][CHILD_LEFT_COORDINATE] += childWidth * SHIFT_RECENTER_MULTIPLIER;
            coordinates[index][CHILD_TOP_COORDINATE] += childHeight * SHIFT_RECENTER_MULTIPLIER;
            coordinates[index][CHILD_RIGHT_COORDINATE] += childWidth * SHIFT_RECENTER_MULTIPLIER;
            coordinates[index][CHILD_BOTTOM_COORDINATE] += childHeight * SHIFT_RECENTER_MULTIPLIER;

            View child = getChildAt(index);

            if (child.getVisibility() == GONE) {
                return;
            }

            //Set the child coordinate layouts
            child.layout(coordinates[index][CHILD_LEFT_COORDINATE],
                    coordinates[index][CHILD_TOP_COORDINATE],
                    coordinates[index][CHILD_RIGHT_COORDINATE],
                    coordinates[index][CHILD_BOTTOM_COORDINATE]);
        }
    }

}//end class ClothingStackLayout