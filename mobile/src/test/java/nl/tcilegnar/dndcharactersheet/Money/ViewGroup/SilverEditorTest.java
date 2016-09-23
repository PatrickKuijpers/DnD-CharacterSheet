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
public class SilverEditorTest {
    private SilverEditor silverEditor;

    @Test
    public void silverEditorPublicConstructor() {
        // Arrange

        // Act
        SilverEditor silverEditor = new SilverEditor(getContext(), null);

        // Assert
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

    private void initSilverEditor() {
        silverEditor = new SilverEditor(getContext(), null);
    }

    private Context getContext() {
        return App.getContext();
    }
}