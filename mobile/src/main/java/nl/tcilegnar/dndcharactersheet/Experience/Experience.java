package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildType;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class Experience extends StorageObject {
    public static final int EXP_MIN = 0;
    public static final int EXP_MAX = 2500;
    private int currentExp = storage.loadExperience();
    private ExperienceEdgeListener experienceEdgeListener;
    private BuildType buildType;

    public Experience() {
        this(new Storage(), new BuildType());
    }

    @VisibleForTesting
    protected Experience(Storage storage, BuildType buildType) {
        super(storage);
        this.buildType = buildType;
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
        int newExp = currentExp + expUpdateValue;
        validate(expUpdateValue, newExp);

        newExp = correctExperienceWhenEdgeIsReached(newExp);

        currentExp = newExp;
    }

    private void validate(int expUpdateValue, int newExp) throws ExpTooLowException {
        if (newExp < 0 && !buildType.isDebug()) {
            String message = "Nieuwe exp-waarde is te laag: " + currentExp + " + " + expUpdateValue + " = " + newExp;
            throw new ExpTooLowException(message);
        }
    }

    private int correctExperienceWhenEdgeIsReached(int newExp) {
        if (isMinExperienceReached(newExp)) {
            newExp = getNewExpWhenMinExpReached(newExp);
        } else if (isMaxExperienceReached(newExp)) {
            newExp = getNewExpWhenExpMaxReached(newExp);
        }
        return newExp;
    }

    private boolean isMinExperienceReached(int newExp) {
        return newExp < 0;
    }

    private int getNewExpWhenMinExpReached(int newExp) {
        try {
            experienceEdgeListener.onExperienceMinReached();
            newExp = newExp + EXP_MAX;
        } catch (MinLevelReachedException e) {
            newExp = EXP_MIN;
        }
        return newExp;
    }

    private boolean isMaxExperienceReached(int newExp) {
        return newExp > EXP_MAX;
    }

    private int getNewExpWhenExpMaxReached(int newExp) {
        try {
            experienceEdgeListener.onExperienceMaxReached();
            newExp = newExp - EXP_MAX;
        } catch (MaxLevelReachedException e) {
            newExp = EXP_MAX;
        }
        return newExp;
    }

    public void setExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        this.experienceEdgeListener = experienceEdgeListener;
    }

    public interface ExperienceEdgeListener {
        void onExperienceMinReached() throws MinLevelReachedException;

        void onExperienceMaxReached() throws MaxLevelReachedException;
    }

    public class ExpTooLowException extends Exception {
        public ExpTooLowException(String message) {
            super(message);
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
