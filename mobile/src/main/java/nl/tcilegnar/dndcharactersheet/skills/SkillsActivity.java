package nl.tcilegnar.dndcharactersheet.skills;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class SkillsActivity extends BaseStorageActivity {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new SkillsFragment();
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }
}
