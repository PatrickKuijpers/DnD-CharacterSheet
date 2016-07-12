package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class Level extends StorageObject implements ExperienceEdgeListener, ChangeLevelListener {
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 30;
    private int currentLevel = storage.loadLevel();
    private ReadyForLevelDownListener readyForLevelDownListener;
    private ReadyForLevelUpListener readyForLevelUpListener;
    private LevelChangedListener levelChangedListener;

    public Level() {
        this(new Storage());
    }

    @VisibleForTesting
    protected Level(Storage storage) {
        super(storage);
    }

    @Override
    public void save() {
        storage.saveLevel(currentLevel);
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
        readyForLevelDownListener.onReadyForLevelDown();
    }

    private void validateMinReached() throws MinLevelReachedException {
        if (currentLevel == MIN_LEVEL) {
            throw new MinLevelReachedException();
        }
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        validateMaxReached();
        readyForLevelUpListener.onReadyForLevelUp();
    }

    private void validateMaxReached() throws MaxLevelReachedException {
        if (currentLevel == MAX_LEVEL) {
            throw new MaxLevelReachedException();
        }
    }

    @Override
    public void onChangeLevel(int levelChangeValue) {
        currentLevel = currentLevel + levelChangeValue;
        levelChangedListener.onLevelChanged();
    }

    public void setReadyForLevelDownListener(ReadyForLevelDownListener readyForLevelDownListener) {
        this.readyForLevelDownListener = readyForLevelDownListener;
    }

    public void setReadyForLevelUpListener(ReadyForLevelUpListener readyForLevelUpListener) {
        this.readyForLevelUpListener = readyForLevelUpListener;
    }

    public void setLevelChangedListener(LevelChangedListener levelChangedListener) {
        this.levelChangedListener = levelChangedListener;
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

    public interface ReadyForLevelDownListener {
        void onReadyForLevelDown();
    }

    public interface ReadyForLevelUpListener {
        void onReadyForLevelUp();
    }

    public interface LevelChangedListener {
        void onLevelChanged();
    }
}
