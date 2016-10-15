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
public class BronzeEditorTest {
    private BronzeEditor bronzeEditor;

    @Test
    public void bronzeEditorPublicConstructor() {
        // Arrange

        // Act
        BronzeEditor bronzeEditor = new BronzeEditor(App.getContext(), null);

        // Assert
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

    private void initBronzeEditor() {
        bronzeEditor = new BronzeEditor(getContext(), null);
    }

    public Context getContext() {
        return App.getContext();
    }
}