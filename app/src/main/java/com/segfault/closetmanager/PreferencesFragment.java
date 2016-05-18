package com.segfault.closetmanager;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Christina on 5/16/2016.
 */
public class PreferencesFragment extends PreferenceFragment
{
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
