package nl.tcilegnar.dndcharactersheet.Storage;

import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Level.Level;

public class Storage extends SharedPrefs {
    @Override
    protected String fileName() {
        // Deze filelocatie nooit veranderen!
        return "Storage";
    }

    // TODO (Patrick): public voor unittest
    public enum Key {
        // Deze enums nooit veranderen!
        CURRENT_EXP(Experience.EXP_MIN),
        CURRENT_LEVEL(Level.MIN_LEVEL),
        READY_FOR_LEVEL_CHANGE(0),
        PLATINUM(0),
        GOLD(0),
        SILVER(0),
        BRONZE(0);

        public final int defaultValue;

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

    public void savePlatinum(int value) {
        Key key = Key.PLATINUM;
        save(key.name(), value);
    }

    public int loadPlatinum() {
        Key key = Key.PLATINUM;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveGold(int value) {
        Key key = Key.GOLD;
        save(key.name(), value);
    }

    public int loadGold() {
        Key key = Key.GOLD;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveSilver(int value) {
        Key key = Key.SILVER;
        save(key.name(), value);
    }

    public int loadSilver() {
        Key key = Key.SILVER;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveBronze(int value) {
        Key key = Key.BRONZE;
        save(key.name(), value);
    }

    public int loadBronze() {
        Key key = Key.BRONZE;
        return loadInt(key.name(), key.defaultValue);
    }
}
