package nl.tcilegnar.dndcharactersheet.characters;

import nl.tcilegnar.dndcharactersheet.characters.settings.CharacterSettings;

public class CurrentCharacter extends DnDCharacter {
    private static CurrentCharacter currentCharacter;

    public static CurrentCharacter DnDCharacter() {
        if (currentCharacter == null) {
            currentCharacter = new CurrentCharacter();
        }
        return currentCharacter;
    }

    private CurrentCharacter() {
        super(CharacterSettings.getInstance().loadCurrentCharacterId());
    }
}
