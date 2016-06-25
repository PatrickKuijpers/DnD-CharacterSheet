package nl.tcilegnar.dndcharactersheet.SharedPreference;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class SettingCollector {
	private static final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());

	private enum DefaultValue {
		EXP_UPDATE_TYPE(R.string.setting_defaultvalue_experience_update_type),
		EXP_UPDATE_PICKER_STEPSIZE(R.string.setting_defaultvalue_experience_update_picker_steps);

		private final String value;

		DefaultValue(int resId) {
			this.value = getString(resId);
		}

		public String value() {
			return value;
		}
	}

	public static boolean isExperienceUpdateTypeInput() {
		return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_input));
	}

	public static String getExperienceUpdateType() {
		String key = getKey(R.string.setting_key_experience_update_type);
		return getPreference(key, DefaultValue.EXP_UPDATE_TYPE);
	}

	public static int getExperiencePickerStepSize() {
		String key = getKey(R.string.setting_key_experience_update_picker_steps);
		String experiencePickerStepSize = getPreference(key, DefaultValue.EXP_UPDATE_PICKER_STEPSIZE);
		return Integer.valueOf(experiencePickerStepSize);
	}

	public static String getPreference(String key, DefaultValue defaultValue) {
		return prefs.getString(key, defaultValue.value());
	}

	public static String getPreference(String key) {
		return prefs.getString(key, "");
	}

	private static String getKey(int resId) {
		return getString(resId);
	}

	private static String getString(int resId) {
		return App.getContext().getString(resId);
	}
}
