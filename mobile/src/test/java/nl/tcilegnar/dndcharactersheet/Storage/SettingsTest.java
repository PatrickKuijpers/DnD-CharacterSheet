package nl.tcilegnar.dndcharactersheet.Storage;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.preference.Preference;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsTest {
    private Settings settings;

    @Before
    public void setUp() {
        settings = new Settings();
    }

    @Test
    public void testFileName_NotNull() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertNotNull(fileName.isEmpty());
    }

    @Test
    public void testFileName_Settings() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "Settings", fileName);
    }

    @Test
    @Ignore("Kan App resources niet vinden")
    public void testGetExperiencePickerStepSize_NotSaved_GetDefault() {
        // Arrange

        // Act
        int stepSize = settings.getExperiencePickerStepSize();

        // Assert
        // TODO: Welk van deze 2?
        //		assertEquals(DefaultValue.EXP_UPDATE_PICKER_STEPSIZE.value, stepSize);
        String defaultValue = App.getContext().getString(R.string.setting_defaultvalue_experience_update_picker_steps);
        assertEquals(defaultValue, stepSize);
    }

    @Test
    @Ignore("Opslaan van settings nog niet geimplementeerd")
    public void testGetExperiencePickerStepSize_Saved_GetSavedValue() {
        // Arrange
        int expectedStepSize = 100;
        //		settings.saveStepSize(expectedStepSize)

        // Act
        int stepSize = settings.getExperiencePickerStepSize();

        // Assert
        assertEquals(expectedStepSize, stepSize);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_Boolean_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        boolean expectedSavedValue = true;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        boolean savedValue = settings.loadBoolean(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_String_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        String expectedSavedValue = "testValue";

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        String savedValue = settings.loadString(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_Integer_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        int expectedSavedValue = 123;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        int savedValue = settings.loadInt(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_Float_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        float expectedSavedValue = 1.23F;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        float savedValue = settings.loadFloat(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_Long_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        long expectedSavedValue = 1234567890;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        long savedValue = settings.loadLong(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    @SuppressWarnings("all")
    public void testSavePreferenceValue_StringSet_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        Set<String> expectedSavedValue = new HashSet<>(Arrays.asList("T", "E", "S", "T"));

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        Set<String> savedValue = settings.loadStringSet(key);

        // Assert
        Assert.assertEquals(expectedSavedValue, savedValue);
    }

    private Preference getPreference(String key) {
        Preference preference = new Preference(App.getContext());
        preference.setKey(key);
        return preference;
    }
}