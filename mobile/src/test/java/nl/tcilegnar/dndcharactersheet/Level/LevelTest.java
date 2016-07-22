package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Level.Level.LevelChangedListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.ReadyForLevelDownListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.ReadyForLevelUpListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static nl.tcilegnar.dndcharactersheet.Level.Level.CurrentProjectedLevelListener;
import static nl.tcilegnar.dndcharactersheet.Level.Level.MAX_LEVEL;
import static nl.tcilegnar.dndcharactersheet.Level.Level.MIN_LEVEL;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LevelTest {
    private static final int DEFAULT_LEVEL = Storage.Key.CURRENT_LEVEL.defaultValue;
    private static Level level;
    private Storage storageMock;
    private ReadyForLevelUpListener readyForLevelUpListenerMock;
    private ReadyForLevelDownListener readyForLevelDownListenerMock;
    private LevelChangedListener levelChangedListenerMock;
    private CurrentProjectedLevelListener currentProjectedLevelListenerMock;
    private int initialLevel;

    @Test
    public void newLevel_Default_LevelLoadedAndDefaultLevelIsSetAsCurrentLevel() {
        // Arrange
        initLevelDefault();

        // Act
        Level level = new Level();

        // Assert
        verify(storageMock, times(1)).loadLevel();
        assertEquals(DEFAULT_LEVEL, level.getCurrentLevel());
    }

    @Test
    public void newLevel_StorageValuePositive_IsSetAsCurrentLevel() {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int expectedSavedLevel = 12;
        doReturn(expectedSavedLevel).when(storageMock).loadLevel();

        // Act
        Level level = new Level(storageMock);

        // Assert
        verify(storageMock, times(1)).loadLevel();
        assertEquals(expectedSavedLevel, level.getCurrentLevel());
    }

    @Test
    public void save_DefaultValue() {
        // Arrange
        initLevelDefault();

        // Act
        level.save();

        // Assert
        verify(storageMock).saveLevel(DEFAULT_LEVEL);
    }

    @Test
    public void save_LoadedValue() {
        // Arrange
        initLevel(12);

        // Act
        level.save();

        // Assert
        verify(storageMock).saveLevel(initialLevel);
    }

    @Test
    public void save_NewValueIsSet() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initLevelDefault();
        int expectedLevelChangeValue = 1;
        level.onChangeLevel(expectedLevelChangeValue);

        // Act
        level.save();

        // Assert
        int expectedLevel = initialLevel + expectedLevelChangeValue;
        verify(storageMock).saveLevel(expectedLevel);
    }

    @Test
    public void getCurrentLevel_NoValueSet_ValueIsDefault() {
        // Arrange
        initLevelDefault();

        // Act
        int currentLevel = level.getCurrentLevel();

        // Assert
        assertEquals(DEFAULT_LEVEL, currentLevel);
    }

    @Test
    public void onExperienceMinPassed_WithStartingLevel12_OnReadyForLevelDown() throws MinLevelReachedException {
        // Arrange
        initLevel(12);

        // Act
        level.onExperienceMinPassed();

        // Assert
        verifyOnReadyForLevelDown();
    }

    @Test
    public void onExperienceMinPassed_WithStartingLevel12AndProjectedLevelMin1_OnReadyForLevelDown() throws
            MinLevelReachedException {
        // Arrange
        initLevel(12);
        doReturn(initialLevel - 1).when(currentProjectedLevelListenerMock).getCurrentProjectedLevel();

        // Act
        level.onExperienceMinPassed();

        // Assert
        verifyOnReadyForLevelDown();
    }

    @Test
    public void onExperienceMinPassed_WithStartingLevelAlmostMin_OnReadyForLevelDown() throws MinLevelReachedException {
        // Arrange
        initLevel(MIN_LEVEL + 1);

        // Act
        level.onExperienceMinPassed();

        // Assert
        verifyOnReadyForLevelDown();
    }

    @Test(expected = MinLevelReachedException.class)
    public void onExperienceMinPassed_WithStartingLevelAlmostMinAndProjectedLevelMin1_MinLevelReachedException()
            throws MinLevelReachedException {
        // Arrange
        initLevel(MIN_LEVEL + 1);
        doReturn(initialLevel - 1).when(currentProjectedLevelListenerMock).getCurrentProjectedLevel();

        // Act
        level.onExperienceMinPassed();

        // Assert
    }

    @Test(expected = MinLevelReachedException.class)
    public void onExperienceMinPassed_WithMinStartingLevel_MinLevelReachedException() throws MinLevelReachedException {
        // Arrange
        initLevel(MIN_LEVEL);

        // Act
        level.onExperienceMinPassed();

        // Assert
    }

    @Test
    public void onExperienceMaxReached_WithStartingLevel12_OnReadyForLevelUp() throws MaxLevelReachedException {
        // Arrange
        initLevel(12);

        // Act
        level.onExperienceMaxReached();

        // Assert
        verifyOnReadyForLevelUp();
    }

    @Test
    public void onExperienceMaxReached_WithStartingLevel12AndProjectedLevelPlus1_OnReadyForLevelUp() throws
            MaxLevelReachedException {
        // Arrange
        initLevel(12);
        doReturn(initialLevel + 1).when(currentProjectedLevelListenerMock).getCurrentProjectedLevel();

        // Act
        level.onExperienceMaxReached();

        // Assert
        verifyOnReadyForLevelUp();
    }

    @Test
    public void onExperienceMaxReached_WithStartingLevelAlmostMax_OnReadyForLevelUp() throws MaxLevelReachedException {
        // Arrange
        initLevel(MAX_LEVEL - 1);

        // Act
        level.onExperienceMaxReached();

        // Assert
        verifyOnReadyForLevelUp();
    }

    @Test(expected = MaxLevelReachedException.class)
    public void onExperienceMaxReached_WithStartingLevelAlmostMaxAndProjectedLevelPlus1_MaxLevelReachedException()
            throws MaxLevelReachedException {
        // Arrange
        initLevel(MAX_LEVEL - 1);
        doReturn(initialLevel + 1).when(currentProjectedLevelListenerMock).getCurrentProjectedLevel();

        // Act
        level.onExperienceMaxReached();

        // Assert
    }

    @Test(expected = MaxLevelReachedException.class)
    public void onExperienceMaxReached_WithMaxStartingLevel_MaxLevelReachedException() throws MaxLevelReachedException {
        // Arrange
        initLevel(MAX_LEVEL);

        // Act
        level.onExperienceMaxReached();

        // Assert
    }

    @Test
    public void onChangeLevel_Plus1_LevelIsIncreasedWith1() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initLevelDefault();

        // Act
        int levelChangeValue = 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_Plus3_LevelIsIncreasedWith3() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initLevelDefault();

        // Act
        int levelChangeValue = 3;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_UpToMaxLevel_LevelIsIncreased() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        initLevelDefault();

        // Act
        int levelChangeValue = MAX_LEVEL - initialLevel;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test(expected = MaxLevelReachedException.class)
    public void onChangeLevel_OverMaxLevel_ExperienceMaxException() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        initLevelDefault();

        // Act
        int levelChangeValue = MAX_LEVEL - initialLevel + 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
    }

    @Test
    public void onChangeLevel_OverMaxLevel_LevelIsNotIncreased() throws MinLevelReachedException {
        // Arrange
        initLevelDefault();

        // Act
        int levelChangeValue = MAX_LEVEL - initialLevel + 1;
        try {
            level.onChangeLevel(levelChangeValue);
        } catch (MaxLevelReachedException e) {
            // Niets doen
        }

        // Assert
        assertLevelNotChanged(level, initialLevel);
    }

    @Test
    public void onChangeLevel_Minus1_LevelIsDecreasedWith1() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initLevel(10);

        // Act
        int levelChangeValue = -1;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_Minus3_LevelIsDecreasedWith3() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initLevel(10);

        // Act
        int levelChangeValue = -3;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_UpToMinLevel_LevelIsDecreased() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        initLevel(10);

        // Act
        int levelChangeValue = MIN_LEVEL - initialLevel;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, initialLevel, levelChangeValue);
    }

    @Test(expected = MinLevelReachedException.class)
    public void onChangeLevel_OverMinLevel_ExperienceMinException() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        initLevel(10);

        // Act
        int levelChangeValue = MIN_LEVEL - initialLevel - 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
    }

    @Test
    public void onChangeLevel_OverMinLevel_LevelIsNotDecreased() throws MaxLevelReachedException {
        // Arrange
        initLevel(10);

        // Act
        int levelChangeValue = MIN_LEVEL - initialLevel - 1;
        try {
            level.onChangeLevel(levelChangeValue);
        } catch (MinLevelReachedException e) {
            // Niets doen
        }

        // Assert
        assertLevelNotChanged(level, initialLevel);
    }

    private void initLevelDefault() {
        initLevel(DEFAULT_LEVEL);
    }

    private void initLevel(int initialLevel) {
        this.initialLevel = initialLevel;
        level = getNewLevelWithMocksAndListeners(initialLevel);
    }

    private Level getNewLevelWithMocksAndListeners(int initialSavedLevel) {
        storageMock = mock(Storage.class);
        doReturn(initialSavedLevel).when(storageMock).loadLevel();
        Level level = new Level(storageMock);
        initListeners(level);
        return level;
    }

    private void initListeners(Level level) {
        readyForLevelDownListenerMock = mock(ReadyForLevelDownListener.class);
        readyForLevelUpListenerMock = mock(ReadyForLevelUpListener.class);
        levelChangedListenerMock = mock(LevelChangedListener.class);
        currentProjectedLevelListenerMock = mock(CurrentProjectedLevelListener.class);
        doReturn(initialLevel).when(currentProjectedLevelListenerMock).getCurrentProjectedLevel();
        level.setReadyForLevelDownListener(readyForLevelDownListenerMock);
        level.setReadyForLevelUpListener(readyForLevelUpListenerMock);
        level.addLevelChangedListener(levelChangedListenerMock);
        level.setCurrentProjectedLevelListener(currentProjectedLevelListenerMock);
    }

    private void verifyOnReadyForLevelDown() throws MinLevelReachedException {
        verify(readyForLevelDownListenerMock).onReadyForLevelDown();
        assertEquals(initialLevel, level.getCurrentLevel()); // En ga nog niet direct level down!
    }

    private void verifyOnReadyForLevelUp() throws MaxLevelReachedException {
        verify(readyForLevelUpListenerMock).onReadyForLevelUp();
        assertEquals(initialLevel, level.getCurrentLevel()); // En ga nog niet direct level up!
    }

    private void assertLevelChanged(Level level, int previousLevel, int levelChangeValue) {
        int expectedNewLevel = previousLevel + levelChangeValue;
        int newLevel = level.getCurrentLevel();
        assertEquals(expectedNewLevel, newLevel);
        verify(levelChangedListenerMock, times(1)).onLevelChanged();
    }

    private void assertLevelNotChanged(Level level, int previousLevel) {
        int newLevel = level.getCurrentLevel();
        assertEquals(previousLevel, newLevel);
        verify(levelChangedListenerMock, never()).onLevelChanged();
    }
}
