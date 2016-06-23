package nl.tcilegnar.dndcharactersheet.SharedPreference;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class SettingCollector {
	private static final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());

	private enum DefaultValue {
		EXP_PICKER_STEPSIZE(R.string.setting_defaultvalue_experience_update);

		private final String value;

		DefaultValue(int resId) {
			this.value = getString(resId);
		}

		public String value() {
			return value;
		}
	}

	public static int getExperiencePickerStepSize() {
		String key = getKey(R.string.setting_key_experience_update_steps);
		String experiencePickerStepSize = getPreference(key, DefaultValue.EXP_PICKER_STEPSIZE);
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
