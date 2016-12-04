package nl.tcilegnar.dndcharactersheet.MainMenu.Settings;

import android.preference.CheckBoxPreference;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class MainSettingsFragment extends SettingsFragment {
    private CheckBoxPreference showHintsPref;
    private CheckBoxPreference playSoundPref;

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
        playSoundPref = (CheckBoxPreference) findPreference(R.string.setting_key_play_sounds);
    }

    @Override
    protected void initValues() { // TODO: is dit nodig?
        MainSettings settings = getSettings();
        boolean shouldShowHints = settings.shouldShowHints();
        showHintsPref.setChecked(shouldShowHints);
        boolean shouldPlaySound = settings.shouldPlaySounds();
        playSoundPref.setChecked(shouldPlaySound);
    }

    @Override
    protected void setPreferenceChangeListeners() {
        showHintsPref.setOnPreferenceChangeListener(this);
        playSoundPref.setOnPreferenceChangeListener(this);
    }
}