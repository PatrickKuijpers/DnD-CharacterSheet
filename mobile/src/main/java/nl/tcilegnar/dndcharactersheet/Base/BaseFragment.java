package nl.tcilegnar.dndcharactersheet.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;

import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;

public abstract class BaseFragment extends Fragment {
    public final String TAG = this.getClass().getSimpleName();

    private boolean settingsChanged;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings settings = getSettings();
        if (settings != null) {
            settings.addSettingsChangedListener(this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDrawerIcon();
        setActivityTitle();
    }

    private void setDrawerIcon() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            handleDrawerIcon(supportActionBar);
        }
    }

    protected void setActivityTitle() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getTitle());
        }
    }

    protected String getTitle() {
        return getActivity().getTitle().toString();
    }

    private void handleDrawerIcon(ActionBar supportActionBar) {
        if (shouldShowHomeAsBack()) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected boolean shouldShowHomeAsBack() {
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
