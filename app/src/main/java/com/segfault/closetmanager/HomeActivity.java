package com.segfault.closetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * THIS IS THE ACTIVITY THAT RUNS
 */
public class HomeActivity extends AppCompatActivity {

    private Button mClosetButton;
    private Button mOutfitCreatorButton;
    private Button mLookbookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //assign buttons
        mClosetButton = (Button) findViewById(R.id.closet_button);
        mOutfitCreatorButton = (Button) findViewById(R.id.outfit_creator_button);
        mLookbookButton = (Button) findViewById(R.id.lookbook_button);

        TESTING_CLASS tester = new TESTING_CLASS();
        tester.testMethod();
    }


    public void goToCloset(View view) {
        Intent intent = new Intent(this, ClosetActivity.class);
        startActivity(intent);
    }

    public void goToOutfitGenerator(View view) {
        Intent intent = new Intent(this, OutfitGenActivity.class);
        startActivity(intent);
    }

    public void goToLookbook(View view) {
        Intent intent = new Intent(this, LookbookActivity.class);
        startActivity(intent);
    }
}
