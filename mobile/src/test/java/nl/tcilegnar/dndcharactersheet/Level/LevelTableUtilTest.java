package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Level.entities.LevelTable;

import static junit.framework.Assert.assertTrue;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.ALL_LEVELS;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.MAX_EXP_FOR_MAX_LEVEL;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.MAX_LVL;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.MIN_LVL;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.NoMaxExperienceForLevelException;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LevelTableUtilTest {
    private LevelTableUtil levelTableUtil;

    @Before
    public void setUp() {
        levelTableUtil = new LevelTableUtil();
    }

    @Test
    public void newLevelTableUtil_ThrowsNoExceptions() {
        // Arrange

        // Act
        new LevelTableUtil();

        // Assert
        assertTrue("Constructor aanroepen zou zonder exceptions moeten plaatsvinden", true);
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_NegativeLevelDoesNotExist_IllegalArgumentException() {
        // Arrange
        int currentLevel = -1;

        // Act
        levelTableUtil.getMaxExperience(currentLevel);

        // Assert
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_LevelZeroDoesNotExist_IllegalArgumentException() {
        // Arrange
        int currentLevel = 0;

        // Act
        levelTableUtil.getMaxExperience(currentLevel);

        // Assert
    }

    @Test
    public void getMaxExperience_FirstLevel_IsStartingExpFromOneLevelHigher() {
        // Arrange
        int currentLevel = MIN_LVL;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        LevelTable nextLevel = getLevelEnum(currentLevel + 1);
        assertEquals(nextLevel.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevelMinusOne_IsStartingExpFromMaxLevel() {
        // Arrange
        int currentLevel = MAX_LVL - 1;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        LevelTable lastLevel = getLevelEnum(MAX_LVL);
        assertEquals(lastLevel.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevel_IsMaxExpForMaxLevel() {
        // Arrange
        int currentLevel = MAX_LVL;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        assertEquals(MAX_EXP_FOR_MAX_LEVEL, maxExp);
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_MaxLevelPlusOne_IllegalArgumentException() {
        // Arrange
        int currentLevel = ALL_LEVELS.length + 1;

        // Act
        levelTableUtil.getMaxExperience(currentLevel);

        // Assert
    }

    private LevelTable getLevelEnum(int currentLevel) {
        return ALL_LEVELS[currentLevel - 1];
    }
}