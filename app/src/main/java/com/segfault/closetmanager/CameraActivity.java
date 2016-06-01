package com.segfault.closetmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    private static final int REQUEST_CAMERA_PERMISSION = 38237;

    Camera.PictureCallback rawCallback;
    Camera.ShutterCallback shutterCallback;
    Camera.PictureCallback jpegCallback;

    ImageView overlay;
    String EXTRA_TYPE_STRING;

    Closet mCurrentCloset = Account.currentAccountInstance.getCloset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_camera);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION); //TODO: have an actual thing for this

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        EXTRA_TYPE_STRING = getIntent().getStringExtra(Clothing.EXTRA_TYPE_STRING);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        overlay = (ImageView)findViewById(R.id.overlay);
        if(EXTRA_TYPE_STRING.equals(Clothing.TOP)){
            overlay.setImageResource(R.drawable.top);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.BOTTOM)){
            overlay.setImageResource(R.drawable.pants);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.ACCESSORY)){
            overlay.setImageResource(R.drawable.accessory);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.SHOE)){
            overlay.setImageResource(R.drawable.sneaker);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.BODY)){
            overlay.setImageResource(R.drawable.dress);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.HAT)){
            overlay.setImageResource(R.drawable.cap);
        }
        else if(EXTRA_TYPE_STRING.equals(Clothing.JACKET)){
            overlay.setImageResource(R.drawable.hooded_jacket);
        }



        jpegCallback = new PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                Clothing currCloth;
                try {
                    String id = String.format("/sdcard/%d.png", System.currentTimeMillis());
                    outStream = new FileOutputStream(id);

                    outStream.write(data);
                    outStream.close();
                    Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_LONG).show();

                    currCloth = new Clothing();
                    currCloth.setId(id);

                    mCurrentCloset.getList().add(currCloth);

                    mCurrentCloset.addId(id);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                finally {
                    Intent intent = new Intent(getBaseContext(), AddClothingActivity.class);
                    int index = mCurrentCloset.getList().size() - 1;
                    intent.putExtra("Clothing", mCurrentCloset.getList().get(index));
                    startActivity(intent);
                }
            }
        };

    }

    public void captureImage(View v) throws IOException {
        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        }

        catch (RuntimeException e) {
            e.printStackTrace();
        }

        Camera.Parameters param;
        param = camera.getParameters();
        param.setPreviewSize(352, 288);
        camera.setDisplayOrientation(90);
        camera.setParameters(param);

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        camera = Camera.open();
                    }

                    catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    System.err.println("Oh no! You can't use the camera!");
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}