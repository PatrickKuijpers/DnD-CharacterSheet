package nl.tcilegnar.dndcharactersheet.Base.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class BaseNumberPickerTest {
    private static final boolean SHOULD_BE_VISIBLE = true;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 10;
    private static final int STEP_SIZE = 1;
    private static final int INITIAL_VALUE_INDEX = 4;

    @Test
    public void BaseNumberPickerDefaultConstructor_SettingsSaved() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();

        // Assert
        assertNotNull(baseNumberPicker.settings);
    }

    @Test
    public void BaseNumberPickerDefaultConstructor_ShouldBeVisible() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();

        // Assert
        assertEquals(View.VISIBLE, baseNumberPicker.getVisibility());
    }

    @Test
    public void BaseNumberPickerDefaultConstructor_ShouldNotBeVisible() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getNotVisibleBaseNumberPickerStub();

        // Assert
        assertNotEquals(View.VISIBLE, baseNumberPicker.getVisibility());
    }

    @Test
    public void updateSettingsData_StartWithVisibleAndUpdateToNotVisible_ShouldNotBeVisible() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();

        baseNumberPickerStubShouldBeVisible = false;
        assertEquals(View.VISIBLE, baseNumberPicker.getVisibility());

        // Act
        baseNumberPicker.updateSettingsData();

        // Assert
        assertNotEquals(View.VISIBLE, baseNumberPicker.getVisibility());
    }

    @Test
    public void updateSettingsData_StartWithNotVisibleAndUpdateToVisible_ShouldBeVisible() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getNotVisibleBaseNumberPickerStub();

        baseNumberPickerStubShouldBeVisible = true;
        assertNotEquals(View.VISIBLE, baseNumberPicker.getVisibility());

        // Act
        baseNumberPicker.updateSettingsData();

        // Assert
        assertEquals(View.VISIBLE, baseNumberPicker.getVisibility());
    }

    @Test
    public void updateSettingsData_UpdatePickerValues_ShouldHaveNewValues() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();
        initStubValues(true, 1, 7, 2, 1); // Initialiseer met: 1 - >3< - 5 - 7

        assertEquals(View.VISIBLE, baseNumberPicker.getVisibility()); // Start visible
        assertInitialValues(baseNumberPicker, MIN_VALUE, MAX_VALUE, STEP_SIZE, INITIAL_VALUE_INDEX);

        // Act
        baseNumberPicker.updateSettingsData();

        // Assert
        assertEquals(View.VISIBLE, baseNumberPicker.getVisibility()); // Nog steeds visible
        assertInitialValues(baseNumberPicker, 1, 7, 2, 1);
    }

    @Test
    public void onSaveInstanceStateAndOnRestoreInstanceState_ShouldHaveSameValues() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();

        // Act
        Parcelable savedState = baseNumberPicker.onSaveInstanceState();

        final int NEW_VALUE = INITIAL_VALUE_INDEX + 1;
        baseNumberPicker.setValue(NEW_VALUE);

        baseNumberPicker.onRestoreInstanceState(savedState);

        // Assert
        assertNotEquals("De nieuwe waarde is nog steeds opgeslagen", NEW_VALUE, baseNumberPicker.getValue());
        assertEquals("De oude waarde is niet restored", INITIAL_VALUE_INDEX, baseNumberPicker.getValue());
    }

    @Test
    public void getCurrentSelectedNumber_Default_IsZero() {
        // Arrange
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();

        // Act
        int number = baseNumberPicker.getCurrentSelectedNumber();

        // Assert
        assertEquals(INITIAL_VALUE_INDEX, number);
    }

    @Test
    public void getCurrentSelectedNumber_WithNewValueSet_ReturnNumber() {
        // Arrange
        int expectedNumber = 7;
        BaseNumberPicker baseNumberPicker = getBaseNumberPickerStub();
        baseNumberPicker.setValue(expectedNumber);

        // Act
        int number = baseNumberPicker.getCurrentSelectedNumber();

        // Assert
        assertEquals(expectedNumber, number);
    }

    @Test
    public void setDividerColorTransparent() throws IllegalAccessException {
        // Arrange
        BaseNumberPicker numberPickerStub = getBaseNumberPickerStub();

        // Act
        numberPickerStub.setDividerColorTransparent();

        // Assert
        assertDividerColor(numberPickerStub, R.color.transparent);
    }

    @Test
    public void setDividerColor_Red() throws IllegalAccessException {
        // Arrange
        int expectedColorRes = R.color.red;
        BaseNumberPicker numberPickerStub = getBaseNumberPickerStub();

        // Act
        numberPickerStub.setDividerColor(expectedColorRes);

        // Assert
        assertDividerColor(numberPickerStub, expectedColorRes);
    }

    private boolean baseNumberPickerStubShouldBeVisible;
    private int minValue;
    private int maxValue;
    private int stepSize;
    private int initialValueIndex;

    private BaseNumberPicker getBaseNumberPickerStub() {
        initStubValues(SHOULD_BE_VISIBLE, MIN_VALUE, MAX_VALUE, STEP_SIZE, INITIAL_VALUE_INDEX);
        return new BaseNumberPickerStub();
    }

    private BaseNumberPicker getNotVisibleBaseNumberPickerStub() {
        initStubValues(false, MIN_VALUE, MAX_VALUE, STEP_SIZE, INITIAL_VALUE_INDEX);
        return new BaseNumberPickerStub();
    }

    private void initStubValues(boolean shouldBeVisible, int minValue, int maxValue, int stepSize, int
            initialValueIndex) {
        this.baseNumberPickerStubShouldBeVisible = shouldBeVisible;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepSize = stepSize;
        this.initialValueIndex = initialValueIndex;
    }

    private class BaseNumberPickerStub extends BaseNumberPicker {
        public <T extends Settings> BaseNumberPickerStub() {
            super(App.getContext(), null, getSettingsDummy());
        }

        @Override
        protected boolean shouldBeVisible() {
            return baseNumberPickerStubShouldBeVisible;
        }

        @Override
        protected int minValue() {
            return minValue;
        }

        @Override
        protected int maxValue() {
            return maxValue;
        }

        @Override
        protected int initialValue() {
            return initialValueIndex;
        }

        @Override
        protected int getPickerStepSize() {
            return stepSize;
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

    private void assertInitialValues(BaseNumberPicker baseNumberPicker, int expectedMinValue, int expectedMaxValue,
                                     int expectedStepSize, int expectedInitialNumberIndex) {
        int totalNumberOfValues = baseNumberPicker.getDisplayedValues().length;
        int minValue = baseNumberPicker.getMinValue();
        int maxValue = getMaxValue(baseNumberPicker, totalNumberOfValues);
        int stepSize = getStepSize(minValue, maxValue, totalNumberOfValues);
        int initialNumber = baseNumberPicker.getCurrentSelectedNumber();

        assertEquals(expectedMinValue, minValue);
        assertEquals(expectedMaxValue, maxValue);
        assertEquals(expectedStepSize, stepSize);
        // TODO: dit moet beter kunnen: initialNumber ipv -index
        int expectedInitialNumber = getValue(baseNumberPicker, expectedInitialNumberIndex);
        assertEquals(expectedInitialNumber, initialNumber);
    }

    private int getMaxValue(BaseNumberPicker baseNumberPicker, int totalNumberOfValues) {
        return getValue(baseNumberPicker, totalNumberOfValues - 1);
    }

    private int getValue(BaseNumberPicker baseNumberPicker, int index) {
        return Integer.valueOf(baseNumberPicker.getDisplayedValues()[index]);
    }

    private int getStepSize(int min, int max, int totalNumberOfValues) {
        return (max - min) / (totalNumberOfValues - 1);
    }

    // =====

    @Test
    public void initView() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void onSaveInstanceState() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    public void onRestoreInstanceState() {
        // Arrange

        // Act

        // Assert
    }

    protected void assertDividerColor(BaseNumberPicker numberPickerStub, @ColorRes int colorRes) throws
            IllegalAccessException {
        int expectedColor = ContextCompat.getColor(App.getContext(), colorRes);

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                ColorDrawable colorDrawable = (ColorDrawable) pf.get(numberPickerStub);
                int color = colorDrawable.getColor();
                assertEquals(expectedColor, color);
                break;
            }
        }
    }

    @Test
    public void setDividerColor() {
        // Arrange

        // Act

        // Assert
    }
}