package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;

import java.io.Serializable;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Experience implements Serializable {
	private static final int EXP_MAX = 2500;
	private Storage storage;
	private int currentExp = 0;
	private ExperienceEdgeListener experienceEdgeListener;

	public Experience() {
		this(new Storage());
	}

	@VisibleForTesting
	protected Experience(Storage storage) {
		this.storage = storage;
		this.currentExp = storage.loadExperience();
	}

	public int getMax() {
		return EXP_MAX;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public int updateExperience(int expUpdateValue) throws ExpTooLowException {
		int newExp = currentExp + expUpdateValue;
		validateUpdate(expUpdateValue, newExp);

		newExp = correctExperienceWhenMaxIsReached(newExp);

		currentExp = newExp;
		return currentExp;
	}

	private void validateUpdate(int expUpdateValue, int newExp) throws ExpTooLowException {
		if (newExp < 0) {
			throw new ExpTooLowException("Nieuwe exp-waarde is te laag: " + currentExp + " + " + expUpdateValue + " = " + newExp);
		}
	}

	private int correctExperienceWhenMaxIsReached(int newExp) {
		if (isMaxExperienceReached(newExp)) {
			experienceEdgeListener.onExperienceMaxReached();
			newExp = newExp - EXP_MAX;
		}
		return newExp;
	}

	private boolean isMaxExperienceReached(int newExp) {
		return newExp > EXP_MAX;
	}

	public void saveExp() {
		storage.saveExperience(currentExp);
	}

	public void setExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
		this.experienceEdgeListener = experienceEdgeListener;
	}

	public interface ExperienceEdgeListener {
		void onExperienceMaxReached();
	}

	public class ExpTooLowException extends Exception {
		public ExpTooLowException(String message) {
			super(message);
		}
	}
}
