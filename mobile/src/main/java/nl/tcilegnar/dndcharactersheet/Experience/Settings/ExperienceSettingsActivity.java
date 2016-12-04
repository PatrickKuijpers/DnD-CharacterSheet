package nl.tcilegnar.dndcharactersheet.Experience.Settings;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class ExperienceSettingsActivity extends SettingsActivity {
    @Override
    protected SettingsFragment getSettingsFragment() {
        return new ExperienceSettingsFragment();
    }
}
