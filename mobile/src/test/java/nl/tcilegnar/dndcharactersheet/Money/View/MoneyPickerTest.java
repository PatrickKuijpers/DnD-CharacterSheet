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
public class MoneyPickerTest {
    private MoneyPicker moneyPicker;
    private MoneySettings moneySettingsMock;

    @Test
    public void moneyPickerPublicConstructor() {
        // Arrange

        // Act
        MoneyPicker moneyPicker = new MoneyPicker(getContext(), null);

        // Assert
        assertNotNull(moneyPicker.settings);
        assertTrue(moneyPicker.settings instanceof MoneySettings);
    }

    @Test
    public void hideView_MoneyPickerShouldBeInvisibleInsteadOfGone() {
        // Arrange
        initMoneyPicker();

        // Act
        moneyPicker.hideView();

        // Assert
        int visibility = moneyPicker.getVisibility();
        assertEquals(View.INVISIBLE, visibility);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsNumberPicker_ShouldBeVisible() {
        // Arrange
        initMoneyPicker();
        doReturn(true).when(moneySettingsMock).isMoneyUpdateTypeNumberPicker();

        // Act
        boolean shouldBeVisible = moneyPicker.shouldBeVisible();

        // Assert
        assertEquals(true, shouldBeVisible);
    }

    @Test
    public void shouldBeVisible_MoneyUpdateTypeIsNotNumberPicker_ShouldNotBeVisible() {
        // Arrange
        initMoneyPicker();
        doReturn(false).when(moneySettingsMock).isMoneyUpdateTypeNumberPicker();

        // Act
        boolean shouldBeVisible = moneyPicker.shouldBeVisible();

        // Assert
        assertEquals(false, shouldBeVisible);
    }

    private void initMoneyPicker() {
        moneySettingsMock = mock(MoneySettings.class);
        moneyPicker = new MoneyPicker(getContext(), null, moneySettingsMock);
    }

    private Context getContext() {
        return App.getContext();
    }
}