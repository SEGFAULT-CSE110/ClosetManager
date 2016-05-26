package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Christopher Cabreros on 05-May-16.
 * Describes the login activity
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void runLogin(View view) {
        //TODO: check login credentials. also remove finish if not needed
        Intent intent = new Intent(this, HomeActivity.class);
        this.finish();
        startActivity(intent);
    }

}
