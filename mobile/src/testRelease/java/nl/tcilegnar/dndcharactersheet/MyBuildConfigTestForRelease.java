package nl.tcilegnar.dndcharactersheet;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyBuildConfigTestForRelease extends MyBuildConfigTest {
    @Override
    public void isDebug_DependsOnBuildType() {
        isDebug_False();
    }

    private void isDebug_False() {
        // Arrange

        // Act
        boolean isDebug = buildConfig.isDebug();

        // Assert
        assertFalse(isDebug);
    }
}