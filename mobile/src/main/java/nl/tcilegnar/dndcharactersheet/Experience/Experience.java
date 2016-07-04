package nl.tcilegnar.dndcharactersheet.Experience;

import java.io.Serializable;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Experience implements Serializable {
	private static final int EXP_MAX = 2500;
	private Storage storage;
	private int currentExp = 0;

	public Experience() {
		this(new Storage());
	}

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

	public int updateExperience(int expUpdateValue) throws ExpTooLowException, ExpTooHighException {
		int newExp = currentExp + expUpdateValue;
		validateUpdate(expUpdateValue, newExp);
		currentExp = newExp;
		return currentExp;
	}

	private void validateUpdate(int expUpdateValue, int newExp) throws ExpTooLowException, ExpTooHighException {
		if (newExp < 0) {
			throw new ExpTooLowException("Nieuwe exp-waarde is te laag: " + currentExp + " + " + expUpdateValue + " = " + newExp);
		}
		if (newExp > EXP_MAX) {
			throw new ExpTooHighException("Nieuwe exp-waarde is te hoog: " + currentExp + " + " + expUpdateValue + " = " + newExp);
		}
	}

	public void saveExp() {
		storage.saveExperience(currentExp);
	}

	public class ExpTooLowException extends Exception {
		public ExpTooLowException(String message) {
			super(message);
		}
	}

	public class ExpTooHighException extends Exception {
		public ExpTooHighException(String message) {
			super(message);
		}
	}
}
