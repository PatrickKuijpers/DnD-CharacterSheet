package nl.tcilegnar.dndcharactersheet.Level.LevelUp;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class ExperienceRemainder extends StorageObject {
    private int experienceRemainder;

    public ExperienceRemainder() {
        super();
    }

    @VisibleForTesting
    protected ExperienceRemainder(Storage storage) {
        super(storage);
    }

    @Override
    protected void init() {
        this.experienceRemainder = storage.loadExperienceRemainder();
    }

    @Override
    public void save() {
        storage.saveExperienceRemainder(experienceRemainder);
    }

    public int getExperienceRemainder() {
        return experienceRemainder;
    }
}
