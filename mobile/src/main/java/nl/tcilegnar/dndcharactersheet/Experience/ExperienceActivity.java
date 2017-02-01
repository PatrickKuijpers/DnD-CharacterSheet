package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettingsActivity;

public class ExperienceActivity extends BaseStorageActivity {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new ExperienceFragment();
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return ExperienceSettingsActivity.class;
    }
}
