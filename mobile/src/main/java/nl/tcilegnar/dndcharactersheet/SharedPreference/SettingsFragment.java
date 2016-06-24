package nl.tcilegnar.dndcharactersheet.SharedPreference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener {

	private final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

		setPreferenceChangeListeners();
	}

	private void setPreferenceChangeListeners() {
		ListPreference prefExp = (ListPreference) findPreference(getString(R.string.setting_key_experience_update_steps));
		prefExp.setOnPreferenceChangeListener(this);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		SettingCollector.getPreference(key);

		SharedPreferences settings = App.getContext().getSharedPreferences(key, 0);
		Editor editor = settings.edit();

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
			return false;
		}

		editor.apply();

		return true;
	}
}