package nl.tcilegnar.dndcharactersheet.Settings.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.preference.Preference;
import android.support.annotation.StringRes;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.DefaultSettingValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainSettingsTest {
    private MainSettings settings;
    private BaseStorageFragment settingsChangedListener;

    @Before
    public void setUp() {
        settings = MainSettings.getInstance();
        setListeners();
    }

    private void setListeners() {
        settingsChangedListener = mock(BaseStorageFragment.class);
        settings.setSettingsChangedListener(settingsChangedListener);
    }

    @After
    public void tearDown() {
        settings.tearDown();
    }

    @Test
    public void fileName_NotNull() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertNotNull(fileName.isEmpty());
    }

    @Test
    public void fileName_MainSettings() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "MainSettings", fileName);
    }

    @Test
    public void shouldShowHints_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean shouldShowHints = settings.shouldShowHints();

        // Assert
        boolean defaultValue = Boolean.valueOf(DefaultSettingValue.SHOW_HINTS.value);
        assertEquals(defaultValue, shouldShowHints);
    }

    @Test
    public void shouldShowHints_Saved_GetSavedValue() {
        // Arrange
        boolean expectedValue = !Boolean.valueOf(DefaultSettingValue.SHOW_HINTS.value);
        savePreference(R.string.setting_key_show_hints, expectedValue);

        // Act
        boolean shouldShowHints = settings.shouldShowHints();

        // Assert
        assertEquals(expectedValue, shouldShowHints);
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
}