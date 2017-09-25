package nl.tcilegnar.dndcharactersheet.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import nl.tcilegnar.dndcharactersheet.characters.settings.CharacterSettings;

public enum CharacterList {
    INSTANCE;

    private List<DnDCharacter> characters;

    public List<DnDCharacter> getCharacters() {
        if (characters == null) {
            characters = new ArrayList<>();
            TreeSet<String> ids = CharacterSettings.getInstance().loadCharacterIds();
            for (String id : ids) {
                DnDCharacter character = new DnDCharacter(id);
                characters.add(character);
            }
        }
        return characters;
    }

    public List<String> getCharacterNames() {
        ArrayList<String> characterNames = new ArrayList<>();
        for (DnDCharacter character : getCharacters()) {
            String name = character.getName();
            characterNames.add(name);
        }
        return characterNames;
    }
}
