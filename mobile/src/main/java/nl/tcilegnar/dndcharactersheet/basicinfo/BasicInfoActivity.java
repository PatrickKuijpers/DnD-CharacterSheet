package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class BasicInfoActivity extends BaseActivity {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new BasicInfoFragment();
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }
}
