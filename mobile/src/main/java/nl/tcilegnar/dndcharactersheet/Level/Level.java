package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;

import nl.tcilegnar.dndcharactersheet.Base.Exceptions.CustomToastException;
import nl.tcilegnar.dndcharactersheet.Base.StorageObject;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.LevelListener;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.Level.entities.LevelTable;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

public class Level extends StorageObject implements ExperienceEdgeListener, ChangeLevelListener, LevelListener {
    public static final int MIN_LEVEL = LevelTable.ONE.level;
    public static final int MAX_LEVEL = LevelTable.values().length;
    private int currentLevel = storage.loadLevel();
    private ArrayList<ReadyForLevelChangeListener> readyForLevelChangeListeners = new ArrayList<>();
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
        onReadyForLevelChangeListenerUpdate(-1);
    }

    private void onReadyForLevelChangeListenerUpdate(int levelChangeValue) {
        for (ReadyForLevelChangeListener readyForLevelChangeListener : readyForLevelChangeListeners) {
            readyForLevelChangeListener.onReadyForLevelChange(levelChangeValue);
        }
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
        onReadyForLevelChangeListenerUpdate(+1);
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

    public void addReadyForLevelChangeListener(ReadyForLevelChangeListener readyForLevelChangeListener) {
        readyForLevelChangeListeners.add(readyForLevelChangeListener);
    }

    public void addLevelChangedListener(LevelChangedListener levelChangedListener) {
        levelChangedListeners.add(levelChangedListener);
    }

    public void setCurrentProjectedLevelListener(CurrentProjectedLevelListener currentProjectedLevelListener) {
        this.currentProjectedLevelListener = currentProjectedLevelListener;
    }

    public interface ReadyForLevelChangeListener {
        void onReadyForLevelChange(int levelChangeValue);
    }

    public interface LevelChangedListener {
        void onLevelChanged();
    }

    public interface CurrentProjectedLevelListener {
        int getCurrentProjectedLevel();
    }

    public static class MinLevelReachedException extends CustomToastException {
        public MinLevelReachedException() {
            super(Res.getString(R.string.min_level_reached_exception) + MIN_LEVEL);
        }
    }

    public static class MaxLevelReachedException extends CustomToastException {
        public MaxLevelReachedException() {
            super(Res.getString(R.string.max_level_reached_exception) + MAX_LEVEL);
        }
    }
}
