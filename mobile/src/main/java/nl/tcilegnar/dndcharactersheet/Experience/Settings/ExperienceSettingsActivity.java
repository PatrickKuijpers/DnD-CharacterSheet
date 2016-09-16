package nl.tcilegnar.dndcharactersheet.Experience.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class ExperienceSettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment settingsFragment = new ExperienceSettingsFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
    }
}
