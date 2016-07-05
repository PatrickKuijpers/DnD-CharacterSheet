package nl.tcilegnar.dndcharactersheet.Storage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@SuppressWarnings("all")
public class SharedPrefsTest {
    private Settings settings;

    @Before
    public void setUp() {
        // Gebruikt om abstract SharedPrefs te kunnen instantiëren
        settings = new Settings();
    }

    @Test
    public void testGetPrefs_AbstractFileName_IsExtendedPrefs() {
        // Arrange

        // Act
        SharedPreferences prefs = settings.getPrefs();

        // Assert
        SharedPreferences defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        assertNotSame(defaultSharedPrefs, prefs);
        SharedPreferences extendedPrefs = App.getContext().getSharedPreferences(settings.fileName(), Context
                .MODE_PRIVATE);
        assertEquals(extendedPrefs, prefs);
    }

    @Test
    public void testGetPrefs_FileNameIsNull_IsDefaultPrefs() {
        // Arrange
        Settings settings = spy(new Settings());
        doReturn(null).when(settings).fileName();

        // Act
        SharedPreferences prefs = settings.getPrefs();

        // Assert
        SharedPreferences defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        assertEquals(defaultSharedPrefs, prefs);
        SharedPreferences extendedPrefs = App.getContext().getSharedPreferences(this.settings.fileName(), Context
                .MODE_PRIVATE);
        assertNotSame(extendedPrefs, prefs);
    }

    @Test
    public void testSaveAndLoadBoolean_True_ValueSaved() {
        // Arrange
        String key = "testKey1";
        boolean expectedSavedValue = true;

        // Act
        settings.save(key, expectedSavedValue);
        boolean savedValue = settings.loadBoolean(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadBoolean_False_ValueSaved() {
        // Arrange
        String key = "testKey2";
        boolean expectedSavedValue = false;

        // Act
        settings.save(key, expectedSavedValue);
        boolean savedValue = settings.loadBoolean(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadBoolean_Default() {
        // Arrange
        String key = "testKey3";
        boolean expectedDefaultValue = false;

        // Act
        boolean defaultValue = settings.loadBoolean(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadBoolean_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        boolean expectedDefaultValue = true;

        // Act
        boolean defaultValue = settings.loadBoolean(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testSaveAndLoadString_x_ValueSaved() {
        // Arrange
        String key = "testKey1";
        String expectedSavedValue = "testValue";

        // Act
        settings.save(key, expectedSavedValue);
        String savedValue = settings.loadString(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadString_Null_ValueSaved() {
        // Arrange
        String key = "testKey2";
        String expectedSavedValue = null;

        // Act
        settings.save(key, expectedSavedValue);
        String savedValue = settings.loadString(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadString_Default() {
        // Arrange
        String key = "testKey3";
        String expectedDefaultValue = null;

        // Act
        String defaultValue = settings.loadString(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadString_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        String expectedDefaultValue = "defaultValue";

        // Act
        String defaultValue = settings.loadString(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testSaveAndLoadInt_12_ValueSaved() {
        // Arrange
        String key = "testKey1";
        int expectedSavedValue = 12;

        // Act
        settings.save(key, expectedSavedValue);
        int savedValue = settings.loadInt(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadInt_Minus12_ValueSaved() {
        // Arrange
        String key = "testKey2";
        int expectedSavedValue = -12;

        // Act
        settings.save(key, expectedSavedValue);
        int savedValue = settings.loadInt(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadInt_Default() {
        // Arrange
        String key = "testKey3";
        int expectedDefaultValue = 0;

        // Act
        int defaultValue = settings.loadInt(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadInt_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        int expectedDefaultValue = 12;

        // Act
        int defaultValue = settings.loadInt(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testSaveAndLoadFloat_1p2_ValueSaved() {
        // Arrange
        String key = "testKey1";
        float expectedSavedValue = 1.2F;

        // Act
        settings.save(key, expectedSavedValue);
        float savedValue = settings.loadFloat(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadFloat_Minus1p2_ValueSaved() {
        // Arrange
        String key = "testKey2";
        float expectedSavedValue = -1.2F;

        // Act
        settings.save(key, expectedSavedValue);
        float savedValue = settings.loadFloat(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadFloat_Default() {
        // Arrange
        String key = "testKey3";
        float expectedDefaultValue = 0;

        // Act
        float defaultValue = settings.loadFloat(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadFloat_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        float expectedDefaultValue = 1.2F;

        // Act
        float defaultValue = settings.loadFloat(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testSaveAndLoadLong_123_ValueSaved() {
        // Arrange
        String key = "testKey1";
        long expectedSavedValue = 123;

        // Act
        settings.save(key, expectedSavedValue);
        long savedValue = settings.loadLong(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadLong_Minus123_ValueSaved() {
        // Arrange
        String key = "testKey2";
        long expectedSavedValue = -123;

        // Act
        settings.save(key, expectedSavedValue);
        long savedValue = settings.loadLong(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadLong_Default() {
        // Arrange
        String key = "testKey3";
        long expectedDefaultValue = 0;

        // Act
        long defaultValue = settings.loadLong(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadLong_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        long expectedDefaultValue = 123;

        // Act
        long defaultValue = settings.loadLong(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testSaveAndLoadStringSet_NoStrings_ValueSaved() {
        // Arrange
        String key = "testKey1";
        Set<String> expectedSavedValue = new HashSet<>();

        // Act
        settings.save(key, expectedSavedValue);
        Set<String> savedValue = settings.loadStringSet(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testSaveAndLoadStringSet_SeveralString_ValueSaved() {
        // Arrange
        String key = "testKey2";
        Set<String> expectedSavedValue = new HashSet<>(Arrays.asList("T", "E", "S", "T"));

        // Act
        settings.save(key, expectedSavedValue);
        Set<String> savedValue = settings.loadStringSet(key);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void testLoadStringSet_Default() {
        // Arrange
        String key = "testKey3";
        Set<String> expectedDefaultValue = null;

        // Act
        Set<String> defaultValue = settings.loadStringSet(key);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    public void testLoadStringSet_DefaultOverride() {
        // Arrange
        String key = "testKey4";
        Set<String> expectedDefaultValue = new HashSet<>(Arrays.asList("T", "E", "S", "T"));

        // Act
        Set<String> defaultValue = settings.loadStringSet(key, expectedDefaultValue);

        // Assert
        assertEquals(expectedDefaultValue, defaultValue);
    }

    @Test
    @Ignore("Kan App resources niet vinden")
    public void testGetKey() {
        // Arrange
        String expectedKey = App.getContext().getString(R.string.app_name);

        // Act
        String key = settings.getKey(R.string.app_name);

        // Assert
        assertEquals(expectedKey, key);
    }

    @Test(expected = Resources.NotFoundException.class)
    public void testGetKey_NoStringResourceId_ResourcesNotFoundException() {
        // Arrange

        // Act
        settings.getString(0);

        // Assert
    }

    @Test
    @Ignore("Kan App resources niet vinden")
    public void testGetString() {
        // Arrange
        String expectedString = App.getContext().getString(R.string.app_name);

        // Act
        String string = settings.getString(R.string.app_name);

        // Assert
        assertEquals(expectedString, string);
    }

    @Test(expected = Resources.NotFoundException.class)
    public void testGetString_NoStringResourceId_ResourcesNotFoundException() {
        // Arrange

        // Act
        settings.getString(0);

        // Assert
    }
}