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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyIndicatorPickerTest {
    private MoneyIndicatorPicker moneyIndicatorPicker;
    private MoneySettings moneySettingsMock;

    @Test
    public void moneyIndicatorPickerPublicConstructor() {
        // Arrange

        // Act
        MoneyIndicatorPicker moneyIndicatorPicker = new MoneyIndicatorPicker(getContext(), null);

        // Assert
        assertNotNull(moneyIndicatorPicker.settings);
        assertTrue(moneyIndicatorPicker.settings instanceof MoneySettings);
    }

    @Test
    public void hideView_MoneyPickerShouldBeInvisibleInsteadOfGone() {
        // Arrange
        initMoneyIndicatorPicker();

        // Act
        moneyIndicatorPicker.hideView();

        // Assert
        int visibility = moneyIndicatorPicker.getVisibility();
        assertEquals(View.INVISIBLE, visibility);
    }

    //    @Test
    //    public void shouldBeVisible_MoneyUpdateTypeIsNumberPicker_ShouldBeVisible() {
    //        // Arrange
    //        initMoneyIndicatorPicker();
    //        doReturn(true).when(moneySettingsMock).isMoneyUpdateTypeNumberPicker();
    //
    //        // Act
    //        boolean shouldBeVisible = moneyIndicatorPicker.shouldBeVisible();
    //
    //        // Assert
    //        assertEquals(true, shouldBeVisible);
    //    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsNotNumberPicker_ShouldNotBeVisible() {
        // Arrange
        initMoneyIndicatorPicker();
        doReturn(false).when(moneySettingsMock).isMoneyUpdateTypeNumberPicker();

        // Act
        boolean shouldBeVisible = moneyIndicatorPicker.shouldBeVisible();

        // Assert
        assertEquals(false, shouldBeVisible);
    }

    private void initMoneyIndicatorPicker() {
        moneySettingsMock = mock(MoneySettings.class);
        moneyIndicatorPicker = new MoneyIndicatorPicker(getContext(), null, moneySettingsMock);
    }

    private Context getContext() {
        return App.getContext();
    }
}