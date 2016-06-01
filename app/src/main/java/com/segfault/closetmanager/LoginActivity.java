package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.facebook.FacebookSdk;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.gson.Gson;


import java.io.IOException;
import java.io.InputStream;
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

    private Closet mCurrentCloset;
    private List<String> list_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //Setup sdk
//        Firebase.setAndroidContext(this);//TODO create application class
//        FacebookSdk.sdkInitialize(this);
//
//        /* Load the view and display it */
        setContentView(R.layout.login);
//
//        /* *************************************
//         *              FACEBOOK               *
//         ***************************************/
//        /* Load the Facebook login button and set up the tracker to monitor access token changes */
//        mFacebookCallbackManager = CallbackManager.Factory.create();
//        mFacebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
//
//        mFacebookAccessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                Log.i(TAG, "Facebook.AccessTokenTracker.OnCurrentAccessTokenChanged");
//                LoginActivity.this.onFacebookAccessTokenChange(currentAccessToken);
//            }
//        };
//
//        /* *************************************
//         *               PASSWORD              *
//         ***************************************/
//        mPasswordLoginButton = (Button) findViewById(R.id.email_sign_in_button);
//        mPasswordLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginWithPassword();
//            }
//        });


//        /* *************************************
//         *               GENERAL               *
//         ***************************************/
//        //mLoggedInStatusTextView = (TextView) findViewById(R.id.login_status);
//
//        mLoggedInStatusTextView = (TextView) findViewById(R.id.textView2);
//        /* Create the Firebase ref that is used for all authentication with Firebase */
//        //mFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
//        mFirebaseRef = new Firebase("https://popping-heat-5754.firebaseio.com");
//
//        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
//        mAuthProgressDialog = new ProgressDialog(this);
//        mAuthProgressDialog.setTitle("Loading");
//        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
//        mAuthProgressDialog.setCancelable(false);
//        mAuthProgressDialog.show();
//
//        mAuthStateListener = new Firebase.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(AuthData authData) {
//                mAuthProgressDialog.hide();
//                setAuthenticatedUser(authData);
//            }
//        };
//        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
//         * user and hide hide any login buttons */
//        mFirebaseRef.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        // if user logged in with Facebook, stop tracking their token
//        if (mFacebookAccessTokenTracker != null) {
//            mFacebookAccessTokenTracker.stopTracking();
//        }
//
//        // if changing configurations, stop tracking firebase session.
//        mFirebaseRef.removeAuthStateListener(mAuthStateListener);
    }


    /**
     * This method fires when any startActivityForResult finishes. The requestCode maps to
     * the value passed into startActivityForResult.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Map<String, String> options = new HashMap<String, String>();

        /*if (requestCode == RC_GOOGLE_LOGIN) {

            if (resultCode != RESULT_OK) {
                mGoogleLoginClicked = false;
            }
            mGoogleIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else if (requestCode == RC_TWITTER_LOGIN) {
            options.put("oauth_token", data.getStringExtra("oauth_token"));
            options.put("oauth_token_secret", data.getStringExtra("oauth_token_secret"));
            options.put("user_id", data.getStringExtra("user_id"));
            authWithFirebase("twitter", options);
        } else {
        */

            /* Otherwise, it's probably the request by the Facebook login button, keep track of the session */
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
        //TODO: check login credentials. also remove finish if not needed
//
//        Firebase ref = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com");
//
//        // Create a handler to handle the result of the authentication
//        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
//            @Override
//            public void onAuthenticated(AuthData authData) {
//                // Authenticated successfully with payload authData
//            }
//
//            @Override
//            public void onAuthenticationError(FirebaseError firebaseError) {
//                // Authenticated failed with error firebaseError
//            }
//        };
//
//        // Authenticate users with a custom Firebase token
//        ref.authWithCustomToken("<token>", authResultHandler);
//
//        // Alternatively, authenticate users anonymously
//        ref.authAnonymously(authResultHandler);
//
//        // Or with an email/password combination
//        ref.authWithPassword("jenny@example.com", "correcthorsebatterystaple", authResultHandler);
//
//        // Or via popular OAuth providers ("facebook", "github", "google", or "twitter")
//        ref.authWithOAuthToken("<provider>", "<oauth-token>", authResultHandler);
        Account currentAccount = IClosetApplication.getAccount();

        mPrefs = getPreferences(MODE_PRIVATE);

        // Load closet
        mCurrentCloset = currentAccount.getCloset();

        String ids = mPrefs.getString("id_list", "");

        list_id = (List<String>) gson.fromJson(ids, List.class);

        if (list_id == null)
            list_id = new ArrayList<>();

        mCurrentCloset.setIdList(list_id);

        try {
            loadPictures(getApplicationContext(), mCurrentCloset.getList(), mCurrentCloset.getIdList());
            currentAccount.getLookbook().assignBelongingCloset(currentAccount.getCloset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, HomeActivity.class);
        this.finish();
        startActivity(intent);
    }


    public void loadPictures(Context context, List<Clothing> clothingList, List<String> id) throws IOException {
        for (int i = 0; i < id.size(); i++) {
            if (id.get(i).contains(".jpg") || id.get(i).contains(".png")) {
                Bitmap firstBitmap = BitmapFactory.decodeFile(id.get(i));

                //scale down first bitmap
                final float densityMultiplier = context.getResources().getDisplayMetrics().density;
                int h = (int) (50 * densityMultiplier); //TODO revise size
                int w = (int) (h * firstBitmap.getWidth() / ((double) firstBitmap.getHeight()));
                Bitmap secondBitmap = Bitmap.createScaledBitmap(firstBitmap, w, h, true);

                //Recycle the bitmap to preserve memory
                firstBitmap.recycle();

                String json = mPrefs.getString(id.get(i), "");
                Clothing currClothing = gson.fromJson(json, Clothing.class);

                if (currClothing.getCategory() == "Hat") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Bottom") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Top") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                } else if (currClothing.getCategory() == "Shoe") {
                    currClothing.setBitmap(secondBitmap);
                    clothingList.add(currClothing);
                }

                System.out.println("Loaded");
            }
        }
    }



    /*
    public void createAccount(View view) {
        Firebase ref = new Firebase("https://popping-heat-5754.firebaseio.com");
        ref.createUser({
                email   :"bobtony@firebase.com",
                password:"correcthorsebatterystaple"
        },function(error, userData) {
            if (error) {
                console.log("Error creating user:", error);
            } else {
                console.log("Successfully created user account with uid:", userData.uid);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.segfault.closetmanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.segfault.closetmanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    */
}
