package nl.tcilegnar.dndcharactersheet.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Level.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@SuppressWarnings("all") // TODO
public class SettingsTest {
    public static final String SETTINGS_STUB_FILE_NAME = "SettingsStubFileName";

    private SettingsStub settings;

    @Before
    public void setUp() {
        settings = new SettingsStub();
    }

    @Test
    public void savePreferenceValue_RandomValue_SettingsChangedListenerCalled() {
        // Arrange
        BaseStorageFragment settingsChangedListener = mock(BaseStorageFragment.class);
        settings.addSettingsChangedListener(settingsChangedListener);

        // Act
        Preference preference = mock(Preference.class);
        settings.savePreferenceValue(preference, "");

        // Assert
        verify(settingsChangedListener).onSettingsChanged();
    }

    @Test
    public void savePreferenceValue_Boolean_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        boolean expectedSavedValue = true;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        boolean savedValue = getSharedPrefs().getBoolean(key, false);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void savePreferenceValue_String_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        String expectedSavedValue = "testValue";

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        String savedValue = getSharedPrefs().getString(key, "");

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void savePreferenceValue_Integer_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        int expectedSavedValue = 123;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        int savedValue = getSharedPrefs().getInt(key, 0);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void savePreferenceValue_Float_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        float expectedSavedValue = 1.23F;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        float savedValue = getSharedPrefs().getFloat(key, 0);

        // Assert
        double delta = 0.0;
        assertEquals(expectedSavedValue, savedValue, delta);
    }

    @Test
    public void savePreferenceValue_Long_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        long expectedSavedValue = 1234567890;

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        long savedValue = getSharedPrefs().getLong(key, 0);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void savePreferenceValue_StringSet_ValueSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        Set<String> expectedSavedValue = new HashSet<>(Arrays.asList("T", "E", "S", "T"));

        // Act
        settings.savePreferenceValue(preference, expectedSavedValue);
        Set<String> savedValue = getSharedPrefs().getStringSet(key, null);

        // Assert
        assertEquals(expectedSavedValue, savedValue);
    }

    @Test
    public void savePreferenceValue_OtherObject_ValueNotSaved() {
        // Arrange
        String key = "testKey";
        Preference preference = getPreference(key);
        Level expectedSavedValue = new Level();

        // Act
        boolean isSaved = settings.savePreferenceValue(preference, expectedSavedValue);

        // Assert
        assertFalse(isSaved);
    }

    private <T> void savePreference(@StringRes int keyId, T value) {
        String key = App.getContext().getString(keyId);
        settings.savePreferenceValue(getPreference(key), value);
    }

    private Preference getPreference(String key) {
        Preference preference = new Preference(App.getContext());
        preference.setKey(key);
        return preference;
    }

    private SharedPreferences getSharedPrefs() {
        return App.getContext().getSharedPreferences(settings.fileName(), Context.MODE_PRIVATE);
    }

    public class SettingsStub extends Settings {
        @Override
        protected String fileName() {
            return SETTINGS_STUB_FILE_NAME;
        }
    }
}