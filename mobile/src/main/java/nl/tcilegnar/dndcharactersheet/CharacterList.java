package nl.tcilegnar.dndcharactersheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.Storage.CharacterSettings;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public enum CharacterList {
    INSTANCE;

    public List<String> getCharacterNames() {
        ArrayList<String> characterNames = new ArrayList<>();
        Set<String> ids = CharacterSettings.getInstance().loadCharacterIds();
        for (String id : ids) {
            String name = new Storage(id).loadCharacterName();
            characterNames.add(name);
        }
        return characterNames;
    }
}
