package nl.tcilegnar.dndcharactersheet.Experience.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.preference.Preference;
import android.support.annotation.StringRes;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.DefaultSettingValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@SuppressWarnings("all") // TODO
public class ExperienceSettingsTest {
    private ExperienceSettings settings;

    @Before
    public void setUp() {
        settings = ExperienceSettings.getInstance();
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
    public void fileName_ExperienceSettings() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "ExperienceSettings", fileName);
    }

    @Test
    public void getExperiencePickerStepSize_NotSaved_GetDefault() {
        // Arrange

        // Act
        int stepSize = settings.getExperiencePickerStepSize();

        // Assert
        // TODO: Welk van deze 2?
        int defaultValue = Integer.valueOf(DefaultSettingValue.EXP_UPDATE_PICKER_STEPSIZE.value);
        // int defaultValue = Integer.valueOf(App.getContext().getString(R.string
        // .setting_defaultvalue_experience_picker_steps));
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
        boolean defaultValue = Boolean.valueOf(DefaultSettingValue.ALLOW_LEVEL_DOWN.value);
        assertEquals(defaultValue, isLevelDownAllowed);
    }

    @Test
    public void isLevelDownAllowed_Saved_GetSavedValue() {
        // Arrange
        boolean expectedValue = !Boolean.valueOf(DefaultSettingValue.ALLOW_LEVEL_DOWN.value);
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
    public void isExperienceUpdateTypeInput_SavedNumberSlider_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_number_slider);
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
    public void isExperienceUpdateTypeNumberslider_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isExpUpdateTypeNumberSlider = settings.isExperienceUpdateTypeNumberSlider();

        // Assert
        boolean defaultValue = true;
        assertEquals(defaultValue, isExpUpdateTypeNumberSlider);
    }

    @Test
    public void isExperienceUpdateTypeNumberSliderSavedInput_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_input);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeNumberSlider = settings.isExperienceUpdateTypeNumberSlider();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isExpUpdateTypeNumberSlider);
    }

    @Test
    public void isExperienceUpdateTypeNumberSlider_SavedNumberSlider_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_experience_update_type_number_slider);
        savePreference(R.string.setting_key_experience_update_type, savedUpdateType);

        // Act
        boolean isExpUpdateTypeNumberSlider = settings.isExperienceUpdateTypeNumberSlider();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isExpUpdateTypeNumberSlider);
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