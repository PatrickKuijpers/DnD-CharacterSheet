package nl.tcilegnar.dndcharactersheet;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class BuildConfigTestForProductFlavor extends BuildConfigTest {
    protected static final String APPLICATION_ID_WITH_FLAVOR = APPLICATION_ID_BASE + ".version_three_five";

    @Override
    public void BuildConfig_ApplicationId_SuffixDependsOnProductFlavor() {
        // Arrange

        // Act
        String actualApplicationId = BuildConfig.APPLICATION_ID;

        // Assert
        assertTrue(actualApplicationId.contains(APPLICATION_ID_WITH_FLAVOR));
    }
}
