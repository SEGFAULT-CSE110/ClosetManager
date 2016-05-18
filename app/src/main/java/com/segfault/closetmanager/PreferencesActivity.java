package com.segfault.closetmanager;

/**
 * Created by Christina on 5/10/2016.
 */
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.err.println("1");
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
        System.err.println("2");
    }

    public static class PreferencesFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            System.err.println("3");
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            System.err.println("4");
        }
    }

}
