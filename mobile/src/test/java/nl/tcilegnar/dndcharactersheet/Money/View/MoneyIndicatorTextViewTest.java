package nl.tcilegnar.dndcharactersheet.Money.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.view.View;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyIndicatorTextViewTest {
    private static final boolean MANUAL = true;
    private static final boolean NOT_MANUAL = !MANUAL;
    private MoneySettings settingsMock;

    @Test
    public void moneyIndicatorTextView_PublicConstructor() {
        // Arrange

        // Act
        MoneyIndicatorTextView moneyIndicatorTextView = new MoneyIndicatorTextView(getContext(), null);

        // Assert
        assertNotNull(moneyIndicatorTextView.settings);
        assertTrue(moneyIndicatorTextView.settings instanceof MoneySettings);
    }

    @Test
    public void getMoneyValue_Default_NoTextIsValue0() {
        // Arrange
        MoneyIndicatorTextView moneyIndicatorTextView = getMoneyIndicatorTextView();

        // Act
        int moneyValue = moneyIndicatorTextView.getMoneyValue();

        // Assert
        assertEquals(0, moneyValue);
    }

    @Test
    public void getMoneyValue_TextIsSetTo12_GetValueOf12() {
        // Arrange
        int expectedValue = 12;
        MoneyIndicatorTextView moneyIndicatorTextView = getMoneyIndicatorTextView();
        moneyIndicatorTextView.setText(String.valueOf(expectedValue));

        // Act
        int moneyValue = moneyIndicatorTextView.getMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void setMoneyValue_TextIsSetTo12_ValueIsSetAsText() {
        // Arrange
        int expectedIntValue = 12;
        MoneyIndicatorTextView moneyIndicatorTextView = getMoneyIndicatorTextView();

        // Act
        moneyIndicatorTextView.setMoneyValue(expectedIntValue);

        // Assert
        String expectedStringValue = String.valueOf(expectedIntValue);
        assertEquals(expectedStringValue, moneyIndicatorTextView.getText());
    }

    @Test
    public void updateSettingsData_StartWithSettingsManualAndSwitchToSettingsNotManual_ShouldBeVisible() {
        // Arrange
        MoneyIndicatorTextView moneyIndicatorTextView = getMoneyIndicatorTextView(MANUAL);
        assertNotEquals(View.VISIBLE, moneyIndicatorTextView.getVisibility());

        doReturn(NOT_MANUAL).when(settingsMock).isMoneyUpdateManual();

        // Act
        moneyIndicatorTextView.updateSettingsData();

        // Assert
        assertEquals(View.VISIBLE, moneyIndicatorTextView.getVisibility());
    }

    @Test
    public void updateSettingsData_StartWithSettingsNotManualAndSwitchToSettingsManual_ShouldNotBeVisible() {
        // Arrange
        MoneyIndicatorTextView moneyIndicatorTextView = getMoneyIndicatorTextView(NOT_MANUAL);
        assertEquals(View.VISIBLE, moneyIndicatorTextView.getVisibility());

        doReturn(MANUAL).when(settingsMock).isMoneyUpdateManual();

        // Act
        moneyIndicatorTextView.updateSettingsData();

        // Assert
        assertNotEquals(View.VISIBLE, moneyIndicatorTextView.getVisibility());
    }

    private MoneyIndicatorTextView getMoneyIndicatorTextView() {
        return getMoneyIndicatorTextView(NOT_MANUAL);
    }

    private MoneyIndicatorTextView getMoneyIndicatorTextView(boolean manual) {
        settingsMock = mock(MoneySettings.class);
        doReturn(manual).when(settingsMock).isMoneyUpdateManual();
        return new MoneyIndicatorTextView(getContext(), null, settingsMock);
    }

    private Context getContext() {
        return App.getContext();
    }
}