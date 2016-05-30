package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.Firebase;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Describes the login activity
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);//TODO create application class
        setContentView(R.layout.login);
    }

    public void runLogin(View view) {
        //TODO: check login credentials. also remove finish if not needed

        Account.currentAccountInstance = new Account("kkk");

        Intent intent = new Intent(this, HomeActivity.class);
        this.finish();
        startActivity(intent);
    }
    /*
    public void createAccount(View view) {
        var ref = new Firebase("https://popping-heat-5754.firebaseio.com");
        ref.createUser({
                email    : "bobtony@firebase.com",
                password : "correcthorsebatterystaple"
        }, function(error, userData) {
            if (error) {
                console.log("Error creating user:", error);
            } else {
                console.log("Successfully created user account with uid:", userData.uid);
            }
        });
    }
    */
}
