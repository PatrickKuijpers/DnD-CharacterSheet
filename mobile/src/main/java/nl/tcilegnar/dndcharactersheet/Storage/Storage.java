package nl.tcilegnar.dndcharactersheet.Storage;

import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Level.Level;

public class Storage extends SharedPrefs {
    @Override
    protected String fileName() {
        return "Storage";
    }

    protected enum Key {
        CURRENT_EXP(Experience.EXP_MIN),
        CURRENT_LEVEL(Level.MIN_LEVEL),
        READY_FOR_LEVEL_CHANGE(0);

        protected final int defaultValue;

        Key(int defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public void saveExperience(int value) {
        Key key = Key.CURRENT_EXP;
        save(key.name(), value);
    }

    public int loadExperience() {
        Key key = Key.CURRENT_EXP;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveLevel(int value) {
        Key key = Key.CURRENT_LEVEL;
        save(key.name(), value);
    }

    public int loadLevel() {
        Key key = Key.CURRENT_LEVEL;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveReadyForLevelChange(int value) {
        Key key = Key.READY_FOR_LEVEL_CHANGE;
        save(key.name(), value);
    }

    public int loadReadyForLevelChange() {
        Key key = Key.READY_FOR_LEVEL_CHANGE;
        return loadInt(key.name(), key.defaultValue);
    }
}
