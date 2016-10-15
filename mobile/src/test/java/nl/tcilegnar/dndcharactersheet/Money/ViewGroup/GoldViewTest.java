package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GoldViewTest {
    private GoldView goldView;
    private Storage storageMock;

    @Test
    public void goldViewPublicConstructor() {
        // Arrange

        // Act
        GoldView goldView = new GoldView(getContext(), null);

        // Assert
        assertNotNull(goldView.storage);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initGoldView();

        // Act
        int resourceId = goldView.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_gold_indicator, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initGoldView();
        int expectedValue = mockLoadGold(11);

        // Act
        int moneyValue = goldView.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initGoldView();
        int expectedSavedValue = 11;

        // Act
        goldView.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveGold(expectedSavedValue);
    }

    private void initGoldView() {
        storageMock = mock(Storage.class);
        goldView = new GoldView(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadGold(int value) {
        doReturn(value).when(storageMock).loadGold();
        return value;
    }
}