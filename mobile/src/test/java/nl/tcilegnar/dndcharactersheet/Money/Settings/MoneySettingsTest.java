package nl.tcilegnar.dndcharactersheet.Money.Settings;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@SuppressWarnings("all") // TODO
public class MoneySettingsTest {
    private MoneySettings settings;

    @Before
    public void setUp() {
        settings = MoneySettings.getInstance();
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
    public void fileName_MoneySettings() {
        // Arrange

        // Act
        String fileName = settings.fileName();

        // Assert
        assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen",
                "MoneySettings", fileName);
    }

    @Test
    public void isMoneyUpdateManual_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isMoneyUpdateManual = settings.isMoneyUpdateManual();

        // Assert
        boolean defaultValue = false;
        assertEquals(defaultValue, isMoneyUpdateManual);
    }

    @Test
    public void isMoneyUpdateManual_SavedTrueForIsMoneyUpdateManual_GetDefault() {
        // Arrange
        boolean expectedValue = true;
        savePreference(R.string.setting_key_money_update_manual, expectedValue);

        // Act
        boolean isMoneyUpdateManual = settings.isMoneyUpdateManual();

        // Assert
        assertEquals(expectedValue, isMoneyUpdateManual);
    }

    @Test
    public void isMoneyUpdateTypeInput_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isMoneyUpdateTypeInput = settings.isMoneyUpdateTypeInput();

        // Assert
        boolean defaultValue = false;
        assertEquals(defaultValue, isMoneyUpdateTypeInput);
    }

    @Test
    public void isMoneyUpdateTypeInput_SavedNumberPicker_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_numberpicker);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeInput = settings.isMoneyUpdateTypeInput();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isMoneyUpdateTypeInput);
    }

    @Test
    public void isMoneyUpdateTypeInput_SavedInput_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_input);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeInput = settings.isMoneyUpdateTypeInput();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isMoneyUpdateTypeInput);
    }

    @Test
    public void isMoneyUpdateTypeNumberSlider_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isMoneyUpdateTypeNumberSlider = settings.isMoneyUpdateTypeNumberSlider();

        // Assert
        boolean defaultValue = false;
        assertEquals(defaultValue, isMoneyUpdateTypeNumberSlider);
    }

    @Test
    public void isMoneyUpdateTypeNumberSlider_SavedNumberPicker_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_numberpicker);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeNumberSlider = settings.isMoneyUpdateTypeNumberSlider();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isMoneyUpdateTypeNumberSlider);
    }

    @Test
    public void isMoneyUpdateTypeNumberSlider_SavedNumberSlider_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_numberslider);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeNumberSlider = settings.isMoneyUpdateTypeNumberSlider();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isMoneyUpdateTypeNumberSlider);
    }

    @Test
    public void isMoneyUpdateTypeNumberPicker_NotSaved_GetDefault() {
        // Arrange

        // Act
        boolean isMoneyUpdateTypeNumberPicker = settings.isMoneyUpdateTypeNumberPicker();

        // Assert
        boolean defaultValue = true;
        assertEquals(defaultValue, isMoneyUpdateTypeNumberPicker);
    }

    @Test
    public void isMoneyUpdateTypeNumberPicker_SavedNumberSlider_False() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_numberslider);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeNumberPicker = settings.isMoneyUpdateTypeNumberPicker();

        // Assert
        boolean expectedValue = false;
        assertEquals(expectedValue, isMoneyUpdateTypeNumberPicker);
    }

    @Test
    public void isMoneyUpdateTypeNumberPicker_SavedNumberPicker_True() {
        // Arrange
        String savedUpdateType = App.getContext().getString(R.string.setting_entry_money_update_type_numberpicker);
        savePreference(R.string.setting_key_money_update_type, savedUpdateType);

        // Act
        boolean isMoneyUpdateTypeNumberPicker = settings.isMoneyUpdateTypeNumberPicker();

        // Assert
        boolean expectedValue = true;
        assertEquals(expectedValue, isMoneyUpdateTypeNumberPicker);
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