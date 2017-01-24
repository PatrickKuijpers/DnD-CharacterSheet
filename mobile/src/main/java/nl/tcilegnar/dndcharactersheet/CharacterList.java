package nl.tcilegnar.dndcharactersheet;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import nl.tcilegnar.dndcharactersheet.Storage.CharacterSettings;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public enum CharacterList {
    INSTANCE;

    public List<String> getCharacterNames() {
        ArrayList<String> characterNames = new ArrayList<>();
        TreeSet<String> ids = CharacterSettings.getInstance().loadCharacterIds();
        for (String id : ids) {
            String name = new Storage(id).loadCharacterName();
            characterNames.add(id);
        }
        return characterNames;
    }
}
