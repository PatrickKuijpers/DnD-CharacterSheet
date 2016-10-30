package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlatinumViewTest {
    private PlatinumView platinumView;
    private Storage storageMock;

    @Test
    public void platinumViewPublicConstructor() {
        // Arrange

        // Act
        PlatinumView platinumView = new PlatinumView(getContext(), null);

        // Assert
        assertNotNull(platinumView.storage);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initPlatinumView();

        // Act
        int resourceId = platinumView.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_platinum_indicator, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initPlatinumView();
        int expectedValue = mockLoadPlatinum(11);

        // Act
        int moneyValue = platinumView.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initPlatinumView();
        int expectedSavedValue = 11;

        // Act
        platinumView.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).savePlatinum(expectedSavedValue);
    }

    @Test
    public void getMoneyValue() {
        // Arrange
        initPlatinumView();
        MoneyValues moneyValues = new MoneyValues(1, 2, 3, 4);

        // Act
        int moneyValue = platinumView.getMoneyValue(moneyValues);

        // Assert
        assertEquals(1, moneyValue);
    }

    private void initPlatinumView() {
        storageMock = mock(Storage.class);
        platinumView = new PlatinumView(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadPlatinum(int value) {
        doReturn(value).when(storageMock).loadPlatinum();
        return value;
    }
}