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

    public void handleLevelChange() {
        int levelChangeValue = 0;
        if (isReadyForLevelDown()) {
            levelChangeValue = -1;
        } else if (isReadyForLevelUp()) {
            levelChangeValue = +1;
        }
        onChangeLevel(levelChangeValue);
        changeLevelListener.onChangeLevel(levelChangeValue);
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

    public void onReadyForLevelUp() throws MaxLevelReachedException {
        validateReadyForLevelUp();
        numberOfLevelsReadyForChange++;
    }

    private void validateReadyForLevelUp() throws MaxLevelReachedException {
        Level projectedLevel = new Level();
        int currentProjectedLevel = getCurrentProjectedLevel(projectedLevel);
        projectedLevel.validateMaxReached(currentProjectedLevel);
    }

    private int getCurrentProjectedLevel(Level projectedLevel) {
        return projectedLevel.getCurrentLevel() + numberOfLevelsReadyForChange;
    }

    public void onReadyForLevelDown() throws MinLevelReachedException {
        validateReadyForLevelDown();
        numberOfLevelsReadyForChange--;
    }

    private void validateReadyForLevelDown() throws MinLevelReachedException {
        Level projectedLevel = new Level();
        int projectedLevelValue = getCurrentProjectedLevel(projectedLevel);
        projectedLevel.validateMinReached(projectedLevelValue);
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        this.changeLevelListener = changeLevelListener;
    }

    public interface ChangeLevelListener {
        void onChangeLevel(int levelChangeValue);
    }
}
