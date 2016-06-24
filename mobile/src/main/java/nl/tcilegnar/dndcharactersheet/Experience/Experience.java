package nl.tcilegnar.dndcharactersheet.Experience;

import java.io.Serializable;

import nl.tcilegnar.dndcharactersheet.FileStorage.Storage;

public class Experience implements Serializable {
	private static final int EXP_MAX = 2500;

	private int currentExp = Storage.getSharedPreference(Storage.Key.CURRENT_EXP);

	public int getMax() {
		return EXP_MAX;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public void saveExp() {
		Storage.saveSharedPreference(Storage.Key.CURRENT_EXP, currentExp);
	}

	public void addExperience(int addedExperience) {
		currentExp = currentExp + addedExperience;
	}

	public void subtractExperience(int subtractedExperience) {
		currentExp = currentExp - subtractedExperience;
	}
}
