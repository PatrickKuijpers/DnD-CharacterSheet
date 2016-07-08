package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Level implements ExperienceEdgeListener {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 30;
    private final Storage storage;
    private int currentLevel;
    private LevelDownListener levelDownListener;
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
    public void onExperienceMinReached() throws MinLevelReachedException {
        validateMinReached();

        currentLevel--;
        levelDownListener.onLevelDown();
    }

    private void validateMinReached() throws MinLevelReachedException {
        if (currentLevel == MIN_LEVEL) {
            throw new MinLevelReachedException();
        }
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        validateMaxReached();

        currentLevel++;
        levelUpListener.onLevelUp();
    }

    private void validateMaxReached() throws MaxLevelReachedException {
        if (currentLevel == MAX_LEVEL) {
            throw new MaxLevelReachedException();
        }
    }

    public void saveLevel() {
        storage.saveLevel(currentLevel);
    }

    public void setLevelDownListener(LevelDownListener levelDownListener) {
        this.levelDownListener = levelDownListener;
    }

    public void setLevelUpListener(LevelUpListener levelUpListener) {
        this.levelUpListener = levelUpListener;
    }

    public class MinLevelReachedException extends Exception {
        public MinLevelReachedException() {
            super("Minimum level bereikt: " + MIN_LEVEL);
        }
    }

    public class MaxLevelReachedException extends Exception {
        public MaxLevelReachedException() {
            super("Maximum level bereikt: " + MAX_LEVEL);
        }
    }

    public interface LevelUpListener {
        void onLevelUp();
    }

    public interface LevelDownListener {
        void onLevelDown();
    }
}
