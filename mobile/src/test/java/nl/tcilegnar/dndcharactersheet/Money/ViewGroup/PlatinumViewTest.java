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
public class PlatinumViewTest {
    //    private MoneyActivity moneyActivity;
    //    private LayoutInflater layoutInflater;
    private PlatinumView platinumView;
    private Storage storageMock;

    @Before
    public void setup() {
        //        layoutInflater = LayoutInflater.from(moneyActivity);
        //        //        ViewGroup root = (ViewGroup) moneyActivity.findViewById(R.id.activity_content_parent);
        //        platinumView = (PlatinumView) layoutInflater.inflate(R.layout.money_platinum_view, null);

        //        moneyActivity = new MoneyActivity();
        //        platinumView = (PlatinumView) layoutInflater.inflate(R.layout.money_platinum_view, null);
        //        loadingSpinner = loadingTextView.findViewById(R.id.loading_text_spinner);
        //        loadingTextTextView = (TextView) loadingTextView.findViewById(R.id.loading_text_text_view);

        initPlatinumView();
    }

    @Test
    public void platinumViewPublicConstructor() {
        // Arrange

        // Act
        PlatinumView platinumView = new PlatinumView(getContext(), null);

        // Assert
        assertNotNull(platinumView.storage);
        Asserts.hasCorrectEditors(platinumView);
    }

    @Test
    public void getLayoutResource() {
        // Arrange

        // Act
        int resourceId = platinumView.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_platinum_view, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        int expectedValue = mockLoadPlatinum(11);

        // Act
        int moneyValue = platinumView.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        int expectedSavedValue = 11;

        // Act
        platinumView.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).savePlatinum(expectedSavedValue);
    }

    private void initPlatinumView() {
        storageMock = mock(Storage.class);
        platinumView = new PlatinumView(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext(); //Robolectric.buildActivity(MoneyActivity.class).create().get();
    }

    private int mockLoadPlatinum(int value) {
        doReturn(value).when(storageMock).loadPlatinum();
        return value;
    }
}