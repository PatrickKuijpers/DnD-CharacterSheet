package nl.tcilegnar.dndcharactersheet.Level.LevelUp;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class ExperienceRemainder extends StorageObject {
    private int experienceRemainder = storage.loadExperienceRemainder();

    public ExperienceRemainder() {
        this(new Storage());
    }

    @VisibleForTesting
    protected ExperienceRemainder(Storage storage) {
        super(storage);
    }

    @Override
    public void save() {
        storage.saveExperienceRemainder(experienceRemainder);
    }

    public int getExperienceRemainder() {
        return experienceRemainder;
    }
}
