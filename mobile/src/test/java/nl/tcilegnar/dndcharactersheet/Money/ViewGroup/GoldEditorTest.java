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
public class GoldEditorTest {
    private GoldEditor goldEditor;
    private Storage storageMock;

    @Test
    public void goldEditorPublicConstructor() {
        // Arrange

        // Act
        GoldEditor goldEditor = new GoldEditor(getContext(), null);

        // Assert
        assertNotNull(goldEditor.storage);
        Asserts.hasCorrectEditors(goldEditor);
    }

    @Test
    public void getLayoutResource() {
        // Arrange
        initGoldEditor();

        // Act
        int resourceId = goldEditor.getLayoutResource();

        // Assert
        assertEquals(R.layout.money_gold_editor, resourceId);
    }

    @Test
    public void loadMoneyValue() {
        // Arrange
        initGoldEditor();
        int expectedValue = mockLoadGold(11);

        // Act
        int moneyValue = goldEditor.loadMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void saveMoneyValue() {
        // Arrange
        initGoldEditor();
        int expectedSavedValue = 11;

        // Act
        goldEditor.saveMoneyValue(expectedSavedValue);

        // Assert
        verify(storageMock).saveGold(expectedSavedValue);
    }

    private void initGoldEditor() {
        storageMock = mock(Storage.class);
        goldEditor = new GoldEditor(getContext(), null, storageMock);
    }

    private Context getContext() {
        return App.getContext();
    }

    private int mockLoadGold(int value) {
        doReturn(value).when(storageMock).loadGold();
        return value;
    }
}