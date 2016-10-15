package nl.tcilegnar.dndcharactersheet.Experience.Settings;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class ExperienceSettingsFragment extends SettingsFragment {
    private ListPreference expUpdateTypePref;
    private ListPreference expPickerStepsPref;
    private CheckBoxPreference allowLevelDownPref;

    @Override
    protected int getSettingsResource() {
        return R.xml.settings_experience;
    }

    @Override
    protected ExperienceSettings getSettings() {
        return ExperienceSettings.getInstance();
    }

    @Override
    protected void initViews() {
        expUpdateTypePref = (ListPreference) findPreference(R.string.setting_key_experience_update_type);
        expPickerStepsPref = (ListPreference) findPreference(R.string.setting_key_experience_picker_steps);
        allowLevelDownPref = (CheckBoxPreference) findPreference(R.string.setting_key_allow_level_down);
    }

    @Override
    protected void initValues() {
        ExperienceSettings settings = getSettings();
        String experienceUpdateType = settings.getExperienceUpdateType();
        expUpdateTypePref.setValue(experienceUpdateType);

        int pickerStepSize = settings.getExperiencePickerStepSize();
        expPickerStepsPref.setValue(String.valueOf(pickerStepSize));

        boolean isLevelDownAllowed = settings.isLevelDownAllowed();
        allowLevelDownPref.setChecked(isLevelDownAllowed);
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        String selectedValue = expUpdateTypePref.getValue();
        handleDependencyOfExpUpdateType(selectedValue);
    }

    private void handleDependencyOfExpUpdateType(String selectedValue) {
        if (selectedValue.equals(getString(R.string.setting_entry_experience_update_type_numberpicker))) {
            expPickerStepsPref.setEnabled(true);
        } else {
            expPickerStepsPref.setEnabled(false);
        }
    }

    @Override
    protected void setPreferenceChangeListeners() {
        expUpdateTypePref.setOnPreferenceChangeListener(this);
        expPickerStepsPref.setOnPreferenceChangeListener(this);
        allowLevelDownPref.setOnPreferenceChangeListener(this);
    }

    @Override
    protected void updateDependencies(Preference preference, Object newValue) {
        if (preference.equals(expUpdateTypePref)) {
            String selectedValue = newValue.toString();
            handleDependencyOfExpUpdateType(selectedValue);
        }
    }
}