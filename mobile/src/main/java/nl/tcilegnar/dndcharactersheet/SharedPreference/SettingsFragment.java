package nl.tcilegnar.dndcharactersheet.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;

import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	private ListPreference expUpdateType;
	private ListPreference expUpdatePickerSteps;

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

		String key = preference.getKey();
		return savePreferenceValue(key, newValue);
	}

	private void handleDependencyOfExpUpdateType(String selectedValue) {
		if (selectedValue.equals(getString(R.string.setting_entry_experience_update_type_numberpicker))) {
			expUpdatePickerSteps.setEnabled(true);
		} else {
			expUpdatePickerSteps.setEnabled(false);
		}
	}

	private boolean savePreferenceValue(String key, Object newValue) {
		SharedPreferences settings = App.getContext().getSharedPreferences(key, Context.MODE_PRIVATE);
		Editor editor = settings.edit();

		boolean saved = true;
		if (newValue instanceof Boolean) {
			editor.putBoolean(key, (Boolean) newValue);
		} else if (newValue instanceof String) {
			editor.putString(key, (String) newValue);
		} else if (newValue instanceof Integer) {
			editor.putInt(key, (Integer) newValue);
		} else if (newValue instanceof Float) {
			editor.putFloat(key, (Float) newValue);
		} else if (newValue instanceof Long) {
			editor.putLong(key, (Long) newValue);
		} else if (newValue instanceof Set<?>) {
			//editor.putStringSet(key, (Set<String>) newValue);
			saved = false;
		} else {
			saved = false;
		}

		editor.apply();

		return saved;
	}
}