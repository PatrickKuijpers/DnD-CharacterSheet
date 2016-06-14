package nl.tcilegnar.dndcharactersheet.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileOutputStream;
import java.io.IOException;

import nl.tcilegnar.dndcharactersheet.App;

public class Storage {
	private static final String SETTINGS_EXP = "Experience";
	private static final int PREF_NOT_FOUND = -1;
	public static final int DEF_VALUE = 0;

	public enum Key {
		CURRENT_EXP
	}

	public enum Name {
		TEST_FILE
	}

	public static void saveSharedPreference(Key key, int value) {
		SharedPreferences settings = App.getContext().getSharedPreferences(SETTINGS_EXP, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key.name(), value);

		// Apply the edits!
		editor.apply();
	}

	public static int getSharedPreference(Key key) {
		SharedPreferences settings = App.getContext().getSharedPreferences(SETTINGS_EXP, 0);
		return settings.getInt(key.name(), DEF_VALUE);
	}

	public static void saveFileDataTest() throws IOException {
		int homeScore;
		byte[] homeScoreBytes;

		homeScoreBytes[0] = (byte) homeScore;
		homeScoreBytes[1] = (byte) (homeScore >> 8);
		homeScoreBytes[2] = (byte) (homeScore >> 16);

		FileOutputStream outputStream = App.getContext().openFileOutput(Name.TEST_FILE.name(), Context.MODE_PRIVATE);
		outputStream.write(homeScoreBytes);
		outputStream.close();
	}
}
