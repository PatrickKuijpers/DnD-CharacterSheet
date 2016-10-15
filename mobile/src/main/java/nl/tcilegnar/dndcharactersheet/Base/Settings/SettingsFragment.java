package nl.tcilegnar.dndcharactersheet.Base.Settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.support.annotation.StringRes;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public abstract class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(getSettingsResource());

        initPreferences();
        initConfirmButton();
    }

    protected abstract int getSettingsResource();

    protected abstract Settings getSettings();

    private void initPreferences() {
        initViews();
        initValues();
        initDependencies();

        setPreferenceChangeListeners();
    }

    protected abstract void initViews();

    protected abstract void initValues();

    protected void initDependencies() {
    }

    protected abstract void setPreferenceChangeListeners();

    private void initConfirmButton() {
        Preference preference = getPreferenceManager().findPreference(getString(R.string.setting_key_confirm));
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().finish();
                return true;
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        updateDependencies(preference, newValue);
        return getSettings().savePreferenceValue(preference, newValue);
    }

    protected void updateDependencies(Preference preference, Object newValue) {
    }

    protected Preference findPreference(@StringRes int keyRes) {
        return super.findPreference(getString(keyRes));
    }
}