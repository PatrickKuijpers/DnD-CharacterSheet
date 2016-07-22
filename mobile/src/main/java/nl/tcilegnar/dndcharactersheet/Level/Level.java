package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import java.util.ArrayList;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.LevelListener;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class Level extends StorageObject implements ExperienceEdgeListener, ChangeLevelListener, LevelListener {
    public static final int MIN_LEVEL = LevelTable.ONE.level;
    public static final int MAX_LEVEL = LevelTable.values().length;
    private int currentLevel = storage.loadLevel();
    private ReadyForLevelDownListener readyForLevelDownListener;
    private ReadyForLevelUpListener readyForLevelUpListener;
    private ArrayList<LevelChangedListener> levelChangedListeners = new ArrayList<>();
    private CurrentProjectedLevelListener currentProjectedLevelListener;

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

    @Override
    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public void onExperienceMinPassed() throws MinLevelReachedException {
        int currentProjectedLevel = currentProjectedLevelListener.getCurrentProjectedLevel();
        validateMinimumLevel(currentProjectedLevel);
        readyForLevelDownListener.onReadyForLevelDown();
    }

    private void validateMinimumLevel(int level) throws MinLevelReachedException {
        if (isMinimumLevel(level)) {
            throw new MinLevelReachedException();
        }
    }

    private boolean isMinimumLevel(int level) {
        return level <= MIN_LEVEL;
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        int currentProjectedLevel = currentProjectedLevelListener.getCurrentProjectedLevel();
        validateMaximumLevel(currentProjectedLevel);
        readyForLevelUpListener.onReadyForLevelUp();
    }

    private void validateMaximumLevel(int level) throws MaxLevelReachedException {
        if (isMaximumLevel(level)) {
            throw new MaxLevelReachedException();
        }
    }

    private boolean isMaximumLevel(int level) throws MaxLevelReachedException {
        return level >= MAX_LEVEL;
    }

    @Override
    public void onChangeLevel(int levelChangeValue) throws MinLevelReachedException, MaxLevelReachedException {
        int newLevel = currentLevel + levelChangeValue;
        validateMinimumLevel(newLevel + 1);
        validateMaximumLevel(newLevel - 1);
        currentLevel = newLevel;
        for (LevelChangedListener levelChangedListener : levelChangedListeners) {
            levelChangedListener.onLevelChanged();
        }
    }

    public void setReadyForLevelDownListener(ReadyForLevelDownListener readyForLevelDownListener) {
        this.readyForLevelDownListener = readyForLevelDownListener;
    }

    public void setReadyForLevelUpListener(ReadyForLevelUpListener readyForLevelUpListener) {
        this.readyForLevelUpListener = readyForLevelUpListener;
    }

    public void addLevelChangedListener(LevelChangedListener levelChangedListener) {
        levelChangedListeners.add(levelChangedListener);
    }

    public void setCurrentProjectedLevelListener(CurrentProjectedLevelListener currentProjectedLevelListener) {
        this.currentProjectedLevelListener = currentProjectedLevelListener;
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

    public interface CurrentProjectedLevelListener {
        int getCurrentProjectedLevel();
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
}
