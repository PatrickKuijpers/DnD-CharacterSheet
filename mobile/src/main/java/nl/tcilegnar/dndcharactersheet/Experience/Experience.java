package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;

import nl.tcilegnar.dndcharactersheet.Base.StorageObject;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.CurrentProjectedLevelListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Experience extends StorageObject {
    public static final int EXP_MIN = 0;

    private final LevelTableUtil levelTableUtil;
    private ExperienceUpdater expUpdater;

    private int currentExp = storage.loadExperience();

    private CurrentProjectedLevelListener currentProjectedLevelListener;
    private ArrayList<ExperienceUpdatedListener> experienceUpdatedListeners = new ArrayList<>();

    public Experience() {
        this(new Storage(), null, new LevelTableUtil());
        this.expUpdater = new ExperienceUpdater(this);
    }

    @VisibleForTesting
    protected Experience(Storage storage, ExperienceUpdater expUpdater, LevelTableUtil levelTableUtil) {
        super(storage);
        this.expUpdater = expUpdater;
        this.levelTableUtil = levelTableUtil;
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
        return levelTableUtil.getMaxExperience(currentLevel);
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public ExperienceUpdater getExperienceUpdater() {
        return expUpdater;
    }

    public void updateExperience(int expUpdateValue) throws ExpTooLowException {
        currentExp = expUpdater.getUpdatedExperience(expUpdateValue);
        onExperienceUpdated();
    }

    private void onExperienceUpdated() {
        for (ExperienceUpdatedListener experienceUpdatedListener : experienceUpdatedListeners) {
            experienceUpdatedListener.onExperienceUpdated();
        }
    }

    public void setCurrentProjectedLevelListener(CurrentProjectedLevelListener currentProjectedLevelListener) {
        this.currentProjectedLevelListener = currentProjectedLevelListener;
    }

    public void addExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        expUpdater.addExperienceEdgeListener(experienceEdgeListener);
    }

    public void addExperienceUpdatedListener(ExperienceUpdatedListener experienceUpdatedListener) {
        experienceUpdatedListeners.add(experienceUpdatedListener);
    }

    public interface LevelListener {
        int getCurrentLevel();
    }

    public interface ExperienceUpdatedListener {
        void onExperienceUpdated();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Experience) {
            Experience exp = (Experience) obj;
            isEqual = getCurrentExp() == exp.getCurrentExp() && getMin() == exp.getMin();
            if (isEqual && currentProjectedLevelListener != null) {
                isEqual = getMax() == exp.getMax();
            }
        }
        return isEqual;
    }
}
