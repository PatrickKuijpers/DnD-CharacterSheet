package nl.tcilegnar.dndcharactersheet.Money.View;

import org.junit.Before;
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
public class MoneySliderTest {
    private MoneySlider moneySlider;
    private MoneySettings moneySettingsMock;

    @Test
    public void moneySliderPublicConstructor() {
        // Arrange

        // Act
        MoneySlider moneySlider = new MoneySlider(getContext(), null);

        // Assert
        assertNotNull(moneySlider.settings);
        assertTrue(moneySlider.settings instanceof MoneySettings);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsSlider_ShouldBeVisible() {
        // Arrange
        initMoneySlider();
        doReturn(true).when(moneySettingsMock).isMoneyUpdateTypeNumberSlider();

        // Act
        boolean shouldBeVisible = moneySlider.shouldBeVisible();

        // Assert
        assertEquals(true, shouldBeVisible);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsNotSlider_ShouldNotBeVisible() {
        // Arrange
        initMoneySlider();
        doReturn(false).when(moneySettingsMock).isMoneyUpdateTypeNumberSlider();

        // Act
        boolean shouldBeVisible = moneySlider.shouldBeVisible();

        // Assert
        assertEquals(false, shouldBeVisible);
    }

    private void initMoneySlider() {
        moneySettingsMock = mock(MoneySettings.class);
        moneySlider = new MoneySlider(getContext(), null, moneySettingsMock);
    }

    private Context getContext() {
        return App.getContext();
    }
}