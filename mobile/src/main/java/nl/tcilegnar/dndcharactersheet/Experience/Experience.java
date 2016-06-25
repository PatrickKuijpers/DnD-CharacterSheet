package nl.tcilegnar.dndcharactersheet.Experience;

import java.io.Serializable;

import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceUpdater.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.FileStorage.Storage;

public class Experience implements Serializable, ExperienceUpdateListener {
	private static final int EXP_MAX = 2500;

	private int currentExp = Storage.getSharedPreference(Storage.Key.CURRENT_EXP);
	private ExperienceUpdateFinishedListener experienceUpdateFinishedListener;

	public int getMax() {
		return EXP_MAX;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public int updateExperience(int expUpdateValue) {
		currentExp = currentExp + expUpdateValue;
		return currentExp;
	}

	public void saveExp() {
		Storage.saveSharedPreference(Storage.Key.CURRENT_EXP, currentExp);
	}

	@Override
	public void onUpdateExperience(int expUpdateValue) {
		int newExp = updateExperience(expUpdateValue);
		experienceUpdateFinishedListener.onExperienceUpdated(newExp);
	}

	public void setExperienceUpdateFinishedListener(ExperienceUpdateFinishedListener experienceUpdateFinishedListener) {
		this.experienceUpdateFinishedListener = experienceUpdateFinishedListener;
	}

	public interface ExperienceUpdateFinishedListener {
		void onExperienceUpdated(int newExp);
	}
}
