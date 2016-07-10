package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.ViewGroup.LevelIndicatorView;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LevelTest {
    private Level level;

    @Before
    public void setUp() {
        level = new Level();
    }

    @Test
    public void testConstructor_StorageValuePositive_IsSetAsCurrentLevel() throws IOException {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int expectedSavedLevel = 12;
        doReturn(expectedSavedLevel).when(storageMock).loadLevel();

        // Act
        level = new Level(storageMock);

        // Assert
        verify(storageMock, times(1)).loadLevel();
        assertEquals(expectedSavedLevel, level.getCurrentLevel());
    }

    @Test
    public void testGetCurrentLevel_NoValueSet_ValueIsDefault() {
        // Arrange

        // Act
        int currentLevel = level.getCurrentLevel();

        // Assert
        assertEquals(1, currentLevel);
    }

    @Test
    public void testOnExperienceMaxReached_WithStartingLevel_LevelIncreasedBy1() throws MaxLevelReachedException {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int initialSavedLevel = 12;
        doReturn(initialSavedLevel).when(storageMock).loadLevel();
        Level level = getLevelWithMocks(storageMock);

        // Act
        level.onExperienceMaxReached();

        // Assert
        assertEquals(initialSavedLevel + 1, level.getCurrentLevel());
    }

    @Test(expected = MaxLevelReachedException.class)
    public void testOnExperienceMaxReached_WithMaxStartingLevel_MaxLevelReachedException() throws
            MaxLevelReachedException {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int initialSavedLevel = level.getMaxLevel();
        doReturn(initialSavedLevel).when(storageMock).loadLevel();
        Level level = getLevelWithMocks(storageMock);

        // Act
        level.onExperienceMaxReached();

        // Assert
    }

    @Test
    public void testOnExperienceMaxReached_WithMaxStartingLevel_LevelNotIncreased() {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int initialSavedLevel = level.getMaxLevel();
        doReturn(initialSavedLevel).when(storageMock).loadLevel();
        Level level = getLevelWithMocks(storageMock);

        // Act
        try {
            level.onExperienceMaxReached();
        } catch (MaxLevelReachedException e) {
            // Doe niets
        }

        // Assert
        assertEquals(initialSavedLevel, level.getCurrentLevel());
    }

    @Test
    public void testSaveLevel_SaveZeroLevel_StorageMethodCalledWithZero() {
        // Arrange
        Storage storageMock = mock(Storage.class);
        Level level = new Level(storageMock);

        // Act
        level.save();

        // Assert
        int expectedSavedLevel = 0;
        verify(storageMock, times(1)).saveLevel(eq(expectedSavedLevel));
    }

    @Test
    public void testSaveLevel_SavePositiveLevel_StorageMethodCalledWithSameValue() {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int expectedSavedLevel = 10;
        doReturn(expectedSavedLevel).when(storageMock).loadLevel();
        Level level = new Level(storageMock);

        // Act
        level.save();

        // Assert
        verify(storageMock, times(1)).saveLevel(eq(expectedSavedLevel));
    }

    private Level getLevelWithMocks(Storage storageMock) {
        Level level = new Level(storageMock);
        LevelIndicatorView levelIndicatorViewMock = mock(LevelIndicatorView.class);
        level.setReadyForLevelUpListener(levelIndicatorViewMock);
        return level;
    }
}
