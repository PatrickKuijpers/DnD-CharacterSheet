package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class Level extends StorageObject implements ExperienceEdgeListener, ChangeLevelListener {
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 30;
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

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void onExperienceMinReached() throws MinLevelReachedException {
        validateMinimumLevel(currentLevel);
        readyForLevelDownListener.onReadyForLevelDown();
    }

    public void validateMinimumLevel(int level) throws MinLevelReachedException {
        if (isMinimumLevel(level)) {
            throw new MinLevelReachedException();
        }
    }

    private boolean isMinimumLevel(int level) {
        return level <= MIN_LEVEL;
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        validateMaximumLevel(currentLevel);
        readyForLevelUpListener.onReadyForLevelUp();
    }

    public void validateMaximumLevel(int level) throws MaxLevelReachedException {
        if (isMaximumLevel(level)) {
            throw new MaxLevelReachedException();
        }
    }

    private boolean isMaximumLevel(int Level) throws MaxLevelReachedException {
        return Level >= MAX_LEVEL;
    }

    @Override
    public void onChangeLevel(int levelChangeValue) throws MinLevelReachedException, MaxLevelReachedException {
        int newLevel = currentLevel + levelChangeValue;
        validateMinimumLevel(newLevel + 1);
        validateMaximumLevel(newLevel - 1);
        currentLevel = newLevel;
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
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public class MaxLevelReachedException extends Exception {
        public MaxLevelReachedException() {
            super("Maximum level bereikt: " + MAX_LEVEL);
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public interface ReadyForLevelDownListener {
        void onReadyForLevelDown() throws MinLevelReachedException;
    }

    public interface ReadyForLevelUpListener {
        void onReadyForLevelUp() throws MaxLevelReachedException;
    }

    public interface LevelChangedListener {
        void onLevelChanged();
    }
}
