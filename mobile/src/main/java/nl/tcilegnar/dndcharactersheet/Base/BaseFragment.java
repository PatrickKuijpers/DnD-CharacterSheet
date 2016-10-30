package nl.tcilegnar.dndcharactersheet.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public abstract class BaseFragment extends Fragment {
    private boolean settingsChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings settings = getSettings();
        if (settings != null) {
            settings.addSettingsChangedListener(this);
        }

        setDrawerIconAsBack();
    }

    private void setDrawerIconAsBack() {
        if (shouldShowHomeAsUp()) {
            ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeButtonEnabled(true);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    protected boolean shouldShowHomeAsUp() {
        return false;
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
    public void onDestroy() {
        super.onDestroy();
        Settings settings = getSettings();
        if (settings != null) {
            settings.removeSettingsChangedListener(this);
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
