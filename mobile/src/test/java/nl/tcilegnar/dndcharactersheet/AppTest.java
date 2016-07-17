package nl.tcilegnar.dndcharactersheet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.content.res.Resources;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppTest {
    @Test
    public void getContext() {
        // Arrange
        Context expectedContext = RuntimeEnvironment.application.getApplicationContext();

        // Act
        Context actualContext = App.getContext();
        //		Context actualContext = RuntimeEnvironment.application;

        // Assert
        assertEquals(expectedContext, actualContext);
    }

    @Test
    public void getAppResources() {
        // Arrange
        Resources expectedResources = RuntimeEnvironment.application.getApplicationContext().getResources();

        // Act
        Resources actualResources = App.getAppResources();

        // Assert
        assertEquals(expectedResources.getString(R.string.app_name), actualResources.getString(R.string.app_name));
    }

    @Test
    public void isDebug_Unittest_True() {
        // Arrange

        // Act
        boolean isDebug = App.isDebug();

        // Assert
        assertTrue(isDebug);
    }

    @Test
    @Ignore("Kan nog niet test afdwingen")
    public void isDebug_Test_True() {
        // Arrange
        // TODO

        // Act
        boolean isDebug = App.isDebug();

        // Assert
        assertTrue(isDebug);
    }

    @Test
    @Ignore("Kan nog niet prod afdwingen")
    public void isDebug_Prod_False() {
        // Arrange
        // TODO

        // Act
        boolean isDebug = App.isDebug();

        // Assert
        assertFalse(isDebug);
    }
}
