package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class Experience extends StorageObject {
    public static final int EXP_MIN = 0;
    public static final int EXP_MAX = 2500;
    private int currentExp = storage.loadExperience();
    private ExperienceUpdater expUpdater;

    public Experience() {
        this(new Storage(), null);
        this.expUpdater = new ExperienceUpdater(this);
    }

    @VisibleForTesting
    protected Experience(Storage storage, ExperienceUpdater expUpdater) {
        super(storage);
        this.expUpdater = expUpdater;
    }

    @Override
    public void save() {
        storage.saveExperience(currentExp);
    }

    public int getMin() {
        return EXP_MIN;
    }

    public int getMax() {
        return EXP_MAX;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void updateExperience(int expUpdateValue) throws ExpTooLowException {
        currentExp = expUpdater.getUpdatedExperience(expUpdateValue);
    }

    public void setExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        expUpdater.setExperienceEdgeListener(experienceEdgeListener);
    }
}
