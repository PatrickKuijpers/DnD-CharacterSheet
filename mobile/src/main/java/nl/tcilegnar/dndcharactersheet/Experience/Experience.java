package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

import static nl.tcilegnar.dndcharactersheet.Level.Level.CurrentProjectedLevelListener;

public class Experience extends StorageObject {
    public static final int EXP_MIN = 0;
    private static final int EXP_MULTIPLIER_VALUE = 1000;
    private int currentExp = storage.loadExperience();
    private ExperienceUpdater expUpdater;
    private CurrentProjectedLevelListener currentProjectedLevelListener;

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
        int currentLevel = currentProjectedLevelListener.getCurrentProjectedLevel();
        return EXP_MULTIPLIER_VALUE * currentLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void updateExperience(int expUpdateValue) throws ExpTooLowException {
        currentExp = expUpdater.getUpdatedExperience(expUpdateValue);
    }

    public void addExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        expUpdater.addExperienceEdgeListener(experienceEdgeListener);
    }

    public void setCurrentProjectedLevelListener(CurrentProjectedLevelListener currentProjectedLevelListener) {
        this.currentProjectedLevelListener = currentProjectedLevelListener;
    }

    public interface LevelListener {
        int getCurrentLevel();
    }
}
