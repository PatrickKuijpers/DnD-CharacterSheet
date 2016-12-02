package nl.tcilegnar.dndcharactersheet.MainMenu.Settings;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class MainSettingsActivity extends SettingsActivity {
    @Override
    protected SettingsFragment getSettingsFragment() {
        return new MainSettingsFragment();
    }
}
