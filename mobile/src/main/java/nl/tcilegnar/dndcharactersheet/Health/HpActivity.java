package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class HpActivity extends BaseStorageActivity {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new HpFragment();
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;//HpSettingsActivity.class;
    }
}
