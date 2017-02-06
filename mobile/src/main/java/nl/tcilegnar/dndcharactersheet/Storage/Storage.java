package nl.tcilegnar.dndcharactersheet.Storage;

import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Health.Hp;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.abilities.entities.Ability;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;
import nl.tcilegnar.dndcharactersheet.characters.DnDCharacter;

public class Storage extends SharedPrefs {

    protected final String characterId;

    public Storage() {
        this(CurrentCharacter.DnDCharacter().getId());
    }

    public Storage(String characterId) {
        this.characterId = characterId;
    }

    @Override
    protected String fileName() {
        return characterId;
    }

    public enum Key {
        // Deze enums nooit veranderen!
        CHARACTER_NAME(DnDCharacter.DEFAULT_NAME),
        RACE(DnDCharacter.DEFAULT_RACE),
        CLASS(DnDCharacter.DEFAULT_CLASS),
        ALIGNMENT(DnDCharacter.DEFAULT_ALIGNMENT),
        DEITY(DnDCharacter.DEFAULT_DEITY),
        GENDER(DnDCharacter.DEFAULT_GENDER),
        AGE(DnDCharacter.DEFAULT_AGE),
        HEIGHT(DnDCharacter.DEFAULT_HEIGHT),
        WEIGHT(DnDCharacter.DEFAULT_WEIGHT),
        HAIR(DnDCharacter.DEFAULT_HAIR),
        EYES(DnDCharacter.DEFAULT_EYES),

        CURRENT_EXP(Experience.EXP_MIN),
        CURRENT_LEVEL(Level.MIN_LEVEL),
        READY_FOR_LEVEL_CHANGE(0),

        STRENGTH(Ability.DEFAULT_VALUE),
        DEXTERITY(Ability.DEFAULT_VALUE),
        CONSTITUTION(Ability.DEFAULT_VALUE),
        WISDOM(Ability.DEFAULT_VALUE),
        INTELLIGENCE(Ability.DEFAULT_VALUE),
        CHARISMA(Ability.DEFAULT_VALUE),
        STRENGTH_TEMP(Ability.DEFAULT_VALUE_TEMP),
        DEXTERITY_TEMP(Ability.DEFAULT_VALUE_TEMP),
        CONSTITUTION_TEMP(Ability.DEFAULT_VALUE_TEMP),
        WISDOM_TEMP(Ability.DEFAULT_VALUE_TEMP),
        INTELLIGENCE_TEMP(Ability.DEFAULT_VALUE_TEMP),
        CHARISMA_TEMP(Ability.DEFAULT_VALUE_TEMP),

        TOTAL_HP(Hp.DEFAULT_TOTAL),
        CURRENT_HP(Hp.DEFAULT_CURRENT),
        TEMP_HP(Hp.DEFAULT_TEMP),

        PLATINUM(0),
        GOLD(0),
        SILVER(0),
        BRONZE(0);

        public final String defaultValue;

        Key(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        Key(int defaultValue) {
            this.defaultValue = String.valueOf(defaultValue);
        }
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

    public void saveAbilityTemp(Key key, int value) {
        saveAbility(key, value);
    }

    public int loadAbilityTemp(Key key) {
        return loadAbility(key);
    }

    public void saveHasAbilityTemp(Key key, int value) {
        saveAbility(key, value);
    }

    public int loadHasAbilityTemp(Key key) {
        return loadAbility(key);
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
