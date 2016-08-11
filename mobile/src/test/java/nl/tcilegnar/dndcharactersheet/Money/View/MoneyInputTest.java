package nl.tcilegnar.dndcharactersheet.Money.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyInputTest {
    private MoneyInput moneyInput;
    private MoneySettings moneySettingsMock;

    @Test
    public void moneyInputPublicConstructor() {
        // Arrange

        // Act
        MoneyInput moneyInput = new MoneyInput(getContext(), null);

        // Assert
        assertNotNull(moneyInput.settings);
        assertTrue(moneyInput.settings instanceof MoneySettings);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsInput_ShouldBeVisible() {
        // Arrange
        initMoneyInput();
        doReturn(true).when(moneySettingsMock).isMoneyUpdateTypeInput();

        // Act
        boolean shouldBeVisible = moneyInput.shouldBeVisible();

        // Assert
        assertEquals(true, shouldBeVisible);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsNotInput_ShouldNotBeVisible() {
        // Arrange
        initMoneyInput();
        doReturn(false).when(moneySettingsMock).isMoneyUpdateTypeInput();

        // Act
        boolean shouldBeVisible = moneyInput.shouldBeVisible();

        // Assert
        assertEquals(false, shouldBeVisible);
    }

    @Test
    public void setMoneyValue_SetPositiveNumber_ValueIsSet() {
        // Arrange
        initMoneyInput();
        int expectedInputValue = 1;

        // Act
        moneyInput.setMoneyValue(expectedInputValue);

        // Assert
        assertEquals(String.valueOf(expectedInputValue), moneyInput.getText().toString());
    }

    @Test
    // TODO exception? negatief niet mogelijk?
    public void setMoneyValue_SetNegativeNumber_ValueIsNotSet___ExpectException() {
        // Arrange
        initMoneyInput();
        int expectedInputValue = -1;

        // Act
        moneyInput.setMoneyValue(expectedInputValue);

        // Assert
        assertEquals(String.valueOf(expectedInputValue), moneyInput.getText().toString());
    }

    @Test
    // TODO exception? te hoog niet mogelijk?
    public void setMoneyValue_SetTooHighNumber_ValueIsNotSet___ExpectException() {
        // Arrange
        initMoneyInput();
        int expectedInputValue = 9999;

        // Act
        moneyInput.setMoneyValue(expectedInputValue);

        // Assert
        assertEquals(String.valueOf(expectedInputValue), moneyInput.getText().toString());
    }

    private void initMoneyInput() {
        moneySettingsMock = mock(MoneySettings.class);
        moneyInput = new MoneyInput(getContext(), null, moneySettingsMock);
    }

    private Context getContext() {
        return App.getContext();
    }
}