package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GoldEditorTest {
    private GoldEditor goldEditor;

    @Test
    public void goldEditorPublicConstructor() {
        // Arrange

        // Act
        GoldEditor goldEditor = new GoldEditor(getContext(), null);

        // Assert
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

    private void initGoldEditor() {
        goldEditor = new GoldEditor(getContext(), null);
    }

    public Context getContext() {
        return App.getContext();
    }
}