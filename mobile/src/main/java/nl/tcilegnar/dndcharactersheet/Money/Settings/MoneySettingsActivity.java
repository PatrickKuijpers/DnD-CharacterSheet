package nl.tcilegnar.dndcharactersheet.Money.Settings;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class MoneySettingsActivity extends SettingsActivity {
    @Override
    protected SettingsFragment getSettingsFragment() {
        return new MoneySettingsFragment();
    }
}
