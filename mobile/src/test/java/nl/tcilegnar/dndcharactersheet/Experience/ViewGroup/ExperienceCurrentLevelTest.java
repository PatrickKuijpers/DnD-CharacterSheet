package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.Animations.ExperienceProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil;
import nl.tcilegnar.dndcharactersheet.R;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceCurrentLevelTest {
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_EXP_MAX = new LevelTableUtil().getMaxExperience(DEFAULT_LEVEL);
    private static final int DEFAULT_CURRENT_EXP = 0;
    private ExperienceCurrentLevel experienceCurrentLevel;
    private Experience experienceMock;
    private ExperienceProgressBarAnimation experienceProgressBarAnimationMock;

    @Before
    public void setUp() {
        experienceCurrentLevel = getNewExperienceCurrentLevel_WithDefaultValues();
    }

    @Test
    public void newExperienceCurrentLevel_DefaultConstructor_InitViewsAndValuesWithDefaultExperienceValues() {
        // Arrange

        // Act
        experienceCurrentLevel = new ExperienceCurrentLevel(App.getContext(), null);

        // Assert
        assertIsDefaultNewExperience(experienceCurrentLevel.getExperience());
        assertProgressbarValues(DEFAULT_EXP_MAX, DEFAULT_CURRENT_EXP);
        assertExperienceLabelValues(DEFAULT_CURRENT_EXP);
    }

    @Test
    public void newExperienceCurrentLevel_DefaultExperienceMock_InitViewsAndValuesWithDefaultExperienceValues() {
        // Arrange

        // Act

        // Assert
        assertProgressbarValues(DEFAULT_EXP_MAX, DEFAULT_CURRENT_EXP);
        assertExperienceLabelValues(DEFAULT_CURRENT_EXP);
    }

    @Test
    public void newExperienceCurrentLevel_NotDefaultExperience_InitViewsAndValuesWithExperienceValues() {
        // Arrange
        int expectedExpMax = 5000;
        int expectedCurrentExp = 1234;

        // Act
        experienceCurrentLevel = getNewExperienceCurrentLevel(expectedExpMax, expectedCurrentExp);

        // Assert
        assertProgressbarValues(expectedExpMax, expectedCurrentExp);
        assertExperienceLabelValues(expectedCurrentExp);
    }

    @Test
    public void getExperience_ExperienceMock_ExperienceMockIsSet() {
        // Arrange

        // Act
        Experience experience = experienceCurrentLevel.getExperience();

        // Assert
        assertEquals(experienceMock, experience);
    }

    @Test
    public void testSaveExp_ExperienceSaveMethodCalled() {
        // Arrange

        // Act
        experienceCurrentLevel.save();

        // Assert
        verify(experienceMock, times(1)).save();
    }

    @Test
    public void onUpdateExperience_AddZeroExp_AllViewValuesAreDefault() throws ExpTooLowException {
        // Arrange
        int initialExpValue = DEFAULT_CURRENT_EXP;
        int expUpdateValue = 0;
        prepareOnUpdateExperienceTest(initialExpValue, expUpdateValue);

        // Act
        experienceCurrentLevel.onUpdateExperience(expUpdateValue);

        // Assert
        verify(experienceMock).updateExperience(expUpdateValue);
    }

    @Test
    public void onUpdateExperience_AddPositiveExp_AllViewValuesAreUpdated() throws ExpTooLowException {
        // Arrange
        int initialExpValue = 100;
        int expUpdateValue = 10;
        prepareOnUpdateExperienceTest(initialExpValue, expUpdateValue);

        // Act
        experienceCurrentLevel.onUpdateExperience(expUpdateValue);

        // Assert
        verify(experienceMock).updateExperience(expUpdateValue);
    }

    @Test
    public void onUpdateExperience_AddNegativeExp_AllViewValuesAreUpdated() throws ExpTooLowException {
        // Arrange
        int initialExpValue = 100;
        int expUpdateValue = -10;
        prepareOnUpdateExperienceTest(initialExpValue, expUpdateValue);

        // Act
        experienceCurrentLevel.onUpdateExperience(expUpdateValue);

        // Assert
        verify(experienceMock).updateExperience(expUpdateValue);
    }

    @Test
    public void onUpdateExperience_NegativeExpWithResultBelowZero_ViewValuesAreNotUpdated() throws ExpTooLowException {
        // Arrange
        int initialExpValue = 10;
        int expUpdateValue = -100;
        prepareOnUpdateExperienceTest(initialExpValue, expUpdateValue);

        // Act
        experienceCurrentLevel.onUpdateExperience(expUpdateValue);

        // Assert
        verify(experienceMock).updateExperience(expUpdateValue);
    }

    @Test
    public void onExperienceUpdated_ZeroExp_TextviewContainsZeroExpValue() {
        // Arrange
        int currentExp = 0;
        doReturn(currentExp).when(experienceMock).getCurrentExp();

        // Act
        experienceCurrentLevel.onExperienceUpdated();

        // Assert
        assertExperienceViewsUpdated(currentExp);
    }

    @Test
    public void onExperienceUpdated_PositiveExp_AllViewValuesAreUpdated() {
        // Arrange
        int currentExp = 123;
        doReturn(currentExp).when(experienceMock).getCurrentExp();

        // Act
        experienceCurrentLevel.onExperienceUpdated();

        // Assert
        assertExperienceViewsUpdated(currentExp);
    }

    @Test
    public void hasExperienceUpdateAnimationFinished_True() {
        // Arrange
        boolean expectedValue = true;
        doReturn(expectedValue).when(experienceProgressBarAnimationMock).isFinished();

        // Act
        boolean hasFinished = experienceCurrentLevel.hasExperienceUpdateAnimationFinished();

        // Assert
        assertEquals(expectedValue, hasFinished);
    }

    @Test
    public void hasExperienceUpdateAnimationFinished_False() {
        // Arrange
        boolean expectedValue = false;
        doReturn(expectedValue).when(experienceProgressBarAnimationMock).isFinished();

        // Act
        boolean hasFinished = experienceCurrentLevel.hasExperienceUpdateAnimationFinished();

        // Assert
        assertEquals(expectedValue, hasFinished);
    }

    private ExperienceCurrentLevel getNewExperienceCurrentLevel_WithDefaultValues() {
        return getNewExperienceCurrentLevel(DEFAULT_EXP_MAX, DEFAULT_CURRENT_EXP);
    }

    private ExperienceCurrentLevel getNewExperienceCurrentLevel(int expMax, int currentExp) {
        experienceMock = mock(Experience.class);
        doReturn(expMax).when(experienceMock).getMax();
        doReturn(currentExp).when(experienceMock).getCurrentExp();
        experienceProgressBarAnimationMock = mock(ExperienceProgressBarAnimation.class);
        return new ExperienceCurrentLevel(App.getContext(), null, experienceMock, experienceProgressBarAnimationMock);
    }

    private void prepareOnUpdateExperienceTest(int initialExpValue, int expUpdateValue) throws ExpTooLowException {
        experienceCurrentLevel = getNewExperienceCurrentLevel(DEFAULT_EXP_MAX, initialExpValue);
        int newExp = initialExpValue + expUpdateValue;
        if (newExp < 0) {
            doThrow(ExpTooLowException.class).when(experienceMock).updateExperience(anyInt());
        } else {
            doReturn(newExp).when(experienceMock).getCurrentExp();
        }
    }

    private void assertExperienceViewsUpdated(int currentExp) {
        assertExperienceLabelValues(currentExp);
        verify(experienceProgressBarAnimationMock).start(any(ProgressBar.class), any(Experience.class));
    }

    private void assertProgressbarValues(int expMax, int currentExp) {
        ProgressBar progressBar = (ProgressBar) experienceCurrentLevel.findViewById(R.id.experience_progressBar);
        assertEquals(expMax, progressBar.getMax());
        assertProgressbarProgressValue(progressBar, currentExp);
    }

    private void assertProgressbarProgressValue(ProgressBar progressBar, int currentExp) {
        assertEquals(currentExp, progressBar.getProgress());
    }

    private void assertExperienceLabelValues(int currentExpValue) {
        TextView expLabel = (TextView) experienceCurrentLevel.findViewById(R.id.experience_text);
        String labelText = expLabel.getText().toString();
        boolean labelContainsCurrentExpValue = labelText.contains(String.valueOf(currentExpValue));
        assertTrue(labelContainsCurrentExpValue);
    }

    private void assertIsDefaultNewExperience(Experience experience) {
        Experience expectedExperience = new Experience();
        assertEquals(expectedExperience, experience);
    }
}
