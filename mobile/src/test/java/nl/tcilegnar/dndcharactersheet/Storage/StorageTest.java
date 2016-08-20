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
    public void testFileName_NotNull() {
        // Arrange

        // Act
        String fileName = storage.fileName();

        // Assert
        assertNotNull(fileName.isEmpty());
    }

    @Test
    public void testFileName_Storage() {
        // Arrange

        // Act
        String fileName = storage.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "Storage", fileName);
    }

    @Test
    public void testSaveAndLoadExperience() {
        // Arrange
        int expectedExperience = 1234;

        // Act
        storage.saveExperience(expectedExperience);
        int experience = storage.loadExperience();

        // Assert
        assertEquals(expectedExperience, experience);
    }

    @Test
    public void testSaveAndLoadLevel() {
        // Arrange
        int expectedLevel = 12;

        // Act
        storage.saveLevel(expectedLevel);
        int level = storage.loadLevel();

        // Assert
        assertEquals(expectedLevel, level);
    }

    @Test
    public void testSaveAndLoadReadyForLevelChange() {
        // Arrange
        int expectedReadyForLevelChange = 2;

        // Act
        storage.saveReadyForLevelChange(expectedReadyForLevelChange);
        int readyForLevelChange = storage.loadReadyForLevelChange();

        // Assert
        assertEquals(expectedReadyForLevelChange, readyForLevelChange);
    }
}