package nl.tcilegnar.dndcharactersheet.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ResTest {

    @Test
    public void getAppResources_ShouldBeResourcesFromApplicationContext() {
        // Arrange

        // Act
        android.content.res.Resources actualResources = Res.getApplicationResources();

        // Assert
        assertEquals(getApplicationResources(), actualResources);
    }

    @Test
    public void getString_ShouldContainSameStringAsGetStringFromApplicationResources() {
        // Arrange

        // Act
        String actualString = Res.getString(R.string.app_name);

        // Assert
        String expectedString = getApplicationResources().getString(R.string.app_name);
        assertEquals(expectedString, actualString);
    }

    @Test
    public void getInt_ShouldContainSameIntegerAsGetIntegerFromApplicationResources() {
        // Arrange

        // Act
        Integer actualInteger = Res.getInt(R.integer.max_length_bronze_value);

        // Assert
        Integer expectedInteger = getApplicationResources().getInteger(R.integer.max_length_bronze_value);
        assertEquals(expectedInteger, actualInteger);
    }

    @Test
    public void getDimension_ShouldContainSameDimenAsGetDimensionFromApplicationResourcesDividedByDensity() {
        // Arrange

        // Act
        int actualDimension = Res.getDimension(R.dimen.textsize_default);

        // Assert
        float density = getApplicationResources().getDisplayMetrics().density;
        float dimension = getApplicationResources().getDimension(R.dimen.textsize_default);
        int expectedDimension = (int) (dimension / density);
        assertEquals(expectedDimension, actualDimension);
    }

    @Test
    public void getBoolean_ShouldContainSameBooleanAsGetBooleanFromApplicationResources() {
        // Arrange

        // Act
        boolean bool = Res.getBoolean(R.bool.is_portrait);

        // Assert
        boolean expectedBool = getApplicationResources().getBoolean(R.bool.is_portrait);
        assertEquals(expectedBool, bool);
    }

    @Test
    public void getColor_ShouldContainSameColorAsGetColorFromApplicationContext() {
        // Arrange

        // Act
        int actualColorInt = Res.getColor(R.color.colorAccent);

        // Assert
        int expectedColorInt = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        assertEquals(expectedColorInt, actualColorInt);
    }

    private Resources getApplicationResources() {
        return getApplicationContext().getResources();
    }

    private Context getApplicationContext() {
        return RuntimeEnvironment.application.getApplicationContext();
    }
}
