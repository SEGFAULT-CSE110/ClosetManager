package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();


    /* *************************************
     *              GENERAL                *
     ***************************************/
    /* TextView that is used to display information about the logged in user */
    private TextView mLoggedInStatusTextView;

    /* A dialog that is presented until the Firebase authentication finished. */
    private ProgressDialog mAuthProgressDialog;

    /* A reference to the Firebase */
    private Firebase mFirebaseRef;

    /* Data from the authenticated user */
    private AuthData mAuthData;

    /* Listener for Firebase session changes */
    private Firebase.AuthStateListener mAuthStateListener;


    /* *************************************
     *              FACEBOOK               *
     ***************************************/
    /* The login button for Facebook */
    private LoginButton mFacebookLoginButton;
    /* The callback manager for Facebook */
    private CallbackManager mFacebookCallbackManager;
    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mFacebookAccessTokenTracker;

    /* *************************************
     *              PASSWORD               *
     ***************************************/
    private Button mPasswordLoginButton;


    // Storage variables
    SharedPreferences mPrefs;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
    }


    /**
     * This method fires when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Map<String, String> options = new HashMap<String, String>();

        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* If a user is currently authenticated, display a logout menu */
        if (this.mAuthData != null) {
            //getMenuInflater().inflate(R.menu.main, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.log_out_botton) { // not sure
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Unauthenticate from Firebase and from providers where necessary.
     */
    private void logout() {
        if (this.mAuthData != null) {
            /* logout of Firebase */
            mFirebaseRef.unauth();
            /* Logout of any of the Frameworks. This step is optional, but ensures the user is not logged into
             * Facebook/Google+ after logging out of Firebase. */
            if (this.mAuthData.getProvider().equals("facebook")) {
                /* Logout from Facebook */
                LoginManager.getInstance().logOut();
            }
            /* Update authenticated user and show login buttons */
            setAuthenticatedUser(null);
        }
    }

    /**
     * This method will attempt to authenticate a user to firebase given an oauth_token (and other
     * necessary parameters depending on the provider)
     */
    private void authWithFirebase(final String provider, Map<String, String> options) {
        if (options.containsKey("error")) {
            showErrorDialog(options.get("error"));
        } else {
            mAuthProgressDialog.show();
            if (provider.equals("twitter")) {
                // if the provider is twitter, we pust pass in additional options, so use the options endpoint
                mFirebaseRef.authWithOAuthToken(provider, options, new AuthResultHandler(provider));
            } else {
                // if the provider is not twitter, we just need to pass in the oauth_token
                mFirebaseRef.authWithOAuthToken(provider, options.get("oauth_token"), new AuthResultHandler(provider));
            }
        }
    }

    /**
     * Once a user is logged in, take the mAuthData provided from Firebase and "use" it.
     */
    private void setAuthenticatedUser(AuthData authData) {
        if (authData != null) {
            /* Hide all the login buttons */
            mFacebookLoginButton.setVisibility(View.GONE);
            mPasswordLoginButton.setVisibility(View.GONE);
            mLoggedInStatusTextView.setVisibility(View.VISIBLE);
            /* show a provider specific status text */
            String name = null;
            if (authData.getProvider().equals("facebook")
                    || authData.getProvider().equals("google")
                    || authData.getProvider().equals("twitter")) {
                name = (String) authData.getProviderData().get("displayName");
            } else if (authData.getProvider().equals("anonymous")
                    || authData.getProvider().equals("password")) {
                name = authData.getUid();
            } else {
                Log.e(TAG, "Invalid provider: " + authData.getProvider());
            }
            if (name != null) {
                mLoggedInStatusTextView.setText("Logged in as " + name + " (" + authData.getProvider() + ")");
            }
        } else {
            /* No authenticated user show all the login buttons */
            mFacebookLoginButton.setVisibility(View.VISIBLE);

            mPasswordLoginButton.setVisibility(View.VISIBLE);
            mLoggedInStatusTextView.setVisibility(View.GONE);
        }
        this.mAuthData = authData;
        /* invalidate options menu to hide/show the logout button */
        //supportInvalidateOptionsMenu();
    }

    /**
     * Show errors to users
     */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }

    /* ************************************
     *             FACEBOOK               *
     **************************************
     */
    private void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            mAuthProgressDialog.show();
            mFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new AuthResultHandler("facebook"));
        } else {
            // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
            if (this.mAuthData != null && this.mAuthData.getProvider().equals("facebook")) {
                mFirebaseRef.unauth();
                setAuthenticatedUser(null);
            }
        }
    }

    /* ************************************
     *              PASSWORD              *
     **************************************
     */
    public void loginWithPassword() {
        mAuthProgressDialog.show();
        mFirebaseRef.authWithPassword("test@firebaseuser.com", "test1234", new AuthResultHandler("password"));
    }


    public void runLogin(View view) {
        //Receive account singleton
        Account currentAccount = IClosetApplication.getAccount();

        //Load closet
        Closet currentCloset = currentAccount.getCloset();
        loadCloset(currentCloset);

        //Load Lookbook
        loadLookbookToAccount(currentAccount);


        //Start a new intent
        Intent intent = new Intent(this, HomeActivity.class);
        this.finish();
        startActivity(intent);
    }

    /**
     * Loads in the pictures and new other shit
     * @param clothingList
     * @param id
     * @throws IOException
     */
    private void loadPictures(List<Clothing> clothingList, List<String> id) throws IOException {
        System.err.println("Beginning to load in files and bitmaps");
        for (int i = 0; i < id.size(); i++) {

            System.err.println("ID at position " + i + "is " + id);

            if (id.get(i).contains(".jpg") || id.get(i).contains(".png")) {

                //scale down first bitmap
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize  = 8; //make the image 1/4 the size
                Bitmap firstBitmap = BitmapFactory.decodeFile(id.get(i), options);

                //Rotate the bitmap and remove first bitmap
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap currentBitmap = Bitmap.createBitmap(firstBitmap, 0, 0,
                        firstBitmap.getWidth(), firstBitmap.getHeight(), matrix, true);
                firstBitmap.recycle();

                //Recycle the bitmap to preserve memory
                firstBitmap.recycle();

                //Decode the clothing
                String json = mPrefs.getString(id.get(i), "");
                Clothing currClothing = gson.fromJson(json, Clothing.class);
                currClothing.setBitmap(currentBitmap);
                clothingList.add(currClothing);

                System.out.println("Loaded file " + i);
            }
        }

        System.err.println("Finished loading shit");
    }

    private void loadCloset(Closet currentCloset){

        //Receive preferences
        mPrefs  = getSharedPreferences("com.segfault.closetmanager", Context.MODE_PRIVATE);

        //Get the list of IDs. Create a new one if it's not available
        String ids = mPrefs.getString(IClosetApplication.PREFERENCE_CLOTHING_ID, "");
        ArrayList<String> list_id = (ArrayList<String>) gson.fromJson(ids, List.class);
        if (list_id == null) {
            list_id = new ArrayList<>();
            System.err.println("Created a new id list in LoginActivity.java");
        }
        currentCloset.setIdList(list_id);

        //Load the pictures and clothing
        try {
            loadPictures(currentCloset.getList(), currentCloset.getIdList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadLookbookToAccount(Account currentAccount) {

        //Receive preferences
        mPrefs  = getSharedPreferences("com.segfault.closetmanager", Context.MODE_PRIVATE);

        //Receive lookbook
        //http://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type/5554296#5554296
        String lookbookString = mPrefs.getString(IClosetApplication.PREFERENCE_LOOKBOOK_ID, "");
        Type currentType = new TypeToken<List<List<String>>>(){}.getType();
        List<List<String>> currentLookbookString = new Gson().fromJson(lookbookString, currentType);
        if (currentLookbookString == null) {
            currentLookbookString = new ArrayList<>();
            System.err.println("Created a new lookbook in LoginActivity.java");
        }

        Lookbook currentLookbook = currentAccount.getLookbook();
        currentLookbook.assignBelongingCloset(currentAccount.getCloset());
        currentLookbook.deserializeAllOutfits(currentLookbookString);
    }


}
