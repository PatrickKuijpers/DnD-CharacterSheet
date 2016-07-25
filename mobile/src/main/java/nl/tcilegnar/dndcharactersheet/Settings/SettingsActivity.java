package nl.tcilegnar.dndcharactersheet.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.content.LocalBroadcastManager;

public class SettingsActivity extends PreferenceActivity {
    public static final String SETTINGS_CHANGED = "SETTINGS_CHANGED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setPreferenceChangeListener(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
    }

    public void onPreferenceChanged() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SETTINGS_CHANGED));
    }
}
