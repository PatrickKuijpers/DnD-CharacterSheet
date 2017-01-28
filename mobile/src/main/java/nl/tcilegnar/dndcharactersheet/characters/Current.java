package nl.tcilegnar.dndcharactersheet.characters;

import nl.tcilegnar.dndcharactersheet.Storage.CharacterSettings;

public class Current extends DnDCharacter {
    private static Current currentDndCharacter;

    public static Current DnDCharacter() {
        if (currentDndCharacter == null) {
            currentDndCharacter = new Current();
        }
        return currentDndCharacter;
    }

    private Current() {
        super(CharacterSettings.getInstance().loadCurrentCharacterId());
    }
}
