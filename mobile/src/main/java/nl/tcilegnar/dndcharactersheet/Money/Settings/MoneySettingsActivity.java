package nl.tcilegnar.dndcharactersheet.Money.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MoneySettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoneySettingsFragment settingsFragment = new MoneySettingsFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).commit();
    }
}
