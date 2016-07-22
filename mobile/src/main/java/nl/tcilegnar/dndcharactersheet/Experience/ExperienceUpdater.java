package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import java.util.ArrayList;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.MyBuildConfig;

public class ExperienceUpdater {
    private final Experience experience;
    private MyBuildConfig buildConfig;
    private ArrayList<ExperienceEdgeListener> experienceEdgeListeners = new ArrayList<>();

    public ExperienceUpdater(Experience experience) {
        this(experience, new MyBuildConfig());
    }

    @VisibleForTesting
    protected ExperienceUpdater(Experience experience, MyBuildConfig buildConfig) {
        this.experience = experience;
        this.buildConfig = buildConfig;
    }

    public int getUpdatedExperience(int expUpdateValue) throws ExpTooLowException {
        int newExp = experience.getCurrentExp() + expUpdateValue;
        validate(expUpdateValue, newExp);

        int finalNewExp = correctExperienceWhenEdgeIsReached(newExp);
        return finalNewExp;
    }

    private void validate(int expUpdateValue, int newExp) throws ExpTooLowException {
        if (newExp < 0 && !buildConfig.isDebug()) {
            int currentExp = experience.getCurrentExp();
            String message = "Nieuwe exp-waarde is te laag: " + currentExp + " + " + expUpdateValue + " = " +
                    newExp;
            throw new ExpTooLowException(message);
        }
    }

    private int correctExperienceWhenEdgeIsReached(int newExp) {
        while (isMinExperiencePassed(newExp)) {
            try {
                onExperienceMinReached();
                newExp += experience.getMax();
            } catch (Level.MinLevelReachedException e) {
                newExp = experience.getMin();
                break;
            }
        }
        while (isMaxExperienceReached(newExp)) {
            try {
                onExperienceMaxReached();
                newExp -= experience.getMax();
            } catch (Level.MaxLevelReachedException e) {
                newExp = experience.getMax();
                break;
            }
        }
        return newExp;
    }

    private void onExperienceMinReached() throws Level.MinLevelReachedException {
        for (ExperienceEdgeListener experienceEdgeListener : experienceEdgeListeners) {
            experienceEdgeListener.onExperienceMinPassed();
        }
    }

    private void onExperienceMaxReached() throws Level.MaxLevelReachedException {
        for (ExperienceEdgeListener experienceEdgeListener : experienceEdgeListeners) {
            experienceEdgeListener.onExperienceMaxReached();
        }
    }

    private boolean isMinExperiencePassed(int newExp) {
        return newExp < 0;
    }

    private boolean isMaxExperienceReached(int newExp) {
        return newExp >= experience.getMax();
    }

    public void addExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        experienceEdgeListeners.add(experienceEdgeListener);
    }

    public interface ExperienceEdgeListener {
        void onExperienceMinPassed() throws Level.MinLevelReachedException;

        void onExperienceMaxReached() throws Level.MaxLevelReachedException;
    }

    public class ExpTooLowException extends Exception {
        public ExpTooLowException(String message) {
            super(message);
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
