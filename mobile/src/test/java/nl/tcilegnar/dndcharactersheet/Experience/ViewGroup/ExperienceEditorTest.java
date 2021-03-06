package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettings;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceInput;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceSlider;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.R;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceEditorTest {
    private static final boolean DEFAULT_IS_EXPERIENCE_UPDATE_TYPE_INPUT = true;
    private static final boolean DEFAULT_IS_NOT_EXPERIENCE_UPDATE_TYPE_NUMBER_PICKER = false;
    private static final ExperienceSettings settingsMock = mock(ExperienceSettings.class);
    private static Button plusButtonMock;
    private static Button minButtonMock;
    private ExperienceEditor experienceEditor;
    private ExperienceUpdateListener experienceUpdateListenerMock;
    private ExperienceInput expInput;
    private ExperienceSlider expPicker;

    @BeforeClass
    public static void beforeClass() {
        plusButtonMock = getButtonMock(R.id.experience_plus_button);
        minButtonMock = getButtonMock(R.id.experience_min_button);
    }

    @Test
    public void newExperienceEditor_DefaultSettings_InitViewsAndValues() {
        // Arrange

        // Act
        experienceEditor = new ExperienceEditor(App.getContext(), null);

        // Assert
        assertOnClickListenersInitialized();
    }

    @Test
    public void newExperienceEditor_DefaultSettingsMock_InitViewsAndValues() {
        // Arrange

        // Act
        initNewExperienceEditor(DEFAULT_IS_EXPERIENCE_UPDATE_TYPE_INPUT,
                DEFAULT_IS_NOT_EXPERIENCE_UPDATE_TYPE_NUMBER_PICKER);

        // Assert
        assertOnClickListenersInitialized();
    }

    @Test
    public void onClick_PlusButtonAndExperienceUpdateAnimationFinishedIsFalse_OnUpdateExperienceNotCalled() {
        // Arrange
        initNewExperienceEditor_WithInput();
        doReturn(false).when(experienceUpdateListenerMock).hasExperienceUpdateAnimationFinished();

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        verify(experienceUpdateListenerMock, never()).onUpdateExperience(anyInt());
    }

    @Test
    public void onClick_MinButtonAndExperienceUpdateAnimationFinishedIsFalse_OnUpdateExperienceNotCalled() {
        // Arrange
        initNewExperienceEditor_WithInput();
        doReturn(false).when(experienceUpdateListenerMock).hasExperienceUpdateAnimationFinished();

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        verify(experienceUpdateListenerMock, never()).onUpdateExperience(anyInt());
    }

    @Test
    public void onClick_PlusButtonUsingEmptyInput_OnUpdateExperienceWithValue0() {
        // Arrange
        initNewExperienceEditor_WithInput();

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    @Test
    public void onClick_PlusButtonUsingInputByDefaultAndValue5_OnUpdateExperienceUsingDefaultInputValue() {
        // Arrange
        int inputValue = 5;
        initNewExperienceEditor_WithInputValue(inputValue);

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        int expectedValue = inputValue;
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_PlusButtonUsingNumberSliderAndDefaultValue0_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        initNewExperienceEditor_WithNumberSlider();

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        int expectedValue = expPicker.getCurrentSelectedNumber();
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_PlusButtonUsingNumberSliderAndValue300_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        int pickerValueIndex = 3;
        initNewExperienceEditor_WithNumberSliderAndSelectedIndex(pickerValueIndex);

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        int expectedValue = pickerValueIndex * 100; // 300 is de default value op index #3 (default step size = 100)
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_PlusButtonUsingNoInputAndNoNumberSlider_OnUpdateExperienceUsingDefaultValue0() {
        // Arrange
        initNewExperienceEditor_WithNoInputAndNoNumberSlider();

        // Act
        experienceEditor.onClick(plusButtonMock);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    @Test
    public void onClick_MinButtonUsingEmptyInput_OnUpdateExperienceWithValue0() {
        // Arrange
        initNewExperienceEditor_WithInput();

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    @Test
    public void onClick_MinButtonUsingInputByDefaultAndValue5_OnUpdateExperienceUsingDefaultInputValue() {
        // Arrange
        int inputValue = 5;
        initNewExperienceEditor_WithInputValue(inputValue);

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        int expectedValue = -inputValue;
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_MinButtonUsingNumberSliderAndDefaultValue0_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        initNewExperienceEditor_WithNumberSlider();

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        int expectedValue = -expPicker.getCurrentSelectedNumber();
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_MinButtonUsingNumberSliderAndValue300_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        int pickerValueIndex = 3;
        initNewExperienceEditor_WithNumberSliderAndSelectedIndex(pickerValueIndex);

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        int expectedValue = -(pickerValueIndex * 100); // 300 is de default value op index #3 (default step size = 100)
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onClick_MinButtonUsingNoInputAndNoNumberSlider_OnUpdateExperienceUsingDefaultValue0() {
        // Arrange
        initNewExperienceEditor_WithNoInputAndNoNumberSlider();

        // Act
        experienceEditor.onClick(minButtonMock);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    @Test
    public void onEditorAction_EnterUsingEmptyInput_OnUpdateExperienceWithValue0() {
        // Arrange
        initNewExperienceEditor_WithInput();

        // Act
        experienceEditor.onEditorAction(expInput, EditorInfo.IME_ACTION_DONE, null);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    @Test
    public void onEditorAction_EnterUsingInputByDefaultAndValue5_OnUpdateExperienceUsingDefaultInputValue() {
        // Arrange
        int inputValue = 5;
        initNewExperienceEditor_WithInputValue(inputValue);

        // Act
        experienceEditor.onEditorAction(expInput, EditorInfo.IME_ACTION_DONE, null);

        // Assert
        int expectedValue = inputValue;
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onEditorAction_EnterUsingNumberSliderAndDefaultValue0_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        initNewExperienceEditor_WithNumberSlider();

        // Act
        experienceEditor.onEditorAction(expInput, EditorInfo.IME_ACTION_DONE, null);

        // Assert
        int expectedValue = expPicker.getCurrentSelectedNumber();
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onEditorAction_EnterUsingNumberSliderAndValue300_OnUpdateExperienceUsingDefaultExpPickerValue() {
        // Arrange
        int pickerValueIndex = 3;
        initNewExperienceEditor_WithNumberSliderAndSelectedIndex(pickerValueIndex);

        // Act
        experienceEditor.onEditorAction(expInput, EditorInfo.IME_ACTION_DONE, null);

        // Assert
        int expectedValue = pickerValueIndex * 100; // 300 is de default value op index #3 (default step size = 100)
        verify(experienceUpdateListenerMock).onUpdateExperience(expectedValue);
    }

    @Test
    public void onEditorAction_EnterUsingNoInputAndNoNumberSlider_OnUpdateExperienceUsingDefaultValue0() {
        // Arrange
        initNewExperienceEditor_WithNoInputAndNoNumberSlider();

        // Act
        experienceEditor.onEditorAction(expInput, EditorInfo.IME_ACTION_DONE, null);

        // Assert
        verify(experienceUpdateListenerMock).onUpdateExperience(0);
    }

    private void initNewExperienceEditor_WithInput() {
        initNewExperienceEditor(true, false);
    }

    private void initNewExperienceEditor_WithInputValue(int inputValue) {
        initNewExperienceEditor_WithInput();
        expInput.setText(String.valueOf(inputValue));
    }

    private void initNewExperienceEditor_WithNumberSlider() {
        initNewExperienceEditor(false, true);
    }

    private void initNewExperienceEditor_WithNumberSliderAndSelectedIndex(int pickerValueIndex) {
        initNewExperienceEditor_WithNumberSlider();
        expPicker.setValue(pickerValueIndex);
    }

    private void initNewExperienceEditor_WithNoInputAndNoNumberSlider() {
        initNewExperienceEditor(false, false);
    }

    private void initNewExperienceEditor(boolean isExperienceUpdateTypeInput, boolean
            isExperienceUpdateTypeNumberSlider) {
        doReturn(isExperienceUpdateTypeInput).when(settingsMock).isExperienceUpdateTypeInput();
        doReturn(isExperienceUpdateTypeNumberSlider).when(settingsMock).isExperienceUpdateTypeNumberSlider();

        experienceEditor = new ExperienceEditor(App.getContext(), null, settingsMock);
        expInput = (ExperienceInput) experienceEditor.findViewById(R.id.experience_input);
        expPicker = (ExperienceSlider) experienceEditor.findViewById(R.id.experience_picker);
        setListeners(experienceEditor);
    }

    private void setListeners(ExperienceEditor experienceEditor) {
        experienceUpdateListenerMock = mock(ExperienceUpdateListener.class);
        doReturn(true).when(experienceUpdateListenerMock).hasExperienceUpdateAnimationFinished();
        experienceEditor.setExperienceUpdateListener(experienceUpdateListenerMock);
    }

    private void assertOnClickListenersInitialized() {
        View plusButton = experienceEditor.findViewById(R.id.experience_plus_button);
        View minButton = experienceEditor.findViewById(R.id.experience_min_button);
        assertTrue(plusButton.hasOnClickListeners());
        assertTrue(minButton.hasOnClickListeners());
    }

    private static Button getButtonMock(@IdRes int resId) {
        Button buttonMock = mock(Button.class);
        doReturn(resId).when(buttonMock).getId();
        return buttonMock;
    }
}