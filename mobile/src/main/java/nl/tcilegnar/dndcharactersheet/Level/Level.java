package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Level implements ExperienceEdgeListener {
    private static final int MAX_LEVEL = 30;
    private final Storage storage;
    private int currentLevel;
    private LevelUpListener levelUpListener;

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
        levelUpListener.onLevelUp();
    }

    private void validate() throws MaxLevelReachedException {
        if (currentLevel == MAX_LEVEL) {
            throw new MaxLevelReachedException();
        }
    }

    public void saveLevel() {
        storage.saveLevel(currentLevel);
    }

    public void setLevelUpListener(LevelUpListener levelUpListener) {
        this.levelUpListener = levelUpListener;
    }

    public class MaxLevelReachedException extends Exception {
        public MaxLevelReachedException() {
            super("Maximum level bereikt: " + MAX_LEVEL);
        }
    }

    public interface LevelUpListener {
        void onLevelUp();
    }
}
