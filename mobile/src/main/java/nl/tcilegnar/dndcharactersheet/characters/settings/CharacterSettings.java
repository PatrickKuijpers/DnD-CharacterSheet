package nl.tcilegnar.dndcharactersheet.characters.settings;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Storage.SharedPrefs;

public class CharacterSettings extends SharedPrefs {
    private static final Comparator<String> CHARACTER_ID_COMPERATOR = Collections.reverseOrder();
    private static final TreeSet<String> DEFAULT_CHARACTER_IDS = new TreeSet<>(CHARACTER_ID_COMPERATOR);
    private static final String FIRST_CHARACTER_ID = "10001"; // Nooit wijzigen!!!

    private static CharacterSettings instance;

    public static CharacterSettings getInstance() {
        if (instance == null) {
            instance = new CharacterSettings();
        }
        return instance;
    }

    private CharacterSettings() {
        makeSureAnyCharacterExists();
    }

    private void makeSureAnyCharacterExists() {
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

    public void addCharacter() {
        addCharacter(getNewCharacterId());
    }

    private String getNewCharacterId() {
        int firstCharacterId = Integer.valueOf(getFirstCharacterId());
        firstCharacterId++;
        return String.valueOf(firstCharacterId);
    }

    private String getFirstCharacterId() {
        return loadCharacterIds().first();
    }

    private void addCharacter(String newCharacterId) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.add(newCharacterId);
        saveCharacterIds(currentCharacterIds);

        switchCharacter(newCharacterId);
    }

    public void removeCharacter(String characterId) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.remove(characterId);
        saveCharacterIds(currentCharacterIds);

        boolean isActiveCharacterRemoved = characterId.equals(loadCurrentCharacterId());
        if (isActiveCharacterRemoved) {
            makeSureAnyCharacterExists();
            switchCharacter(getFirstCharacterId());
        }
    }

    public void switchCharacter(String characterId) {
        if (!characterId.equals(loadCurrentCharacterId())) {
            saveCurrentCharacterId(characterId);
            App.restart();
        }
    }

    public TreeSet<String> loadCharacterIds() {
        Set<String> unsortedCharacterIds = loadStringSet(Key.CHARACTER_IDS.name(), DEFAULT_CHARACTER_IDS);
        TreeSet<String> sortedCharacterIds = new TreeSet<>(CHARACTER_ID_COMPERATOR);
        sortedCharacterIds.addAll(unsortedCharacterIds);
        return sortedCharacterIds;
    }

    private void saveCharacterIds(TreeSet<String> characterIds) {
        save(Key.CHARACTER_IDS.name(), characterIds);
    }

    public String loadCurrentCharacterId() {
        return loadString(Key.CURRENT_CHARACTER_ID.name()); // zou altijd moeten bestaan (default FIRST_CHARACTER_ID?)
    }

    private void saveCurrentCharacterId(String characterId) {
        save(Key.CURRENT_CHARACTER_ID.name(), characterId);
    }
}
