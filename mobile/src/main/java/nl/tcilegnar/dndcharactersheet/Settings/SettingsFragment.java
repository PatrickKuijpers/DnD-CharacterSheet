package nl.tcilegnar.dndcharactersheet.Settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	private ListPreference expUpdateType;
	private ListPreference expUpdatePickerSteps;
	private Settings settings = new Settings();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		initPreferences();
	}

	private void initPreferences() {
		expUpdateType = (ListPreference) findPreference(getString(R.string.setting_key_experience_update_type));
		expUpdatePickerSteps = (ListPreference) findPreference(getString(R.string.setting_key_experience_update_picker_steps));

		// TODO: setting defaults setten? Hoe / kan dit?

		initDependencies();

		setPreferenceChangeListeners();
	}

	private void initDependencies() {
		String selectedValue = expUpdateType.getValue().toString();
		handleDependencyOfExpUpdateType(selectedValue);
	}

	private void setPreferenceChangeListeners() {
		expUpdateType.setOnPreferenceChangeListener(this);
		expUpdatePickerSteps.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference.equals(expUpdateType)) {
			String selectedValue = newValue.toString();
			handleDependencyOfExpUpdateType(selectedValue);
		}

		return settings.savePreferenceValue(preference, newValue);
	}

	private void handleDependencyOfExpUpdateType(String selectedValue) {
		if (selectedValue.equals(getString(R.string.setting_entry_experience_update_type_numberpicker))) {
			expUpdatePickerSteps.setEnabled(true);
		} else {
			expUpdatePickerSteps.setEnabled(false);
		}
	}
}