package nl.tcilegnar.dndcharactersheet.Storage;

public class BasicCharacterInfo extends Storage {
    private static final String FILE_NAME_EXTENSION = "_BasicCharacterInfo";

    public BasicCharacterInfo() {
        super();
    }

    public BasicCharacterInfo(String characterId) {
        super(characterId);
    }

    @Override
    protected String fileName() {
        return characterId + FILE_NAME_EXTENSION;
    }

    public void saveName(String value) {
        Key key = Key.CHARACTER_NAME;
        save(key.name(), value);
    }

    public String loadName() {
        Key key = Key.CHARACTER_NAME;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveRace(String value) {
        Key key = Key.RACE;
        save(key.name(), value);
    }

    public String loadRace() {
        Key key = Key.RACE;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveClassName(String value) {
        Key key = Key.CLASS;
        save(key.name(), value);
    }

    public String loadClassName() {
        Key key = Key.CLASS;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveAlignment(String value) {
        Key key = Key.ALIGNMENT;
        save(key.name(), value);
    }

    public String loadAlignment() {
        Key key = Key.ALIGNMENT;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveDeity(String value) {
        Key key = Key.DEITY;
        save(key.name(), value);
    }

    public String loadDeity() {
        Key key = Key.DEITY;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveGender(String value) {
        Key key = Key.GENDER;
        save(key.name(), value);
    }

    public String loadGender() {
        Key key = Key.GENDER;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveAge(int value) {
        Key key = Key.AGE;
        save(key.name(), value);
    }

    public int loadAge() {
        Key key = Key.AGE;
        return loadInt(key.name(), key.defaultValue);
    }

    public void saveHeight(String value) {
        Key key = Key.HEIGHT;
        save(key.name(), value);
    }

    public String loadHeight() {
        Key key = Key.HEIGHT;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveWeight(String value) {
        Key key = Key.WEIGHT;
        save(key.name(), value);
    }

    public String loadWeight() {
        Key key = Key.WEIGHT;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveHairColor(String value) {
        Key key = Key.HAIR;
        save(key.name(), value);
    }

    public String loadHairColor() {
        Key key = Key.HAIR;
        return loadString(key.name(), key.defaultValue);
    }

    public void saveEyeColor(String value) {
        Key key = Key.EYES;
        save(key.name(), value);
    }

    public String loadEyeColor() {
        Key key = Key.EYES;
        return loadString(key.name(), key.defaultValue);
    }
}
