package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    AutoCompleteTextView email;
    String username;
    EditText pass;
    String password;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Firebase.setAndroidContext(this);

        //final Firebase ref = new Firebase("https://iclosetapp.firebaseio.com");
        //Firebase rootRef = ref.child("users").child("closetaccount");
        email = (AutoCompleteTextView)findViewById(R.id.email);
        username = email.getText().toString();
        pass = (EditText)findViewById(R.id.password);
        password = pass.getText().toString();

        Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrefs = getSharedPreferences("com.segfault.closetmanager", Context.MODE_PRIVATE);
                String check = mPrefs.getString(username, "");
                System.out.println("check");
                if(password.equals(check)){
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Username/Password Combination!", Toast.LENGTH_LONG).show();

                }
                /* ref.authWithPassword(username, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {

                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Something went wrong :(
                        switch (firebaseError.getCode()) {
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                // handle a non existing user
                                Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                // handle an invalid password
                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                // handle other errors
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });*/
            }
        });
        Button createButton = (Button)findViewById(R.id.sign_up_buttom);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAcc = new Intent(getBaseContext(), CreateAccountActivity.class);
                startActivity(createAcc);
            }
        });
    }

}
    /*

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }



   /* @Override
    public void onCreate() {
        setContentView(R.layout.login);

       Firebase.setAndroidContext(this);
/*
       Firebase ref = new Firebase("https://iclosetcse110.firebaseio.com");
        Firebase rootRef = ref.child("users").child("closetaccount");
        email = (AutoCompleteTextView)findViewById(R.id.email);
        username = email.getText().toString();
        pass = (EditText)findViewById(R.id.email);
        password = pass.getText().toString();


        Account user = new Account(username);
        // other setup code

        Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
        loButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.getClass() != ClosetActivity.class) {
                    Intent intent = new Intent(callingActivity, ClosetActivity.class);
                    callingActivity.finish();
                    callingActivity.startActivity(intent);
                }
            }
        });/*
        /*ref.authWithPassword(username, password,new AuthResultHandler("user"));
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) { /* ...  }
/*
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
                        switch (error.getCode()) {
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                // handle a non existing user
                                Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                // handle an invalid password
                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                // handle other errors
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };*/


    //}



