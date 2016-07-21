package nl.tcilegnar.dndcharactersheet.Level.LevelUp;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class LevelsReadyForChange extends StorageObject {
    private int numberOfLevelsReadyForChange = storage.loadReadyForLevelChange();

    private ChangeLevelListener changeLevelListener;

    public LevelsReadyForChange() {
        this(new Storage());
    }

    @VisibleForTesting
    protected LevelsReadyForChange(Storage storage) {
        super(storage);
    }

    @Override
    public void save() {
        storage.saveReadyForLevelChange(numberOfLevelsReadyForChange);
    }

    public void handleLevelChange() throws MinLevelReachedException, MaxLevelReachedException {
        int levelChangeValue = 0;
        if (isReadyForLevelDown()) {
            levelChangeValue = -1;
        } else if (isReadyForLevelUp()) {
            levelChangeValue = +1;
        }
        changeLevelListener.onChangeLevel(levelChangeValue);
        onChangeLevel(levelChangeValue);
    }

    public int getNumberOfLevelsReadyForChange() {
        return numberOfLevelsReadyForChange;
    }

    public boolean shouldShowValue() {
        return numberOfLevelsReadyForChange != 0 && numberOfLevelsReadyForChange != 1;
    }

    public boolean isReadyForLevelUp() {
        return numberOfLevelsReadyForChange > 0;
    }

    public boolean isReadyForLevelDown() {
        return numberOfLevelsReadyForChange < 0;
    }

    private void onChangeLevel(int levelChangeValue) {
        numberOfLevelsReadyForChange -= levelChangeValue;
    }

    public void onReadyForLevelDown() throws MinLevelReachedException {
        validateReadyForLevelDown();
        numberOfLevelsReadyForChange--;
        save();
    }

    private void validateReadyForLevelDown() throws MinLevelReachedException {
        Level level = new Level();
        int currentLevel = level.getCurrentLevel();
        int projectedLevelValue = getCurrentProjectedLevel(currentLevel);
        level.validateMinimumLevel(projectedLevelValue);
    }

    public int getCurrentProjectedLevel(int currentLevel) {
        return currentLevel + numberOfLevelsReadyForChange;
    }

    public void onReadyForLevelUp() throws MaxLevelReachedException {
        validateReadyForLevelUp();
        numberOfLevelsReadyForChange++;
        save();
    }

    private void validateReadyForLevelUp() throws MaxLevelReachedException {
        Level level = new Level();
        int currentLevel = level.getCurrentLevel();
        int currentProjectedLevel = getCurrentProjectedLevel(currentLevel);
        level.validateMaximumLevel(currentProjectedLevel);
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        this.changeLevelListener = changeLevelListener;
    }

    public interface ChangeLevelListener {
        void onChangeLevel(int levelChangeValue) throws MinLevelReachedException, MaxLevelReachedException;
    }
}
