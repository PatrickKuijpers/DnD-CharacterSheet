package nl.tcilegnar.dndcharactersheet.Utils;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Utils.MyBuildConfigTest;

import static junit.framework.TestCase.assertFalse;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyBuildConfigTestForDev extends MyBuildConfigTest {
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