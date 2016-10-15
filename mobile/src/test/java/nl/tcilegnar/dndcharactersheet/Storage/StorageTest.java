package nl.tcilegnar.dndcharactersheet.Storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class StorageTest {
    private Storage storage;

    @Before
    public void setUp() {
        storage = new Storage();
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
    public void fileName_Storage() {
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
    public void saveAndLoadTempHp() {
        // Arrange
        int expectedTempHp = 4;

        // Act
        storage.saveTempHp(expectedTempHp);
        int tempHp = storage.loadTempHp();

        // Assert
        assertEquals(expectedTempHp, tempHp);
    }
}
