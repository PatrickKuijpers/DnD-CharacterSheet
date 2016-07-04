package nl.tcilegnar.dndcharactersheet.Storage;

public class Storage extends SharedPrefs {
	@Override
	protected String fileName() {
		return "Storage";
	}

	protected enum Key {
		CURRENT_EXP(0);

		protected final int defaultValue;

		Key(int defaultValue) {
			this.defaultValue = defaultValue;
		}
	}

	public void saveExperience(int value) {
		saveIntFromKey(Key.CURRENT_EXP, value);
	}

	public int loadExperience() {
		return getIntFromKey(Key.CURRENT_EXP);
	}

	protected void saveIntFromKey(Key key, int value) {
		save(key.name(), value);
	}

	protected int getIntFromKey(Key key) {
		return loadInt(key.name(), key.defaultValue);
	}
}
