package nl.tcilegnar.dndcharactersheet.characters;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class DnDCharacter {
    private final String id;
    private final Storage storage;

    public DnDCharacter(String id) {
        this.id = id;
        this.storage = new Storage(id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return storage.loadCharacterName() + " " + id; // TODO: temp. naam, tot deze ingevuld kan worden
    }

    public void setName(String name) {
        storage.saveCharacterName(name);
    }
}
