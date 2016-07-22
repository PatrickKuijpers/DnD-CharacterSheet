package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceTest {
    private static final int DEFAULT_EXP = Storage.Key.CURRENT_EXP.defaultValue;
    private static Experience exp;
    private Storage storageMock;
    private ExperienceUpdater experienceUpdaterMock;
    private int initialExp;

    @Test
    public void newExperience_Default_LevelLoadedAndDefaultLevelIsSetAsCurrentLevel() {
        // Arrange
        initExpDefault();

        // Act
        Experience exp = new Experience();

        // Assert
        verify(storageMock, times(1)).loadExperience();
        assertEquals(DEFAULT_EXP, exp.getCurrentExp());
    }

    @Test
    public void newExperience_StorageValuePositive_IsSetAsCurrentLevel() {
        // Arrange
        Storage storageMock = mock(Storage.class);
        int expectedSavedExperience = 1200;
        doReturn(expectedSavedExperience).when(storageMock).loadExperience();

        // Act
        Experience exp = new Experience(storageMock, experienceUpdaterMock);

        // Assert
        verify(storageMock, times(1)).loadExperience();
        assertEquals(expectedSavedExperience, exp.getCurrentExp());
    }

    @Test
    public void save_DefaultValue() {
        // Arrange
        initExpDefault();

        // Act
        exp.save();

        // Assert
        verify(storageMock).saveExperience(DEFAULT_EXP);
    }

    @Test
    public void save_LoadedValue() {
        // Arrange
        initExp(1200);

        // Act
        exp.save();

        // Assert
        verify(storageMock).saveExperience(initialExp);
    }

    @Test
    public void save_DefaultValuePlusUpdateExpBeforeSave_NewValueIsSaved() throws ExpTooLowException {
        // Arrange
        initExpDefault();

        int addedExp = 1;
        doReturn(initialExp + addedExp).when(experienceUpdaterMock).getUpdatedExperience(addedExp);
        exp.updateExperience(addedExp);

        // Act
        exp.save();

        // Assert
        int expectedLevel = initialExp + addedExp;
        verify(storageMock).saveExperience(expectedLevel);
    }

    @Test
    public void getMin_NoMinSet_IsConstantZero() {
        // Arrange
        initExpDefault();

        // Act
        int min = exp.getMin();

        // Assert
        assertEquals(Experience.EXP_MIN, min);
    }

    @Test
    public void getMax_NoMaxSet_IsBiggerThanZero() {
        // Arrange
        initExpDefault();

        // Act
        int max = exp.getMax();

        // Assert
        assertTrue(max > 0);
    }

    @Test
    public void getCurrentExp_NoValueSet_ValueIsDefaultExp() {
        // Arrange
        initExpDefault();

        // Act
        int currentExp = exp.getCurrentExp();

        // Assert
        assertEquals(DEFAULT_EXP, currentExp);
    }

    @Test
    public void updateExperience_ExperienceUpdaterReturnsPositiveValue_ExpUpdatedWithReturnValueOfExperienceUpdater()
            throws ExpTooLowException {
        // Arrange
        initExpDefault();

        int addedExp = 10;
        int expectedNewExp = getExpectedNewExpFromExperienceUpdaterMock(addedExp);

        // Act
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(expectedNewExp);
    }

    @Test
    public void updateExperience_ExperienceUpdaterReturnsNegativeValue_ExpUpdatedWithReturnValueOfExperienceUpdater()
            throws ExpTooLowException {
        // Arrange
        initExp(100);

        int addedExp = -10;
        int expectedNewExp = getExpectedNewExpFromExperienceUpdaterMock(addedExp);

        // Act
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(expectedNewExp);
    }

    @Test(expected = ExpTooLowException.class)
    public void updateCurrentExp_CauseExpTooLowException_ExpTooLowExceptionThrown() throws ExpTooLowException {
        // Arrange
        initExp(10);

        int addedExp = -100;
        doThrow(ExpTooLowException.class).when(experienceUpdaterMock).getUpdatedExperience(addedExp);

        // Act
        exp.updateExperience(addedExp);

        // Assert
    }

    @Test
    public void setExperienceEdgeListener_UseMock_ListenerIsSetOnExperienceUpdater() {
        // Arrange
        initExpDefault();
        ExperienceEdgeListener experienceEdgeListenerMock = mock(ExperienceEdgeListener.class);

        // Act
        exp.setExperienceEdgeListener(experienceEdgeListenerMock);

        // Assert
        verify(experienceUpdaterMock).setExperienceEdgeListener(experienceEdgeListenerMock);
    }

    private void initExpDefault() {
        initExp(DEFAULT_EXP);
    }

    private void initExp(int initialExp) {
        this.initialExp = initialExp;
        exp = getNewExperienceWithMocksAndListeners(initialExp);
    }

    private Experience getNewExperienceWithMocksAndListeners(int initialSavedExperience) {
        storageMock = mock(Storage.class);
        experienceUpdaterMock = mock(ExperienceUpdater.class);
        doReturn(initialSavedExperience).when(storageMock).loadExperience();
        Experience exp = new Experience(storageMock, experienceUpdaterMock);
        return exp;
    }

    private int getExpectedNewExpFromExperienceUpdaterMock(int addedExp) throws ExpTooLowException {
        int expectedNewExperience = initialExp + addedExp;
        doReturn(expectedNewExperience).when(experienceUpdaterMock).getUpdatedExperience(addedExp);
        return expectedNewExperience;
    }

    private void assertExpIsUpdated(int expectedNewExp) {
        int newExp = exp.getCurrentExp();
        String errorMessage = "newExp is not equal to expectedExp: " + expectedNewExp;
        assertEquals(errorMessage, expectedNewExp, newExp);
    }
}
