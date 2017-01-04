package nl.tcilegnar.dndcharactersheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class BuildConfigTest {
    protected static final String APPLICATION_ID_BASE = "nl.tcilegnar.dndcharactersheet";

    @Test
    public void BuildConfig_ApplicationId_BaseShouldContainNlTcilegnarDndCharacterSheet() {
        // Arrange

        // Act
        String actualApplicationId = BuildConfig.APPLICATION_ID;

        // Assert
        assertTrue(actualApplicationId.contains(APPLICATION_ID_BASE));
    }

    @Test
    public abstract void BuildConfig_ApplicationId_SuffixDependsOnProductFlavor();

    @Test
    public abstract void BuildConfig_ApplicationId_SuffixDependsOnBuildType();
}
