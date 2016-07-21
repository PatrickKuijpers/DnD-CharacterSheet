package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Before;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LevelTest {
    public static final int DEFAULT_LEVEL = Level.MIN_LEVEL;
    private Level level;
    private Storage storageMock;
    private ReadyForLevelUpListener readyForLevelUpListenerMock;
    private ReadyForLevelDownListener readyForLevelDownListenerMock;
    private LevelChangedListener levelChangedListenerMock;

    @Before
    public void setUp() {
        level = getNewLevel_WithDefaultValues();
    }

    @Test
    public void newLevel_Default_LevelLoadedAndDefaultLevelIsSetAsCurrentLevel() {
        // Arrange

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

        // Act
        level.save();

        // Assert
        verify(storageMock).saveLevel(DEFAULT_LEVEL);
    }

    @Test
    public void save_LoadedValue() {
        // Arrange
        int expectedSavedLevel = 12;
        level = getNewLevel(expectedSavedLevel);

        // Act
        level.save();

        // Assert
        verify(storageMock).saveLevel(expectedSavedLevel);
    }

    @Test
    public void save_NewValueIsSet() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();
        int expectedLevelChangeValue = 1;
        level.onChangeLevel(expectedLevelChangeValue);

        // Act
        level.save();

        // Assert
        int expectedLevel = previousLevel + expectedLevelChangeValue;
        verify(storageMock).saveLevel(expectedLevel);
    }

    @Test
    public void getCurrentLevel_NoValueSet_ValueIsDefault() {
        // Arrange

        // Act
        int currentLevel = level.getCurrentLevel();

        // Assert
        assertEquals(DEFAULT_LEVEL, currentLevel);
    }

    @Test
    public void onExperienceMinPassed_NotWithMinStartingLevel_OnReadyForLevelDown() throws MinLevelReachedException {
        // Arrange
        int initialSavedLevel = 12;
        Level level = getNewLevel(initialSavedLevel);

        // Act
        level.onExperienceMinPassed();

        // Assert
        verify(readyForLevelDownListenerMock, times(1)).onReadyForLevelDown();
        assertEquals(initialSavedLevel, level.getCurrentLevel()); // En ga nog niet direct lvl down
    }

    @Test(expected = MinLevelReachedException.class)
    public void onExperienceMinPassed_WithMinStartingLevel_MinLevelReachedException() throws MinLevelReachedException {
        // Arrange
        Level level = getNewLevel(Level.MIN_LEVEL);

        // Act
        level.onExperienceMinPassed();

        // Assert
    }

    @Test
    public void validateMinimumLevel_NotWithMinStartingLevel_NoExceptionThrown() throws MinLevelReachedException {
        // Arrange
        int checkedLevel = 12;

        // Act
        level.validateMinimumLevel(checkedLevel);

        // Assert
        // Geen exception verwacht!
    }

    @Test(expected = MinLevelReachedException.class)
    public void validateMinimumLevel_WithMinStartingLevel_MinLevelReachedException() throws MinLevelReachedException {
        // Arrange
        int checkedLevel = Level.MIN_LEVEL;

        // Act
        level.validateMinimumLevel(checkedLevel);

        // Assert
    }

    @Test
    public void onExperienceMaxReached_NotWithMaxStartingLevel_OnReadyForLevelUp() throws MaxLevelReachedException {
        // Arrange
        int initialSavedLevel = 12;
        Level level = getNewLevel(initialSavedLevel);

        // Act
        level.onExperienceMaxReached();

        // Assert
        verify(readyForLevelUpListenerMock, times(1)).onReadyForLevelUp();
        assertEquals(initialSavedLevel, level.getCurrentLevel()); // En ga nog niet direct lvl up
    }

    @Test(expected = MaxLevelReachedException.class)
    public void onExperienceMaxReached_WithMaxStartingLevel_MaxLevelReachedException() throws MaxLevelReachedException {
        // Arrange
        Level level = getNewLevel(Level.MAX_LEVEL);

        // Act
        level.onExperienceMaxReached();

        // Assert
    }

    @Test
    public void validateMaximumLevel_NotWithMaxStartingLevel_NoExceptionThrown() throws MaxLevelReachedException {
        // Arrange
        int checkedLevel = 12;

        // Act
        level.validateMaximumLevel(checkedLevel);

        // Assert
        // Geen exception verwacht!
    }

    @Test(expected = MaxLevelReachedException.class)
    public void validateMaximumLevel_WithMaxStartingLevel_MaxLevelReachedException() throws MaxLevelReachedException {
        // Arrange
        int checkedLevel = Level.MAX_LEVEL;

        // Act
        level.validateMaximumLevel(checkedLevel);

        // Assert
    }

    @Test
    public void onChangeLevel_Plus1_LevelIsIncreasedWith1() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();

        // Act
        int levelChangeValue = 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_Plus3_LevelIsIncreasedWith3() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();

        // Act
        int levelChangeValue = 3;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_UpToMaxLevel_LevelIsIncreased() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();

        // Act
        int levelChangeValue = Level.MAX_LEVEL - previousLevel;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test(expected = MaxLevelReachedException.class)
    public void onChangeLevel_OverMaxLevel_ExperienceMaxException() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();

        // Act
        int levelChangeValue = Level.MAX_LEVEL - previousLevel + 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
    }

    @Test
    public void onChangeLevel_OverMaxLevel_LevelIsNotIncreased() throws MinLevelReachedException {
        // Arrange
        int previousLevel = level.getCurrentLevel();

        // Act
        int levelChangeValue = Level.MAX_LEVEL - previousLevel + 1;
        try {
            level.onChangeLevel(levelChangeValue);
        } catch (MaxLevelReachedException e) {
            // Niets doen
        }

        // Assert
        assertLevelNotChanged(level, previousLevel);
    }

    @Test
    public void onChangeLevel_Minus1_LevelIsDecreasedWith1() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        int previousLevel = 10;
        Level level = getNewLevel(previousLevel);

        // Act
        int levelChangeValue = -1;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_Minus3_LevelIsDecreasedWith3() throws MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        int previousLevel = 10;
        Level level = getNewLevel(previousLevel);

        // Act
        int levelChangeValue = -3;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test
    public void onChangeLevel_UpToMinLevel_LevelIsDecreased() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        int previousLevel = 10;
        Level level = getNewLevel(previousLevel);

        // Act
        int levelChangeValue = Level.MIN_LEVEL - previousLevel;
        level.onChangeLevel(levelChangeValue);

        // Assert
        assertLevelChanged(level, previousLevel, levelChangeValue);
    }

    @Test(expected = MinLevelReachedException.class)
    public void onChangeLevel_OverMinLevel_ExperienceMinException() throws MaxLevelReachedException,
            MinLevelReachedException {
        // Arrange
        int previousLevel = 10;
        Level level = getNewLevel(previousLevel);

        // Act
        int levelChangeValue = Level.MIN_LEVEL - previousLevel - 1;
        level.onChangeLevel(levelChangeValue);

        // Assert
    }

    @Test
    public void onChangeLevel_OverMinLevel_LevelIsNotDecreased() throws MaxLevelReachedException {
        // Arrange
        int previousLevel = 10;
        Level level = getNewLevel(previousLevel);

        // Act
        int levelChangeValue = Level.MIN_LEVEL - previousLevel - 1;
        try {
            level.onChangeLevel(levelChangeValue);
        } catch (MinLevelReachedException e) {
            // Niets doen
        }

        // Assert
        assertLevelNotChanged(level, previousLevel);
    }

    private Level getNewLevel_WithDefaultValues() {
        return getNewLevel(DEFAULT_LEVEL);
    }

    private Level getNewLevel(int initialSavedLevel) {
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
        level.setReadyForLevelDownListener(readyForLevelDownListenerMock);
        level.setReadyForLevelUpListener(readyForLevelUpListenerMock);
        level.setLevelChangedListener(levelChangedListenerMock);
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
