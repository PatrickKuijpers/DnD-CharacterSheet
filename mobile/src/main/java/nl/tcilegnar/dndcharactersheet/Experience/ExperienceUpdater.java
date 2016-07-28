package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import java.util.ArrayList;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;
import nl.tcilegnar.dndcharactersheet.Utils.Log;

public class ExperienceUpdater {
    private final Experience experience;
    private final Settings settings;

    private ArrayList<ExperienceEdgeListener> experienceEdgeListeners = new ArrayList<>();

    public ExperienceUpdater(Experience experience) {
        this(experience, Settings.getInstance());
    }

    @VisibleForTesting
    protected ExperienceUpdater(Experience experience, Settings settings) {
        this.experience = experience;
        this.settings = settings;
    }

    public int getUpdatedExperience(int expUpdateValue) throws ExpTooLowException {
        int newExp = experience.getCurrentExp() + expUpdateValue;
        validate(expUpdateValue, newExp);

        int finalNewExp = correctExperienceWhenEdgeIsReached(newExp);
        return finalNewExp;
    }

    private void validate(int expUpdateValue, int newExp) throws ExpTooLowException {
        if (newExp < 0 && !settings.isLevelDownAllowed()) {
            int currentExp = experience.getCurrentExp();
            String message = "Nieuwe exp-waarde is te laag: " + currentExp + " + " + expUpdateValue + " = " + newExp;
            Log.w(getClass().getSimpleName(), message);
            throw new ExpTooLowException("Level down is not allowed");
        }
    }

    private int correctExperienceWhenEdgeIsReached(int newExp) {
        while (isMaxExperienceReached(newExp)) {
            try {
                // Kies eerst max exp voor het huidige (projected) level, en trek dat af van het huidige exp
                newExp -= experience.getMax();
                // Verhoog daarna pas het (projected) level
                onExperienceMaxReached();
            } catch (MaxLevelReachedException e) {
                newExp = experience.getMax();
                break;
            }
        }
        while (isMinExperiencePassed(newExp)) {
            try {
                // Verlaag eerst het (projected) level
                onExperienceMinReached();
                // Kies min exp voor het nieuwe (projected) level, en voeg dat toe aan het huidige exp
                newExp += experience.getMax();
            } catch (MinLevelReachedException e) {
                newExp = experience.getMin();
                break;
            }
        }
        return newExp;
    }

    private void onExperienceMinReached() throws MinLevelReachedException {
        for (ExperienceEdgeListener experienceEdgeListener : experienceEdgeListeners) {
            experienceEdgeListener.onExperienceMinPassed();
        }
    }

    private void onExperienceMaxReached() throws MaxLevelReachedException {
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
        void onExperienceMinPassed() throws MinLevelReachedException;

        void onExperienceMaxReached() throws MaxLevelReachedException;
    }

    public class ExpTooLowException extends Exception {
        public ExpTooLowException(String message) {
            super(message);
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
