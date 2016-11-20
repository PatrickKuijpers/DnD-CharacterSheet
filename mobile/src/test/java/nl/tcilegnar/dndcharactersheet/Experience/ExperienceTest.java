package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelTableUtil;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static nl.tcilegnar.dndcharactersheet.Experience.Experience.EXP_MIN;
import static nl.tcilegnar.dndcharactersheet.Level.Level.CurrentProjectedLevelListener;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceTest {
    private static final int DEFAULT_EXP = Storage.Key.CURRENT_EXP.defaultValue;
    private static final int DEFAULT_LEVEL = Storage.Key.CURRENT_LEVEL.defaultValue;
    private static final int DEFAULT_READY_FOR_LEVEL_CHANGE = Storage.Key.READY_FOR_LEVEL_CHANGE.defaultValue;
    private static final int DEFAULT_CURRENT_PROJECTED_LEVEL = DEFAULT_LEVEL + DEFAULT_READY_FOR_LEVEL_CHANGE;
    private static final int DEFAULT_MAX_EXP = 0;
    private static Experience exp;
    private Storage storageMock;
    private ExperienceUpdater experienceUpdaterMock;
    private LevelTableUtil levelTableUtilMock;
    private CurrentProjectedLevelListener currentProjectedLevelListener;
    private int initialExp;
    private int expectedNewExp;

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
        Experience exp = new Experience(storageMock, experienceUpdaterMock, levelTableUtilMock);

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
        assertEquals(EXP_MIN, min);
    }

    @Test
    public void getMax_NoMaxSet_IsDefaultValue() {
        // Arrange
        initExpDefault();

        // Act
        int max = exp.getMax();

        // Assert
        assertMaxIsCorrectRequestedByLevelTableUtil(DEFAULT_MAX_EXP, max);
    }

    @Test
    public void getMax_MaxDefinedByCurrentLevel_IsCorrectMockedValueRequestedByLevelTableUtil() {
        // Arrange
        int expectedMax = 31000;
        initExpDefault();
        mockCurrentLevelAndMaxExp(7, expectedMax);

        // Act
        int max = exp.getMax();

        // Assert
        assertMaxIsCorrectRequestedByLevelTableUtil(expectedMax, max);
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
        mockUpdatedExperience(addedExp);

        // Act
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated();
    }

    @Test
    public void updateExperience_ExperienceUpdaterReturnsNegativeValue_ExpUpdatedWithReturnValueOfExperienceUpdater()
            throws ExpTooLowException {
        // Arrange
        initExp(100);

        int addedExp = -10;
        mockUpdatedExperience(addedExp);

        // Act
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated();
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
        exp.addExperienceEdgeListener(experienceEdgeListenerMock);

        // Assert
        verify(experienceUpdaterMock).addExperienceEdgeListener(experienceEdgeListenerMock);
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
        levelTableUtilMock = mock(LevelTableUtil.class);
        Experience exp = new Experience(storageMock, experienceUpdaterMock, levelTableUtilMock);
        setListeners(exp);
        return exp;
    }

    private void setListeners(Experience exp) {
        currentProjectedLevelListener = mock(CurrentProjectedLevelListener.class);
        mockCurrentLevel(DEFAULT_CURRENT_PROJECTED_LEVEL);
        mockMaxExpForCurrentLevel(DEFAULT_MAX_EXP, DEFAULT_CURRENT_PROJECTED_LEVEL);
        exp.setCurrentProjectedLevelListener(currentProjectedLevelListener);
    }

    private void mockCurrentLevel(int currentLevel) {
        doReturn(currentLevel).when(currentProjectedLevelListener).getCurrentProjectedLevel();
    }

    private void mockMaxExpForCurrentLevel(int maxExp, int currentLevel) {
        doReturn(maxExp).when(levelTableUtilMock).getMaxExperience(currentLevel);
    }

    private void mockCurrentLevelAndMaxExp(int currentLevel, int maxExp) {
        mockCurrentLevel(currentLevel);
        mockMaxExpForCurrentLevel(maxExp, currentLevel);
    }

    private void assertMaxIsCorrectRequestedByLevelTableUtil(int expectedMax, int max) {
        verify(levelTableUtilMock).getMaxExperience(anyInt());
        assertEquals("max is not equal to expectedMax for level", expectedMax, max);
    }

    private void mockUpdatedExperience(int addedExp) throws ExpTooLowException {
        expectedNewExp = initialExp + addedExp;
        doReturn(expectedNewExp).when(experienceUpdaterMock).getUpdatedExperience(addedExp);
    }

    private void assertExpIsUpdated() {
        int newExp = exp.getCurrentExp();
        assertEquals("newExp is not equal to expectedExp", expectedNewExp, newExp);
    }
}
