package nl.tcilegnar.dndcharactersheet.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public abstract class BaseStorageFragment extends BaseFragment {
    private boolean settingsChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSettings().setSettingsChangedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (settingsChanged) {
            settingsChanged = false;
            updateSettingsData();
        }
        onLoadData();
    }

    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract Settings getSettings();

    protected abstract void onLoadData();

    protected abstract void onSaveData();

    protected abstract void updateSettingsData();

    public void onSettingsChanged() {
        settingsChanged = true;
    }
}
