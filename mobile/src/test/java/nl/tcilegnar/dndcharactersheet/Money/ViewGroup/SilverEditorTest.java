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
public class SilverEditorTest {
    private SilverEditor silverEditor;
    private Storage storageMock;

    @Test
    public void silverEditorPublicConstructor() {
        // Arrange

        // Act
        SilverEditor silverEditor = new SilverEditor(getContext(), null);

        // Assert
        assertNotNull(silverEditor.storage);
        Asserts.hasCorrectEditors(silverEditor);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initSilverEditor();

        // Act
        int resourceId = silverEditor.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_silver_editor, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initSilverEditor();
        int expectedValue = mockLoadSilver(11);

        // Act
        int moneyValue = silverEditor.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initSilverEditor();
        int expectedSavedValue = 11;

        // Act
        silverEditor.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveSilver(expectedSavedValue);
    }

    private void initSilverEditor() {
        storageMock = mock(Storage.class);
        silverEditor = new SilverEditor(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadSilver(int value) {
        doReturn(value).when(storageMock).loadSilver();
        return value;
    }
}