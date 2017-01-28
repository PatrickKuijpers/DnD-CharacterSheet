package nl.tcilegnar.dndcharactersheet.Storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static nl.tcilegnar.dndcharactersheet.Storage.Storage.Key;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class StorageTest {
    private static final String FILENAME = "test";
    private Storage storage;

    @Before
    public void setUp() {
        storage = new Storage(FILENAME);
    }

    @Test
    public void fileName_NotNull() {
        // Arrange

        // Act
        String fileName = storage.fileName();

        // Assert
        assertNotNull(fileName.isEmpty());
    }

    @Test
    public void fileName_IsCorrect() {
        // Arrange

        // Act
        String fileName = storage.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "Storage", fileName);
    }

    @Test
    public void saveAndLoadExperience() {
        // Arrange
        int expectedExperience = 1234;

        // Act
        storage.saveExperience(expectedExperience);
        int experience = storage.loadExperience();

        // Assert
        assertEquals(expectedExperience, experience);
    }

    @Test
    public void loadExperience_DefaultValue() {
        // Arrange
        int defaultValue = Key.CURRENT_EXP.defaultValue;

        // Act
        int experience = storage.loadExperience();

        // Assert
        assertEquals(defaultValue, experience);
    }

    @Test
    public void saveAndLoadLevel() {
        // Arrange
        int expectedLevel = 12;

        // Act
        storage.saveLevel(expectedLevel);
        int level = storage.loadLevel();

        // Assert
        assertEquals(expectedLevel, level);
    }

    @Test
    public void loadLevel_DefaultValue() {
        // Arrange
        int defaultValue = Key.CURRENT_LEVEL.defaultValue;

        // Act
        int level = storage.loadLevel();

        // Assert
        assertEquals(defaultValue, level);
    }

    @Test
    public void saveAndLoadReadyForLevelChange() {
        // Arrange
        int expectedReadyForLevelChange = 2;

        // Act
        storage.saveReadyForLevelChange(expectedReadyForLevelChange);
        int readyForLevelChange = storage.loadReadyForLevelChange();

        // Assert
        assertEquals(expectedReadyForLevelChange, readyForLevelChange);
    }

    @Test
    public void loadReadyForLevelChange_DefaultValue() {
        // Arrange
        int defaultValue = Key.READY_FOR_LEVEL_CHANGE.defaultValue;

        // Act
        int readyForLevelChange = storage.loadReadyForLevelChange();

        // Assert
        assertEquals(defaultValue, readyForLevelChange);
    }

    @Test
    public void saveAndLoadAbility() {
        // Arrange
        int expectedAbilityValue = 11;
        Key expectedAbilityKey = Key.CHARISMA;

        // Act
        storage.saveAbility(expectedAbilityKey, expectedAbilityValue);
        int abilityValue = storage.loadAbility(expectedAbilityKey);

        // Assert
        assertEquals(expectedAbilityValue, abilityValue);
    }

    @Test
    public void loadAbility_DefaultValue() {
        // Arrange
        Key expectedAbilityKey = Key.CHARISMA;

        // Act
        int abilityValue = storage.loadAbility(expectedAbilityKey);

        // Assert
        assertEquals(expectedAbilityKey.defaultValue, abilityValue);
    }

    @Test
    public void saveAndLoadPlatinum() {
        // Arrange
        int expectedPlatinum = 12;

        // Act
        storage.savePlatinum(expectedPlatinum);
        int platinum = storage.loadPlatinum();

        // Assert
        assertEquals(expectedPlatinum, platinum);
    }

    @Test
    public void loadPlatinum_DefaultValue() {
        // Arrange
        int defaultValue = Key.PLATINUM.defaultValue;

        // Act
        int platinum = storage.loadPlatinum();

        // Assert
        assertEquals(defaultValue, platinum);
    }

    @Test
    public void saveAndLoadGold() {
        // Arrange
        int expectedGold = 12;

        // Act
        storage.saveGold(expectedGold);
        int gold = storage.loadGold();

        // Assert
        assertEquals(expectedGold, gold);
    }

    @Test
    public void loadGold_DefaultValue() {
        // Arrange
        int defaultValue = Key.GOLD.defaultValue;

        // Act
        int gold = storage.loadGold();

        // Assert
        assertEquals(defaultValue, gold);
    }

    @Test
    public void saveAndLoadSilver() {
        // Arrange
        int expectedSilver = 12;

        // Act
        storage.saveSilver(expectedSilver);
        int silver = storage.loadSilver();

        // Assert
        assertEquals(expectedSilver, silver);
    }

    @Test
    public void loadSilver_DefaultValue() {
        // Arrange
        int defaultValue = Key.SILVER.defaultValue;

        // Act
        int silver = storage.loadSilver();

        // Assert
        assertEquals(defaultValue, silver);
    }

    @Test
    public void saveAndLoadBronze() {
        // Arrange
        int expectedBronze = 12;

        // Act
        storage.saveBronze(expectedBronze);
        int bronze = storage.loadBronze();

        // Assert
        assertEquals(expectedBronze, bronze);
    }

    @Test
    public void loadBronze_DefaultValue() {
        // Arrange
        int defaultValue = Key.BRONZE.defaultValue;

        // Act
        int bronze = storage.loadBronze();

        // Assert
        assertEquals(defaultValue, bronze);
    }

    @Test
    public void saveAndLoadTotalHp() {
        // Arrange
        int expectedTotalHp = 12;

        // Act
        storage.saveTotalHp(expectedTotalHp);
        int totalHp = storage.loadTotalHp();

        // Assert
        assertEquals(expectedTotalHp, totalHp);
    }

    @Test
    public void loadTotalHp_DefaultValue() {
        // Arrange
        int defaultValue = Key.TOTAL_HP.defaultValue;

        // Act
        int totalHp = storage.loadTotalHp();

        // Assert
        assertEquals(defaultValue, totalHp);
    }

    @Test
    public void saveAndLoadCurrentHp() {
        // Arrange
        int expectedCurrentHp = 4;

        // Act
        storage.saveCurrentHp(expectedCurrentHp);
        int currentHp = storage.loadCurrentHp();

        // Assert
        assertEquals(expectedCurrentHp, currentHp);
    }

    @Test
    public void loadCurrentHp_DefaultValue() {
        // Arrange
        int defaultValue = Key.CURRENT_HP.defaultValue;

        // Act
        int currentHp = storage.loadCurrentHp();

        // Assert
        assertEquals(defaultValue, currentHp);
    }

    @Test
    public void saveAndLoadTempHp() {
        // Arrange
        int expectedTempHp = 4;

        // Act
        storage.saveTempHp(expectedTempHp);
        int tempHp = storage.loadTempHp();

        // Assert
        assertEquals(expectedTempHp, tempHp);
    }

    @Test
    public void loadTempHp_DefaultValue() {
        // Arrange
        int defaultValue = Key.TEMP_HP.defaultValue;

        // Act
        int tempHp = storage.loadTempHp();

        // Assert
        assertEquals(defaultValue, tempHp);
    }
}
