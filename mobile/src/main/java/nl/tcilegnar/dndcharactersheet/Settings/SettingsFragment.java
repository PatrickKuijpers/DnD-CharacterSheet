package nl.tcilegnar.dndcharactersheet.Settings;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
    private Settings settings = Settings.getInstance();

    private CheckBoxPreference showHintsPref;
    private ListPreference expUpdateTypePref;
    private ListPreference expPickerStepsPref;
    private CheckBoxPreference allowLevelDownPref;
    private ListPreference moneyUpdateTypePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        initPreferences();
        initConfirmButton();
    }

    private void initPreferences() {
        showHintsPref = (CheckBoxPreference) findPreference(getString(R.string.setting_key_show_hints));
        expUpdateTypePref = (ListPreference) findPreference(getString(R.string.setting_key_experience_update_type));
        expPickerStepsPref = (ListPreference) findPreference(getString(R.string.setting_key_experience_picker_steps));
        allowLevelDownPref = (CheckBoxPreference) findPreference(getString(R.string.setting_key_allow_level_down));
        moneyUpdateTypePref = (ListPreference) findPreference(getString(R.string.setting_key_money_update_type));
        initValues();
        initDependencies();

        setPreferenceChangeListeners();
    }

    private void initValues() {
        boolean shouldShowHints = settings.shouldShowHints();
        showHintsPref.setChecked(shouldShowHints);

        String experienceUpdateType = settings.getExperienceUpdateType();
        expUpdateTypePref.setValue(experienceUpdateType);

        int pickerStepSize = settings.getExperiencePickerStepSize();
        expPickerStepsPref.setValue(String.valueOf(pickerStepSize));

        boolean isLevelDownAllowed = settings.isLevelDownAllowed();
        allowLevelDownPref.setChecked(isLevelDownAllowed);

        String moneyUpdateType = settings.getMoneyUpdateType();
        moneyUpdateTypePref.setValue(moneyUpdateType);
    }

    private void initDependencies() {
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

    private void setPreferenceChangeListeners() {
        showHintsPref.setOnPreferenceChangeListener(this);
        expUpdateTypePref.setOnPreferenceChangeListener(this);
        expPickerStepsPref.setOnPreferenceChangeListener(this);
        allowLevelDownPref.setOnPreferenceChangeListener(this);
        moneyUpdateTypePref.setOnPreferenceChangeListener(this);
    }

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
        return settings.savePreferenceValue(preference, newValue);
    }

    private void updateDependencies(Preference preference, Object newValue) {
        if (preference.equals(expUpdateTypePref)) {
            String selectedValue = newValue.toString();
            handleDependencyOfExpUpdateType(selectedValue);
        }
    }
}