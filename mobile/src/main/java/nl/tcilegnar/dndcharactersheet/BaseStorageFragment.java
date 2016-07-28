package nl.tcilegnar.dndcharactersheet;

import android.os.Bundle;
import android.support.annotation.Nullable;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class BaseStorageFragment extends BaseFragment {
    protected Settings settings = Settings.getInstance();

    private boolean settingsChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings.setSettingsChangedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (settingsChanged) {
            settingsChanged = false;
            updateSettingsData();
        }
    }

    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract void updateSettingsData();

    protected abstract void onSaveData();

    public void onSettingsChanged() {
        settingsChanged = true;
    }
}
