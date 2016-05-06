package com.segfault.closetmanager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines the activity when user decides to view clothing by category.
 */
public class ViewClothingByCatActivity extends FragmentActivity{

    public BitmapFactory.Options mOptions;
    public static List<Bitmap> bitmapList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_clothing_by_cat);

        //load images
        try {
            bitmapList = getImages(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridImageAdapter theAdapter = new GridImageAdapter(this, bitmapList);
        GridView theListView = (GridView) findViewById(R.id.gridView);
        theListView.setAdapter(theAdapter);

        //catch any clicks
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String bitmapSelectedText = "You selected " + position;
            }
        });

    }

    /**
     * Loading method. Only for debug purposes
     * @param context
     * @return
     * @throws IOException
     */
    private List<Bitmap> getImages(Context context) throws IOException {
        //get root directory
        String root = "images/";
        System.out.println(root);

        AssetManager assetManager = context.getAssets();
        String[] files = assetManager.list("images");
        List<String> it= Arrays.asList(files);

        List<Bitmap> bitmaps = new ArrayList<Bitmap>();

        for (int index = 0; index < it.size(); index++) {

            if (it.get(index).contains(".jpg")) {
                InputStream istr = assetManager.open(root + it.get(index));
                Bitmap bitmap = BitmapFactory.decodeStream(istr);
                bitmaps.add(bitmap);
                istr.close();
                System.out.println("Loaded " + it.get(index));
            }
        }
        return bitmaps;
    }


    /**
     * Image Adapter class
     */
    private class GridImageAdapter extends ArrayAdapter<Bitmap>{

        public GridImageAdapter(Context context, List<Bitmap> bitmapList) {
            super(context, R.layout.clothing_image_fragment, bitmapList);
        }

        @Override
        /**
         * convert view is a reaference to other reusable views
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View view = inflater.inflate(R.layout.clothing_image_fragment, parent, false);

            Bitmap currentBitmap = getItem(position);
            ImageView imageView = (ImageView) view.findViewById(R.id.clothing_image_view);
            imageView.setImageBitmap(currentBitmap);

            return view;
        }

    }

}//end class viewByCategoryActivity
