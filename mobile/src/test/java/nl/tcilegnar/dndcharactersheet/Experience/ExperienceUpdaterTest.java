package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettings;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceUpdaterTest {
    private static final int DEFAULT_EXP = Storage.Key.CURRENT_EXP.defaultValue;
    private static final int DEFAULT_EXP_MAX = 1000;
    private static final boolean IS_ALLOWED_LEVEL_DOWN = true;
    private static final boolean IS_NOT_ALLOWED_LEVEL_DOWN = false;
    private static final boolean DEFAULT_IS_ALLOWED_LEVEL_DOWN = IS_NOT_ALLOWED_LEVEL_DOWN;
    private static ExperienceUpdater experienceUpdater;
    private Experience experienceMock;
    private ExperienceSettings settingsMock;
    private ExperienceEdgeListener experienceEdgeListenerMock;
    private int initialExp;
    private int updatedExp;

    @Test
    public void getUpdatedExperience_DefaultInitialExpAndAdded0Exp_ExpUpdatedCorrectly() throws ExpTooLowException {
        // Arrange
        initExpUpdaterDefault();

        // Act
        int addedExp = 0;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsNotUpdated();
    }

    @Test
    public void getUpdatedExperience_DefaultInitialExpAndAdded10Exp_ExpUpdatedCorrectly() throws ExpTooLowException {
        // Arrange
        initExpUpdaterDefault();

        // Act
        int addedExp = 10;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedCorrectly(addedExp);
    }

    @Test
    public void getUpdatedExperience_StartWith10Add5Exp_ExpIsUpdatedCorrectly() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = 5;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedCorrectly(addedExp);
    }

    @Test
    public void getUpdatedExperience_StartWith10Substract5Exp_ExpIsUpdatedCorrectly() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -5;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedCorrectly(addedExp);
    }

    @Test
    public void getUpdatedExperience_AddExactlyUpToMaxExp_OnExperienceMaxReachedAndUpdatedExpIsMinExp() throws
            ExpTooLowException, MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExp_OnExperienceMaxReachedAndNewExpIsLeftoverExp() throws
            ExpTooLowException, MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
        assertExpIsUpdatedOverMax(addedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwice_OnExperienceMaxReachedTwiceAndNewExpIsLeftoverExp() throws
            ExpTooLowException, MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = experienceMock.getMax() * numberOfTimes + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMaxReached();
        assertExpIsUpdatedOverMax(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_AddNotOverMaxExp_NotOnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp() - 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMaxReached();
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpAndMaxLevelReached_OnExperienceMaxReachedAndUpdatedExpIsMax()
            throws ExpTooLowException, MaxLevelReachedException {
        // Arrange
        initExp(10);
        doThrow(new Level().new MaxLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMaxReached();

        // Act
        int addedExp = experienceMock.getMax() + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
        assertEquals(experienceMock.getMax(), updatedExp);
    }

    @Test(expected = ExpTooLowException.class)
    public void getUpdatedExperience_SubstractOverMinExp_ExpTooLowException() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
    }

    @Test
    public void getUpdatedExperience_SubstractOverMinExp_ExpNotUpdated() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        try {
            updatedExp = experienceUpdater.getUpdatedExperience(addedExp);
        } catch (ExpTooLowException e) {
            // Doe niets
        }

        // Assert
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractExactlyUpToMinExp_NotOnExperienceMinPassedAndNewExpIsMinExp()
            throws ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    public ExperienceUpdaterTest() {
        super();
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExp_OnExperienceMinPassedAndNewExpIsLeftoverExp()
            throws ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinPassed();
        assertExpIsUpdatedBelowMin(addedExp);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpTwice_OnExperienceMinPassedTwiceAndNewExpIsLeftoverExp() throws ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMinPassed();
        assertExpIsUpdatedBelowMin(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_LevelDownIsAllowedAndSubstractNotOverMinExp_NotOnExperienceMinPassed() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getCurrentExp() + 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
    }

    @Test
    public void getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpAndMinLevelPassed_UpdatedExpIsMin()
            throws ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);
        doThrow(new Level().new MinLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMinPassed();

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinPassed();
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    private void initExpUpdaterDefault() {
        initExp(DEFAULT_EXP, DEFAULT_IS_ALLOWED_LEVEL_DOWN);
    }

    private void initExp(int initialExp) {
        initExp(initialExp, DEFAULT_IS_ALLOWED_LEVEL_DOWN);
    }

    private void initExp(int initialExp, boolean initialBuildType) {
        this.initialExp = initialExp;
        experienceUpdater = getNewExperienceUpdaterWithMocksAndListeners(initialExp, initialBuildType);
    }

    private ExperienceUpdater getNewExperienceUpdaterWithMocksAndListeners(int initialSavedExperience, boolean
            initialIsAllowedLevelDown) {
        experienceMock = mock(Experience.class);
        settingsMock = mock(ExperienceSettings.class);
        doReturn(initialSavedExperience).when(experienceMock).getCurrentExp();
        doReturn(Experience.EXP_MIN).when(experienceMock).getMin();
        doReturn(DEFAULT_EXP_MAX).when(experienceMock).getMax();
        doReturn(initialIsAllowedLevelDown).when(settingsMock).isLevelDownAllowed();
        ExperienceUpdater experienceUpdater = new ExperienceUpdater(experienceMock, settingsMock);
        initListeners(experienceUpdater);
        return experienceUpdater;
    }

    private void initListeners(ExperienceUpdater experienceUpdater) {
        experienceEdgeListenerMock = mock(ExperienceEdgeListener.class);
        experienceUpdater.addExperienceEdgeListener(experienceEdgeListenerMock);
    }

    private void assertExpIsUpdatedCorrectly(int addedExp) {
        int expectedExp = initialExp + addedExp;
        String sum = "initialExp: " + initialExp + " + addedExp: " + addedExp;
        String errorMessage = "newExp is not equal to expectedExp (" + sum + ")";
        assertEquals(errorMessage, expectedExp, updatedExp);
    }

    private void assertExpIsUpdatedOverMax(int addedExp) {
        assertExpIsUpdatedOverMax(addedExp, 1);
    }

    private void assertExpIsUpdatedOverMax(int addedExp, int numberOfTimes) {
        assertExpIsUpdatedCorrectly(addedExp - experienceMock.getMax() * numberOfTimes);
    }

    private void assertExpIsUpdatedBelowMin(int addedExp) {
        assertExpIsUpdatedBelowMin(addedExp, 1);
    }

    private void assertExpIsUpdatedBelowMin(int addedExp, int numberOfTimes) {
        if (settingsMock.isLevelDownAllowed()) {
            assertExpIsUpdatedCorrectly(addedExp + experienceMock.getMax() * numberOfTimes);
        } else {
            assertExpIsNotUpdated();
        }
    }

    private void assertExpIsNotUpdated() {
        assertEquals(initialExp, updatedExp);
    }
}
