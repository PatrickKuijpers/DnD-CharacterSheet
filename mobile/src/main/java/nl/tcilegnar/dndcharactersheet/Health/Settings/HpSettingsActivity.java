package nl.tcilegnar.dndcharactersheet.Health.Settings;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class HpSettingsActivity extends SettingsActivity {
    @Override
    protected SettingsFragment getSettingsFragment() {
        return new HpSettingsFragment();
    }
}
