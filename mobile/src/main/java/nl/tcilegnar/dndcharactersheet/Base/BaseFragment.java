package nl.tcilegnar.dndcharactersheet.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public abstract class BaseFragment extends Fragment {
    private boolean settingsChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings settings = getSettings();
        if (settings != null) {
            settings.setSettingsChangedListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (settingsChanged) {
            settingsChanged = false;
            updateSettingsData();
        }
    }

    protected Settings getSettings() {
        return null;
    }

    protected void updateSettingsData() {
    }

    public void onSettingsChanged() {
        settingsChanged = true;
    }
}
