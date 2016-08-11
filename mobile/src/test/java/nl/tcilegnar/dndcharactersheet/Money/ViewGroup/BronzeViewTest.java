package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import org.junit.Before;
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
public class BronzeViewTest {
    private BronzeView bronzeView;
    private Storage storageMock;

    @Before
    public void setup() {
        initBronzeView();
    }

    @Test
    public void bronzeViewPublicConstructor() {
        // Arrange

        // Act
        BronzeView bronzeView = new BronzeView(getContext(), null);

        // Assert
        assertNotNull(bronzeView.storage);
        Asserts.hasCorrectEditors(bronzeView);
    }

    @Test
    public void getLayoutResource() {
        // Arrange

        // Act
        int resourceId = bronzeView.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_bronze_view, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        int expectedValue = mockLoadBronze(11);

        // Act
        int moneyValue = bronzeView.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        int expectedSavedValue = 11;

        // Act
        bronzeView.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveBronze(expectedSavedValue);
    }

    private void initBronzeView() {
        storageMock = mock(Storage.class);
        bronzeView = new BronzeView(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadBronze(int value) {
        doReturn(value).when(storageMock).loadBronze();
        return value;
    }
}