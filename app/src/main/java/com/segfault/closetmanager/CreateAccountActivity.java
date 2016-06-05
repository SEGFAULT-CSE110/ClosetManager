package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by allanmartinez on 6/4/16.
 */
public class CreateAccountActivity extends Activity{
    private static final String TAG = LoginActivity.class.getSimpleName();
    AutoCompleteTextView email;
    String username;
    EditText pass;
    String password;
    EditText confirmPass;
    String confirmP;
    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_accounts);
        //Firebase.setAndroidContext(this);

        //final Firebase ref = new Firebase("https://iclosetapp.firebaseio.com");
        email = (AutoCompleteTextView)findViewById(R.id.email);
        username = email.getText().toString();
        pass = (EditText)findViewById(R.id.password);
        password = pass.getText().toString();
        confirmPass = (EditText) findViewById(R.id.confirm_password);
        confirmP = confirmPass.getText().toString();

        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();


        Button createButton = (Button)findViewById(R.id.sign_up_buttom);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmP.equals(password)){
                    prefsEditor.putString( username, password );
                    prefsEditor.apply();
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(CreateAccountActivity.this, "Confirm password does not match password.", Toast.LENGTH_SHORT).show();
                }

                /*ref.createUser(username, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(CreateAccountActivity.this, "Please try inputting new account details again.", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });

    }
}
