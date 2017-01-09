package nl.tcilegnar.dndcharactersheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.Utils.Res;

import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppNameTest {
    @Test
    public void getString_AppName_ShouldContainVersionNumber() {
        // Arrange

        // Act
        String actualAppName = Res.getString(R.string.app_name);

        // Assert
        assertTrue(actualAppName.contains("4.0"));
    }
}
