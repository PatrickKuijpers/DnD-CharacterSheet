package nl.tcilegnar.dndcharactersheet.Storage;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class CharacterSettings extends SharedPrefs {
    private static final TreeSet<String> DEFAULT_CHARACTER_IDS = new TreeSet<>(Collections.reverseOrder());
    private static final int FIRST_CHARACTER_INDEX = 1001; // Nooit wijzigen!!!
    private static final String CHARACTER_ID_PREFIX = "character"; // Nooit wijzigen!!!
    private static final String FIRST_CHARACTER_ID = createCharacterId(FIRST_CHARACTER_INDEX); // Nooit wijzigen!!!

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

    public void addCharacter() {
        addCharacter(getNewCharacterId());
    }

    private String getNewCharacterId() {
        TreeSet<String> existingCharacterIds = loadCharacterIds();
        String lastId = existingCharacterIds.first();
        int lastCharacterIndex = getCharacterIndex(lastId);
        lastCharacterIndex++;
        return createCharacterId(lastCharacterIndex);
    }

    @NonNull // Nooit wijzigen!!!
    private static String createCharacterId(int lastCharacterSuffix) {
        return CHARACTER_ID_PREFIX + lastCharacterSuffix;
    }

    public static int getCharacterIndex(String characterId) {
        return Integer.valueOf(characterId.substring(CHARACTER_ID_PREFIX.length()));
    }

    public void addCharacter(String newCharacterId) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.add(newCharacterId);
        saveCharacterIds(currentCharacterIds);

        switchCharacter(newCharacterId);
    }

    public void removeCharacter(int index) {
        String characterId = createCharacterId(index);
        removeCharacter(characterId);
    }

    public void removeCharacter(String characterId) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.remove(characterId);
        saveCharacterIds(currentCharacterIds);

        boolean isActiveCharacterRemoved = characterId.equals(loadCurrentCharacterId());
        if (isActiveCharacterRemoved) {
            checkFirstCharacter();
            switchCharacter(loadCharacterIds().iterator().next());
        }
    }

    public void switchCharacter(int characterIndex) {
        switchCharacter(createCharacterId(characterIndex));
    }

    public void switchCharacter(String characterId) {
        saveCurrentCharacterId(characterId);
    }

    public TreeSet<String> loadCharacterIds() {
        Set<String> unsortedCharacterIds = loadStringSet(Key.CHARACTER_IDS.name(), DEFAULT_CHARACTER_IDS);
        TreeSet<String> sortedCharacterIds = new TreeSet<>(Collections.reverseOrder());
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
