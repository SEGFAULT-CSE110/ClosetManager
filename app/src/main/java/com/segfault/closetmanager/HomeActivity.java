package com.segfault.closetmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * THIS IS THE ACTIVITY THAT RUNS
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TESTING_CLASS tester = new TESTING_CLASS();
        tester.testMethod();
    }


}
