package com.segfault.closetmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Christina on 5/12/2016.
 * Defines all activities not the camera.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToSettings() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public void setPrefTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "App Theme");

        switch (theme) {
            case "App Theme":
                setTheme(R.style.AppTheme);
                break;
            case "Eco":
                setTheme(R.style.EcoTheme);
                break;
            case "Dynamic":
                setTheme(R.style.DynamicTheme);
                break;
            case "Monochrome":
                setTheme(R.style.MonochromeTheme);
                break;
            default:
                break;

        }
    }
}
