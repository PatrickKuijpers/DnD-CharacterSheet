package nl.tcilegnar.dndcharactersheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppTestVersionName {
    @Test
    public void getResourceString_AppName_ShouldContainVersionNumber() {
        // Arrange

        // Act
        String actualAppName = App.getResourceString(R.string.app_name);

        // Assert
        assertTrue(actualAppName.contains("5.0"));
    }
}
