package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Level implements ExperienceEdgeListener {
    private static final int MAX_LEVEL = 20;
    private final Storage storage;
    private int currentLevel;

    public Level() {
        this(new Storage());
    }

    @VisibleForTesting
    protected Level(Storage storage) {
        this.storage = storage;
        this.currentLevel = storage.loadLevel();
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        validate();

        currentLevel++;
        Toast.makeText(App.getContext(), "Level up: " + currentLevel, Toast.LENGTH_LONG).show();
    }

    private void validate() throws MaxLevelReachedException {
        if (currentLevel == MAX_LEVEL) {
            throw new MaxLevelReachedException();
        }
    }

    public void saveLevel() {
        storage.saveLevel(currentLevel);
    }

    public class MaxLevelReachedException extends Exception {
        public MaxLevelReachedException() {
            super("Maximum level bereikt: " + MAX_LEVEL);
        }
    }
}
