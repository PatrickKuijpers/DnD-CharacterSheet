package nl.tcilegnar.dndcharactersheet.characters;

import nl.tcilegnar.dndcharactersheet.Storage.BasicCharacterInfo;

public class DnDCharacter {
    public static final String DEFAULT_NAME = "Character Name";
    public static final String DEFAULT_RACE = "";
    public static final String DEFAULT_CLASS = "";
    public static final String DEFAULT_ALIGNMENT = "";
    public static final String DEFAULT_DEITY = "";
    public static final String DEFAULT_GENDER = Gender.MALE.toString();
    public static final int DEFAULT_AGE = 0;
    public static final String DEFAULT_HEIGHT = "";
    public static final String DEFAULT_WEIGHT = "";
    public static final String DEFAULT_HAIR = "";
    public static final String DEFAULT_EYES = "";

    private final String id;
    private final BasicCharacterInfo storage;

    public enum Gender {
        MALE, FEMALE
    }

    public DnDCharacter(String id) {
        this.id = id;
        this.storage = new BasicCharacterInfo(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return storage.loadName();
    }

    public void setName(String value) {
        storage.saveName(value);
    }

    public String getRace() {
        return storage.loadRace();
    }

    public void setRace(String value) {
        storage.saveRace(value);
    }

    public String getClassName() {
        return storage.loadClassName();
    }

    public void setClassName(String value) {
        storage.saveClassName(value);
    }

    public String getAlignment() {
        return storage.loadAlignment();
    }

    public void setAlignment(String value) {
        storage.saveAlignment(value);
    }

    public String getDeity() {
        return storage.loadDeity();
    }

    public void setDeity(String value) {
        storage.saveDeity(value);
    }

    public Gender getGender() {
        return Gender.valueOf(storage.loadGender());
    }

    public void setGender(Gender value) {
        storage.saveGender(value.name());
    }

    public int getAge() {
        return storage.loadAge();
    }

    public void setAge(int value) {
        storage.saveAge(value);
    }

    public String getHeight() {
        return storage.loadHeight();
    }

    public void setHeight(String value) {
        storage.saveHeight(value);
    }

    public String getWeight() {
        return storage.loadWeight();
    }

    public void setWeight(String value) {
        storage.saveWeight(value);
    }

    public String getHairColor() {
        return storage.loadHairColor();
    }

    public void setHairColor(String value) {
        storage.saveHairColor(value);
    }

    public String getEyeColor() {
        return storage.loadEyeColor();
    }

    public void setEyeColor(String value) {
        storage.saveEyeColor(value);
    }
}
