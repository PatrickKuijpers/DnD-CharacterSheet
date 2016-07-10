package nl.tcilegnar.dndcharactersheet.Level.LevelUp;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.StorageObject;

public class LevelChange extends StorageObject {
    private int readyForLevelChange;

    private ChangeLevelListener changeLevelListener;

    public LevelChange() {
        super();
    }

    @VisibleForTesting
    protected LevelChange(Storage storage) {
        super(storage);
    }

    @Override
    protected void init() {
        this.readyForLevelChange = storage.loadReadyForLevelChange();
    }

    @Override
    public void save() {
        storage.saveReadyForLevelChange(readyForLevelChange);
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

    public boolean isReadyForLevelUp() {
        return readyForLevelChange > 0;
    }

    public boolean isReadyForLevelDown() {
        return readyForLevelChange < 0;
    }

    private void onChangeLevel(int levelChangeValue) {
        readyForLevelChange = readyForLevelChange - levelChangeValue;
    }

    public void onReadyForLevelUp() {
        readyForLevelChange++;
    }

    public void onReadyForLevelDown() {
        readyForLevelChange--;
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        this.changeLevelListener = changeLevelListener;
    }

    public interface ChangeLevelListener {
        void onChangeLevel(int levelChangeValue);
    }
}
