package nl.tcilegnar.dndcharactersheet.Storage;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.preference.Preference;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class SettingsTest {
    private Settings settings;
    private BaseStorageFragment settingsChangedListener;

    @Before
    public void setUp() {
        settings = Settings.getInstance();
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
    public void fileName_Settings() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "Settings", fileName);
    }

    @Test
    public void shouldShowHints_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean shouldShowHints = settings.shouldShowHints();

        // Assert
        boolean defaultValue = Boolean.valueOf(Settings.DefaultValue.SHOW_HINTS.value);
        assertEquals(defaultValue, shouldShowHints);
    }

    @Test
    public void shouldShowHints_Saved_GetSavedValue() {
        // Arrange
        boolean expectedValue = !Boolean.valueOf(Settings.DefaultValue.SHOW_HINTS.value);
        savePreference(R.string.setting_key_show_hints, expectedValue);

        // Act
        boolean shouldShowHints = settings.shouldShowHints();

        // Assert
        assertEquals(expectedValue, shouldShowHints);
    }

    @Test
    public void getExperiencePickerStepSize_NotSaved_GetDefault() {
        // Arrange

        // Act
        int stepSize = settings.getExperiencePickerStepSize();

        // Assert
        // TODO: Welk van deze 2?
        int defaultValue = Integer.valueOf(Settings.DefaultValue.EXP_UPDATE_PICKER_STEPSIZE.value);
        assertEquals(defaultValue, stepSize);
        //        int defaultValue = Integer.valueOf(App.getContext().getString(R.string
        //                .setting_defaultvalue_experience_picker_steps));
        assertEquals(defaultValue, stepSize);
    }

    @Test
    public void getExperiencePickerStepSize_Saved_GetSavedValue() {
        // Arrange
        int expectedStepSize = 250;
        savePreference(R.string.setting_key_experience_picker_steps, String.valueOf(expectedStepSize));

        // Act
        int stepSize = settings.getExperiencePickerStepSize();

        // Assert
        assertEquals(expectedStepSize, stepSize);
    }

    @Test
    public void isLevelDownAllowed_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isLevelDownAllowed = settings.isLevelDownAllowed();

        // Assert
        boolean defaultValue = Boolean.valueOf(Settings.DefaultValue.ALLOW_LEVEL_DOWN.value);
        assertEquals(defaultValue, isLevelDownAllowed);
    }

    @Test
    public void isLevelDownAllowed_Saved_GetSavedValue() {
        // Arrange
        boolean expectedValue = !Boolean.valueOf(Settings.DefaultValue.ALLOW_LEVEL_DOWN.value);
        savePreference(R.string.setting_key_allow_level_down, expectedValue);

        // Act
        boolean isLevelDownAllowed = settings.isLevelDownAllowed();

        // Assert
        assertEquals(expectedValue, isLevelDownAllowed);
    }

    @Test
    public void isExperienceUpdateTypeInput_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isExpUpdateTypeInput = settings.isExperienceUpdateTypeInput();

        // Assert
        boolean defaultValue = false;
        assertEquals(defaultValue, isExpUpdateTypeInput);
    }

    @Test
    public void isExperienceUpdateTypeInput_SavedNumberPicker_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_numberpicker);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeInput = settings.isExperienceUpdateTypeInput();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isExpUpdateTypeInput);
    }

    @Test
    public void isExperienceUpdateTypeInput_SavedInput_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_input);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeInput = settings.isExperienceUpdateTypeInput();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isExpUpdateTypeInput);
    }

    @Test
    public void isExperienceUpdateTypeNumberPicker_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isExpUpdateTypeNumberPicker = settings.isExperienceUpdateTypeNumberPicker();

        // Assert
        boolean defaultValue = true;
        assertEquals(defaultValue, isExpUpdateTypeNumberPicker);
    }

    @Test
    public void isExperienceUpdateTypeInput_SavedInput_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_input);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeNumberPicker = settings.isExperienceUpdateTypeNumberPicker();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isExpUpdateTypeNumberPicker);
    }

    @Test
    public void isExperienceUpdateTypeInput_SavedNumberPicker_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_numberpicker);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeNumberPicker = settings.isExperienceUpdateTypeNumberPicker();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isExpUpdateTypeNumberPicker);
    }

    @Test
    public void savePreferenceValue_RandomValue_SettingsChangedListenerCalled() {
        // Arrange

        // Act
        Preference preference = mock(Preference.class);
        settings.savePreferenceValue(preference, "");

        // Assert
        verify(settingsChangedListener).onSettingsChanged();
    }

    @Test
    @SuppressWarnings("all")
    public void savePreferenceValue_String_ValueSaved() {
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
    public void savePreferenceValue_Integer_ValueSaved() {
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
    public void savePreferenceValue_Float_ValueSaved() {
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
    public void savePreferenceValue_Long_ValueSaved() {
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

    private void savePreference(@StringRes int keyId, boolean value) {
        String key = settings.getKey(keyId);
        settings.save(key, value);
    }

    private void savePreference(@StringRes int keyId, String value) {
        String key = settings.getKey(keyId);
        settings.save(key, value);
    }

    private void savePreference(@StringRes int keyId, int value) {
        String key = settings.getKey(keyId);
        settings.save(key, value);
    }

    private Preference getPreference(String key) {
        Preference preference = new Preference(App.getContext());
        preference.setKey(key);
        return preference;
    }
}