package nl.tcilegnar.dndcharactersheet.characters.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.BasicCharacterInfo;
import nl.tcilegnar.dndcharactersheet.Storage.SharedPrefs;
import nl.tcilegnar.dndcharactersheet.Utils.Res;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;
import nl.tcilegnar.dndcharactersheet.characters.DnDCharacter;

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
            addCharacter(FIRST_CHARACTER_ID, DnDCharacter.DEFAULT_NAME);
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

    public void addCharacter(Context activityContext) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activityContext);

        dialog.setTitle(Res.getString(R.string.dialog_title_new_character));
        dialog.setMessage(Res.getString(R.string.dialog_message_new_character));

        final EditText edittext = new EditText(activityContext);
        dialog.setView(edittext);

        dialog.setPositiveButton(Res.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edittext.getText().toString();
                addCharacter(name);
            }
        });

        dialog.setNeutralButton(Res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // TODO: mainMenuActivity.selectCurrentCharacter();
            }
        });

        dialog.show();
    }

    private void addCharacter(String name) {
        addCharacter(getNewCharacterId(), name);
    }

    private String getNewCharacterId() {
        int firstCharacterId = Integer.valueOf(getFirstCharacterId());
        firstCharacterId++;
        return String.valueOf(firstCharacterId);
    }

    private String getFirstCharacterId() {
        return loadCharacterIds().first();
    }

    private void addCharacter(String newCharacterId, String name) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.add(newCharacterId);
        saveCharacterIds(currentCharacterIds);

        new BasicCharacterInfo(newCharacterId).saveName(name);

        switchCharacter(newCharacterId);
    }

    public void deleteCharacter(Context activityContext) {
        final CurrentCharacter currentCharacter = CurrentCharacter.DnDCharacter();

        AlertDialog.Builder dialog = new AlertDialog.Builder(activityContext);

        dialog.setTitle(Res.getString(R.string.dialog_title_delete_character));
        dialog.setMessage(String.format(Res.getString(R.string.dialog_message_delete_character), currentCharacter
                .getName()));

        dialog.setPositiveButton(Res.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteCharacter(currentCharacter.getId());
            }
        });

        dialog.setNegativeButton(Res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        dialog.show();
    }

    private void deleteCharacter(String characterId) {
        TreeSet<String> currentCharacterIds = loadCharacterIds();
        currentCharacterIds.remove(characterId);
        saveCharacterIds(currentCharacterIds);

        boolean isActiveCharacterRemoved = characterId.equals(loadCurrentCharacterId());
        if (isActiveCharacterRemoved) {
            makeSureAnyCharacterExists();
            switchCharacter(getFirstCharacterId());
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

    public void switchCharacter(String characterId) {
        if (!characterId.equals(loadCurrentCharacterId())) {
            saveCurrentCharacterId(characterId);
            App.restart();
        }
    }

    public String loadCurrentCharacterId() {
        return loadString(Key.CURRENT_CHARACTER_ID.name()); // zou altijd moeten bestaan (default FIRST_CHARACTER_ID?)
    }

    private void saveCurrentCharacterId(String characterId) {
        save(Key.CURRENT_CHARACTER_ID.name(), characterId);
    }
}
