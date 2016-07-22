package nl.tcilegnar.dndcharactersheet.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.TWENTY;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.TWO;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTable.values;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.MAX_EXP_FOR_MAX_LEVEL;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.NoMaxExperienceForLevelException;
import static nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil.getMaxExperience;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LevelTableUtilTest {
    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_NegativeLevelDoesNotExist_IllegalArgumentException() {
        // Arrange
        int currentLevel = -1;

        // Act
        getMaxExperience(currentLevel);

        // Assert
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_LevelZeroDoesNotExist_IllegalArgumentException() {
        // Arrange
        int currentLevel = 0;

        // Act
        getMaxExperience(currentLevel);

        // Assert
    }

    @Test
    public void getMaxExperience_LevelOne_IsStartingExpFromOneLevelHigher() {
        // Arrange
        int currentLevel = 1;

        // Act
        int maxExp = getMaxExperience(currentLevel);

        // Assert
        assertEquals(TWO.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevelMinusOne_IsStartingExpFromMaxLevel() {
        // Arrange
        int currentLevel = values().length - 1;

        // Act
        int maxExp = getMaxExperience(currentLevel);

        // Assert
        assertEquals(TWENTY.startingExp, maxExp);
    }

    @Test
    public void getMaxExperience_MaxLevel_IsMaxExpForMaxLevel() {
        // Arrange
        int currentLevel = values().length;

        // Act
        int maxExp = getMaxExperience(currentLevel);

        // Assert
        assertEquals(MAX_EXP_FOR_MAX_LEVEL, maxExp);
    }

    @Test(expected = NoMaxExperienceForLevelException.class)
    public void getMaxExperience_MaxLevelPlusOne_IllegalArgumentException() {
        // Arrange
        int currentLevel = values().length + 1;

        // Act
        getMaxExperience(currentLevel);

        // Assert
    }
}