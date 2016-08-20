package nl.tcilegnar.dndcharactersheet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.Utils.MyBuildConfig;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MyBuildConfigTest {
    private MyBuildConfig buildConfig;

    @Before
    public void setUp() {
        buildConfig = new MyBuildConfig();
    }

    @Test
    public void isDebug_Unittest_True() {
        // Arrange

        // Act
        boolean isDebug = buildConfig.isDebug();

        // Assert
        assertTrue(isDebug);
    }

    @Test
    @Ignore("Kan nog niet test afdwingen")
    public void isDebug_Test_True() {
        // Arrange

        // Act
        boolean isDebug = buildConfig.isDebug();

        // Assert
        assertTrue(isDebug);
    }

    @Test
    @Ignore("Kan nog niet prod afdwingen")
    public void isDebug_Prod_False() {
        // Arrange
        // TODO

        // Act
        boolean isDebug = buildConfig.isDebug();

        // Assert
        assertFalse(isDebug);
    }
}