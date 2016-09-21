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
public class BronzeEditorTest {
    private BronzeEditor bronzeEditor;
    private Storage storageMock;

    @Test
    public void bronzeEditorPublicConstructor() {
        // Arrange

        // Act
        BronzeEditor bronzeEditor = new BronzeEditor(getContext(), null);

        // Assert
        assertNotNull(bronzeEditor.storage);
        Asserts.hasCorrectEditors(bronzeEditor);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initBronzeEditor();

        // Act
        int resourceId = bronzeEditor.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_bronze_editor, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initBronzeEditor();
        int expectedValue = mockLoadBronze(11);

        // Act
        int moneyValue = bronzeEditor.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initBronzeEditor();
        int expectedSavedValue = 11;

        // Act
        bronzeEditor.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveBronze(expectedSavedValue);
    }

    private void initBronzeEditor() {
        storageMock = mock(Storage.class);
        bronzeEditor = new BronzeEditor(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadBronze(int value) {
        doReturn(value).when(storageMock).loadBronze();
        return value;
    }
}