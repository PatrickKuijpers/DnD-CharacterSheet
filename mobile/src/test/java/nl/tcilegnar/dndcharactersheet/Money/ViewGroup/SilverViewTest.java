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
public class SilverViewTest {
    private SilverView silverView;
    private Storage storageMock;

    @Test
    public void silverViewPublicConstructor() {
        // Arrange

        // Act
        SilverView silverView = new SilverView(getContext(), null);

        // Assert
        assertNotNull(silverView.storage);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initSilverView();

        // Act
        int resourceId = silverView.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_silver_view, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initSilverView();
        int expectedValue = mockLoadSilver(11);

        // Act
        int moneyValue = silverView.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initSilverView();
        int expectedSavedValue = 11;

        // Act
        silverView.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveSilver(expectedSavedValue);
    }

    private void initSilverView() {
        storageMock = mock(Storage.class);
        silverView = new SilverView(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadSilver(int value) {
        doReturn(value).when(storageMock).loadSilver();
        return value;
    }
}