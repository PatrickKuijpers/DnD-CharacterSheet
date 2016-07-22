package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.MyBuildConfig;

public class ExperienceUpdater {
    private final Experience experience;
    private MyBuildConfig buildConfig;
    private ExperienceEdgeListener experienceEdgeListener;

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
                experienceEdgeListener.onExperienceMinPassed();
                newExp += experience.getMax();
            } catch (Level.MinLevelReachedException e) {
                newExp = experience.getMin();
                break;
            }
        }
        while (isMaxExperienceReached(newExp)) {
            try {
                experienceEdgeListener.onExperienceMaxReached();
                newExp -= experience.getMax();
            } catch (Level.MaxLevelReachedException e) {
                newExp = experience.getMax();
                break;
            }
        }
        return newExp;
    }

    private boolean isMinExperiencePassed(int newExp) {
        return newExp < 0;
    }

    private boolean isMaxExperienceReached(int newExp) {
        return newExp >= experience.getMax();
    }

    public void setExperienceEdgeListener(ExperienceEdgeListener experienceEdgeListener) {
        this.experienceEdgeListener = experienceEdgeListener;
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
