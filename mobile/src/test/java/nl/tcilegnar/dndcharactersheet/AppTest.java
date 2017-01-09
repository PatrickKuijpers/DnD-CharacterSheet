package nl.tcilegnar.dndcharactersheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppTest {
    @Test
    public void getContext_ShouldBeApplicationContext() {
        // Arrange

        // Act
        Context actualContext = App.getContext();

        // Assert
        assertEquals(getApplicationContext(), actualContext);
    }

    private Context getApplicationContext() {
        return RuntimeEnvironment.application.getApplicationContext();
    }
}
