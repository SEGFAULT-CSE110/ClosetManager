package com.segfault.closetmanager;

/**
 * Created by Christina on 5/10/2016.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;

public class PreferencesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_layout);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }
}
