package nl.tcilegnar.dndcharactersheet.Storage;

import android.preference.Preference;
import android.support.annotation.StringRes;

import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class Settings extends SharedPrefs {
	@Override
	protected String fileName() {
		return "Settings";
	}

	private enum DefaultValue {
		EXP_UPDATE_TYPE(R.string.setting_defaultvalue_experience_update_type),
		EXP_UPDATE_PICKER_STEPSIZE(R.string.setting_defaultvalue_experience_update_picker_steps);

		private final String value;

		DefaultValue(@StringRes int resId) {
			value = App.getContext().getString(resId);
		}
	}

	public boolean isExperienceUpdateTypeInput() {
		return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_input));
	}

	public boolean isExperienceUpdateTypeNumberPicker() {
		return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_numberpicker));
	}

	private String getExperienceUpdateType() {
		String key = getKey(R.string.setting_key_experience_update_type);
		return loadString(key, DefaultValue.EXP_UPDATE_TYPE.value);
	}

	public int getExperiencePickerStepSize() {
		String key = getKey(R.string.setting_key_experience_update_picker_steps);
		String experiencePickerStepSize = loadString(key, DefaultValue.EXP_UPDATE_PICKER_STEPSIZE.value);
		return Integer.valueOf(experiencePickerStepSize);
	}

	public boolean savePreferenceValue(Preference preference, Object newValue) {
		String key = preference.getKey();
		boolean isSaved = true;
		if (newValue instanceof Boolean) {
			save(key, (Boolean) newValue);
		} else if (newValue instanceof String) {
			save(key, (String) newValue);
		} else if (newValue instanceof Integer) {
			save(key, (Integer) newValue);
		} else if (newValue instanceof Float) {
			save(key, (Float) newValue);
		} else if (newValue instanceof Long) {
			save(key, (Long) newValue);
		} else if (newValue instanceof Set<?>) {
			save(key, (Set<String>) newValue);
		} else {
			isSaved = false;
		}
		return isSaved;
	}
}