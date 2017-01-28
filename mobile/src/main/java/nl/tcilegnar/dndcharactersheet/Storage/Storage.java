package nl.tcilegnar.dndcharactersheet.Storage;

import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Utils.Log;
import nl.tcilegnar.dndcharactersheet.abilities.entities.Ability;

public class Storage extends SharedPrefs {
    private final String fileName;

    public Storage() {
        this(CharacterSettings.getInstance().loadCurrentCharacterId());
        Log.d("TEST", "Storage fileName" + fileName);
    }

    public Storage(String filename) {
        this.fileName = filename;
    }

    @Override
    protected String fileName() {
        return fileName;
    }

    public enum Key {
        // Deze enums nooit veranderen!
        CHARACTER_NAME(0),
        CURRENT_EXP(Experience.EXP_MIN),
        CURRENT_LEVEL(Level.MIN_LEVEL),
        READY_FOR_LEVEL_CHANGE(0),
        STRENGTH(Ability.DEFAULT_VALUE),
        DEXTERITY(Ability.DEFAULT_VALUE),
        CONSTITUTION(Ability.DEFAULT_VALUE),
        WISDOM(Ability.DEFAULT_VALUE),
        INTELLIGENCE(Ability.DEFAULT_VALUE),
        CHARISMA(Ability.DEFAULT_VALUE),
        PLATINUM(0),
        GOLD(0),
        SILVER(0),
        BRONZE(0),
        TOTAL_HP(0),
        CURRENT_HP(0),
        TEMP_HP(0);

        public final int defaultValue;

        Key(int defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public void saveCharacterName(String value) {
        Key key = Key.CHARACTER_NAME;
        save(key.name(), value);
    }

    public String loadCharacterName() {
        Key key = Key.CHARACTER_NAME;
        String defaultValue = "My Character Name"; // TODO: generics?
        return loadString(key.name(), defaultValue);
    }

    public void saveExperience(int value) {
        Key key = Key.CURRENT_EXP;
        save(key.name(), value);
    }

    public int loadExperience() { // TODO: long ipv int?
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

    /**
     * In tegenstelling tot andere save & load methoden is bij deze de key als extra parameter vereist. Dit is gedaan om
     * te voorkomen dat voor elke ability een aparte methode gedefinieerd moet worden
     */
    public void saveAbility(Key key, int value) {
        save(key.name(), value);
    }

    public int loadAbility(Key key) {
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

    public void saveTotalHp(int value) {
        Key key = Key.TOTAL_HP;
        save(key.name(), value);
    }

    public int loadTotalHp() {
        Key key = Key.TOTAL_HP;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveCurrentHp(int value) {
        Key key = Key.CURRENT_HP;
        save(key.name(), value);
    }

    public int loadCurrentHp() {
        Key key = Key.CURRENT_HP;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveTempHp(int value) {
        Key key = Key.TEMP_HP;
        save(key.name(), value);
    }

    public int loadTempHp() {
        Key key = Key.TEMP_HP;
        return loadInt(key.name(), key.defaultValue);
    }
}
