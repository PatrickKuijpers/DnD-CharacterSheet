package nl.tcilegnar.dndcharactersheet.ability;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class AbilitiesActivity extends BaseStorageActivity {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new AbilitiesFragment();
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }
}
