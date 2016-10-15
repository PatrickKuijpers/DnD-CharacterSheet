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
public class PlatinumEditorTest {
    private PlatinumEditor platinumEditor;

    @Test
    public void platinumEditorPublicConstructor() {
        // Arrange

        // Act
        PlatinumEditor platinumEditor = new PlatinumEditor(getContext(), null);

        // Assert
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

    private void initPlatinumEditor() {
        platinumEditor = new PlatinumEditor(getContext(), null);
    }

    private Context getContext() {
        return App.getContext();
    }
}