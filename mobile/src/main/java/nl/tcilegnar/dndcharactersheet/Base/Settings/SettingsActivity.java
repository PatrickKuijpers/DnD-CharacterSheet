package nl.tcilegnar.dndcharactersheet.Base.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public abstract class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, getSettingsFragment()).commit();
    }

    protected abstract SettingsFragment getSettingsFragment();
}
