package nl.tcilegnar.dndcharactersheet.Storage;

import java.util.HashSet;
import java.util.Set;

public class CharacterSettings extends SharedPrefs {
    private static final Set<String> DEFAULT_CHARACTER_IDS = new HashSet<>();
    private static final int FIRST_CHARACTER_INDEX = 0;
    private static final String FIRST_CHARACTER_ID = "character" + FIRST_CHARACTER_INDEX;

    private static CharacterSettings instance;

    public static CharacterSettings getInstance() {
        if (instance == null) {
            instance = new CharacterSettings();
        }
        return instance;
    }

    private CharacterSettings() {
        checkFirstCharacter();
    }

    private void checkFirstCharacter() {
        boolean isFirstCharacter = loadCharacterIds().isEmpty();
        if (isFirstCharacter) {
            addCharacter(FIRST_CHARACTER_ID);
        }
    }

    public void tearDown() {
        instance = null;
    }

    @Override
    protected String fileName() {
        return SharedPrefs.ROOT;
    }

    private enum Key {
        CHARACTER_IDS,
        CURRENT_CHARACTER_ID
    }

    public void addCharacter(String newCharacterId) {
        Set<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.add(newCharacterId);
        saveCharacterIds(currentCharacterIds);

        switchCharacter(newCharacterId);
    }

    public void removeCharacter(String characterId) {
        Set<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.remove(characterId);
        saveCharacterIds(currentCharacterIds);
    }

    public void switchCharacter(String characterId) {
        saveCurrentCharacterId(characterId);
    }

    public Set<String> loadCharacterIds() {
        return loadStringSet(Key.CHARACTER_IDS.name(), DEFAULT_CHARACTER_IDS);
    }

    private void saveCharacterIds(Set<String> characterIds) {
        save(Key.CHARACTER_IDS.name(), characterIds);
    }

    public String loadCurrentCharacterId() {
        return loadString(Key.CURRENT_CHARACTER_ID.name()); // zou altijd moeten bestaan (default FIRST_CHARACTER_ID?)
    }

    private void saveCurrentCharacterId(String characterId) {
        save(Key.CURRENT_CHARACTER_ID.name(), characterId);
    }
}
