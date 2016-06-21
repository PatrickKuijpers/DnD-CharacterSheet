package nl.tcilegnar.dndcharactersheet.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import nl.tcilegnar.dndcharactersheet.R;

public class Settings extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
}