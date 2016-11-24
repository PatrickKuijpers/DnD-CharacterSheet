package nl.tcilegnar.dndcharactersheet;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyBuildConfigTestForDebug extends MyBuildConfigTest {
    @Override
    public void isDebug_DependsOnBuildType() {
        isDebug_True();
    }

    private void isDebug_True() {
        // Arrange

        // Act
        boolean isDebug = buildConfig.isDebug();

        // Assert
        assertTrue(isDebug);
    }
}