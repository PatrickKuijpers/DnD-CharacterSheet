package nl.tcilegnar.dndcharactersheet.Base.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class BaseNumberInputTest {
    @Test
    public void BaseNumberInputDefaultConstructor_SettingsSaved() {
        // Arrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        // Assert
        assertNotNull(baseNumberInput.settings);
    }

    @Test
    public void BaseNumberInputDefaultConstructor_ShouldBeVisible() {
        // Arrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        // Assert
        assertEquals(View.VISIBLE, baseNumberInput.getVisibility());
    }

    @Test
    public void BaseNumberInputDefaultConstructor_ShouldNotBeVisible() {
        // Arrange
        BaseNumberInput baseNumberInput = getNotVisibleBaseNumberInputStub();

        // Assert
        assertNotEquals(View.VISIBLE, baseNumberInput.getVisibility());
    }

    @Test
    public void updateSettingsData_StartWithVisibleAndUpdateToNotVisible_ShouldNotBeVisible() {
        // Arrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        baseNumberInputStubShouldBeVisible = false;
        assertEquals(View.VISIBLE, baseNumberInput.getVisibility());

        // Act
        baseNumberInput.updateSettingsData();

        // Assert
        assertNotEquals(View.VISIBLE, baseNumberInput.getVisibility());
    }

    @Test
    public void updateSettingsData_StartWithNotVisibleAndUpdateToVisible_ShouldBeVisible() {
        // Arrange
        BaseNumberInput baseNumberInput = getNotVisibleBaseNumberInputStub();

        baseNumberInputStubShouldBeVisible = true;
        assertNotEquals(View.VISIBLE, baseNumberInput.getVisibility());

        // Act
        baseNumberInput.updateSettingsData();

        // Assert
        assertEquals(View.VISIBLE, baseNumberInput.getVisibility());
    }

    @Test
    public void getInputText_Default_IsEmpty() {
        // Arrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        // Act
        String text = baseNumberInput.getInputText();

        // Assert
        assertTrue(text.length() == 0);
    }

    @Test
    public void getInputText_WithInput_HasInput() {
        // Arrange
        String expectedText = "123";
        BaseNumberInput baseNumberInput = getBaseNumberInputStub(expectedText);

        // Act
        String text = baseNumberInput.getInputText();

        // Assert
        assertTrue(text.length() > 0);
        assertEquals(expectedText, text);
    }

    @Test
    public void getInputNumber_Default_IsZero() {
        // Arrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        // Act
        int number = baseNumberInput.getInputNumber();

        // Assert
        assertEquals(0, number);
    }

    @Test
    public void getInputNumber_WithNumberSet_ReturnNumber() {
        // Arrange
        String inputNumber = "123";
        BaseNumberInput baseNumberInput = getBaseNumberInputStub(inputNumber);

        // Act
        int number = baseNumberInput.getInputNumber();

        // Assert
        int expectedNumber = Integer.valueOf(inputNumber);
        assertEquals(expectedNumber, number);
    }

    @Test(expected = NumberFormatException.class)
    public void getInputNumber_WithLettersSetAsText_ExpectNumberFormatException() {
        // Arrange
        String text = "abc";
        BaseNumberInput baseNumberInput = getBaseNumberInputStub(text);

        // Act
        baseNumberInput.getInputNumber();

        // Assert
    }

    @Test
    public void hasInput_Default_ReturnFalse() {
        // ArrangeArrange
        BaseNumberInput baseNumberInput = getBaseNumberInputStub();

        // Act
        boolean hasInput = baseNumberInput.hasInput();

        // Assert
        assertEquals(false, hasInput);
    }

    @Test
    public void hasInput_WithInput_ReturnTrue() {
        // ArrangeArrange
        String text = "123";
        BaseNumberInput baseNumberInput = getBaseNumberInputStub(text);

        // Act
        boolean hasInput = baseNumberInput.hasInput();

        // Assert
        assertEquals(true, hasInput);
    }

    private static boolean baseNumberInputStubShouldBeVisible = true;

    private BaseNumberInput getBaseNumberInputStub(String initialText) {
        baseNumberInputStubShouldBeVisible = true;
        BaseNumberInputStub baseNumberInputStub = new BaseNumberInputStub();
        baseNumberInputStub.setText(initialText);
        return baseNumberInputStub;
    }

    private BaseNumberInput getBaseNumberInputStub() {
        baseNumberInputStubShouldBeVisible = true;
        return new BaseNumberInputStub();
    }

    private BaseNumberInput getNotVisibleBaseNumberInputStub() {
        baseNumberInputStubShouldBeVisible = false;
        return new BaseNumberInputStub();
    }

    private class BaseNumberInputStub extends BaseNumberInput {
        public <T extends Settings> BaseNumberInputStub() {
            super(App.getContext(), null, getSettingsDummy());
        }

        @Override
        protected boolean shouldBeVisible() {
            return baseNumberInputStubShouldBeVisible;
        }
    }

    private Settings getSettingsDummy() {
        return new Settings() {
            @Override
            protected String fileName() {
                return "TEST_FILE_NAME";
            }
        };
    }
}