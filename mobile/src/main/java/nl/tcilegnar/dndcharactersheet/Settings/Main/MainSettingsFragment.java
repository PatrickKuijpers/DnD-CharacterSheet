package nl.tcilegnar.dndcharactersheet.Settings.Main;

import android.preference.CheckBoxPreference;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class MainSettingsFragment extends SettingsFragment {
    private CheckBoxPreference showHintsPref;

    @Override
    protected int getSettingsResource() {
        return R.xml.main_settings;
    }

    @Override
    protected MainSettings getSettings() {
        return MainSettings.getInstance();
    }

    @Override
    protected void initViews() {
        showHintsPref = (CheckBoxPreference) findPreference(R.string.setting_key_show_hints);
    }

    @Override
    protected void initValues() {
        MainSettings settings = getSettings();
        boolean shouldShowHints = settings.shouldShowHints();
        showHintsPref.setChecked(shouldShowHints);
    }

    @Override
    protected void setPreferenceChangeListeners() {
        showHintsPref.setOnPreferenceChangeListener(this);
    }
}