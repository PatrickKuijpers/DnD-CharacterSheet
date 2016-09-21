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
public class PlatinumEditorTest {
    private PlatinumEditor platinumEditor;
    private Storage storageMock;

    @Test
    public void platinumEditorPublicConstructor() {
        // Arrange

        // Act
        PlatinumEditor platinumEditor = new PlatinumEditor(getContext(), null);

        // Assert
        assertNotNull(platinumEditor.storage);
        Asserts.hasCorrectEditors(platinumEditor);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initPlatinumEditor();

        // Act
        int resourceId = platinumEditor.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_platinum_editor, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initPlatinumEditor();
        int expectedValue = mockLoadPlatinum(11);

        // Act
        int moneyValue = platinumEditor.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initPlatinumEditor();
        int expectedSavedValue = 11;

        // Act
        platinumEditor.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).savePlatinum(expectedSavedValue);
    }

    private void initPlatinumEditor() {
        storageMock = mock(Storage.class);
        platinumEditor = new PlatinumEditor(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadPlatinum(int value) {
        doReturn(value).when(storageMock).loadPlatinum();
        return value;
    }
}