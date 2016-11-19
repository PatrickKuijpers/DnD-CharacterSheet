package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertTrue;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.TWENTY;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.TWO;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.values;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.MAX_EXP_FOR_MAX_LEVEL;
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
    public void getMaxExperience_LevelOne_IsStartingExpFromOneLevelHigher() {
        // Arrange
        int currentLevel = 1;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        assertEquals(TWO.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevelMinusOne_IsStartingExpFromMaxLevel() {
        // Arrange
        int currentLevel = values().length - 1;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        assertEquals(TWENTY.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevel_IsMaxExpForMaxLevel() {
        // Arrange
        int currentLevel = values().length;

        // Act
        int maxExp = levelTableUtil.getMaxExperience(currentLevel);

        // Assert
        assertEquals(MAX_EXP_FOR_MAX_LEVEL, maxExp);
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_MaxLevelPlusOne_IllegalArgumentException() {
        // Arrange
        int currentLevel = values().length + 1;

        // Act
        levelTableUtil.getMaxExperience(currentLevel);

        // Assert
    }
}