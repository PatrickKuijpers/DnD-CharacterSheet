package nl.tcilegnar.dndcharactersheet.FileStorage;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileOutputStream;
import java.io.IOException;

import nl.tcilegnar.dndcharactersheet.App;

public class Storage {
	public static final int DEF_VALUE = 0;

	public enum Settings {
		Experience
	}

	public enum Key {
		CURRENT_EXP
	}

	public enum FileName {
		TEST_FILE
	}

	public void saveSharedPreference(Key key, int value) {
		SharedPreferences settings = App.getContext().getSharedPreferences(Settings.Experience.name(), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key.name(), value);
		editor.apply();
	}

	public int getSharedPreference(Key key) {
		SharedPreferences settings = App.getContext().getSharedPreferences(Settings.Experience.name(), Context.MODE_PRIVATE);
		return settings.getInt(key.name(), DEF_VALUE);
	}

	public void saveFileDataTest() throws IOException {
		int homeScore = 0;
		byte[] homeScoreBytes = new byte[0];

		homeScoreBytes[0] = (byte) homeScore;
		homeScoreBytes[1] = (byte) (homeScore >> 8);
		homeScoreBytes[2] = (byte) (homeScore >> 16);

		FileOutputStream outputStream = App.getContext().openFileOutput(FileName.TEST_FILE.name(), Context.MODE_PRIVATE);
		outputStream.write(homeScoreBytes);
		outputStream.close();
	}
}
