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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceUpdaterTest {
    private static final int DEFAULT_EXP = Integer.valueOf(Storage.Key.CURRENT_EXP.defaultValue);
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
            ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(1);
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExp_OnExperienceMaxReachedAndNewExpIsLeftoverExp() throws
            ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(1);
        assertExpIsUpdatedOverMax(addedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwice_OnExperienceMaxReachedTwiceAndNewExpIsLeftoverExp() throws
            ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = experienceMock.getMax() * numberOfTimes + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(2);
        assertExpIsUpdatedOverMax(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_AddNotOverMaxExp_NotOnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp() - 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(0);
    }

    @Test
    public void
    getUpdatedExperience_AddOverMaxExpAndMaxLevelReached_NoLevelsChangedAndOnExperienceMaxReachedAndUpdatedExpIsMax()
            throws ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);
        doThrow(new Level.MaxLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMaxReached();

        // Act
        int addedExp = experienceMock.getMax() + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMaxLevelReached(0);
        assertEquals(experienceMock.getMax(), updatedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwiceAndMaxLevelReachedFirstTime_NoLevelUpAndMaxLevelReached()
            throws ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);
        doThrow(new Level.MaxLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMaxReached();

        // Act
        int numberOfTimes = 2;
        int addedExp = experienceMock.getMax() * numberOfTimes + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMaxLevelReached(0);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwiceAndMaxLevelReachedSecondTime_OneLevelUpAndMaxLevelReached()
            throws ExpTooLowException, MaxLevelReachedException, MinLevelReachedException {
        // Arrange
        initExp(10);
        doNothing().doThrow(new Level.MaxLevelReachedException()).when(experienceEdgeListenerMock)
                .onExperienceMaxReached(); // MaxLevelReachedException bij 2e call op onExperienceMaxReached

        // Act
        int numberOfTimes = 2;
        int addedExp = experienceMock.getMax() * numberOfTimes + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMaxLevelReached(1);
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
    public void getUpdatedExperience_SubstractOverMinExp_ExpNotUpdated() throws ExpTooLowException,
            MinLevelReachedException, MaxLevelReachedException {
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
        assertNumberOfLevelsChanged(0);
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractExactlyUpToMinExp_NotOnExperienceMinPassedAndNewExpIsMinExp()
            throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(0);
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExp_OnExperienceMinPassedAndNewExpIsLeftoverExp()
            throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(-1);
        assertExpIsUpdatedBelowMin(addedExp);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpTwice_OnExperienceMinPassedTwiceAndNewExpIsLeftoverExp() throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(-2);
        assertExpIsUpdatedBelowMin(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_LevelDownIsAllowedAndSubstractNotOverMinExp_NotOnExperienceMinPassed() throws
            ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);

        // Act
        int addedExp = -experienceMock.getCurrentExp() + 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChanged(0);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpAndMinLevelPassed_NoLevelsChangedAndMinLevelReached
            () throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);
        doThrow(new Level.MinLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMinPassed();

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMinLevelReached(0);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpAndMinLevelPassed_OneLevelDownAndMinLevelReached()
            throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);
        doNothing().doThrow(new Level.MinLevelReachedException()).when(experienceEdgeListenerMock)
                .onExperienceMinPassed(); // MinLevelReachedException bij 2e call op onExperienceMinPassed

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMinLevelReached(-1);
    }

    @Test
    public void
    getUpdatedExperience_LevelDownIsAllowedAndSubstractOverMinExpTwiceAndMinLevelPassedFirstTime_NoLevelsChangedAndMinLevelReached() throws ExpTooLowException, MinLevelReachedException, MaxLevelReachedException {
        // Arrange
        initExp(10, IS_ALLOWED_LEVEL_DOWN);
        doThrow(new Level.MinLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMinPassed();

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertNumberOfLevelsChangedAndMinLevelReached(0);
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

    private void assertNumberOfLevelsChanged(int expectedNumberOfLevelsChanged) throws MaxLevelReachedException,
            MinLevelReachedException {
        assertEquals(expectedNumberOfLevelsChanged, experienceUpdater.getNumberOfLevelsChanged());
        assertCorrectListenersCalled(expectedNumberOfLevelsChanged);
    }

    private void assertNumberOfLevelsChangedAndMaxLevelReached(int expectedNumberOfLevelsChanged) throws
            MaxLevelReachedException, MinLevelReachedException {
        // 1x extra onExperienceMaxReached:
        assertCorrectListenersCalled(expectedNumberOfLevelsChanged + 1);
        // ...maar hiervoor niet extra lvl up:
        assertEquals(expectedNumberOfLevelsChanged, experienceUpdater.getNumberOfLevelsChanged());

        assertEquals(experienceMock.getMax(), updatedExp);
    }

    private void assertNumberOfLevelsChangedAndMinLevelReached(int expectedNumberOfLevelsChanged) throws
            MaxLevelReachedException, MinLevelReachedException {
        // 1x extra onExperienceMinPassed:
        assertCorrectListenersCalled(expectedNumberOfLevelsChanged - 1);
        // ...maar hiervoor niet extra lvl down:
        assertEquals(expectedNumberOfLevelsChanged, experienceUpdater.getNumberOfLevelsChanged());

        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    /**
     * @param expectedExpEdgesReached - het aantal keer dat een expEdge bereikt is: <0 betekent onExperienceMinPassed,
     *                                >0 betekent onExperienceMaxReached
     */
    private void assertCorrectListenersCalled(int expectedExpEdgesReached) throws MinLevelReachedException,
            MaxLevelReachedException {
        if (expectedExpEdgesReached < 0) {
            verify(experienceEdgeListenerMock, times(-expectedExpEdgesReached)).onExperienceMinPassed();
            verify(experienceEdgeListenerMock, never()).onExperienceMaxReached();
        } else if (expectedExpEdgesReached > 0) {
            verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
            verify(experienceEdgeListenerMock, times(expectedExpEdgesReached)).onExperienceMaxReached();
        } else {
            verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
            verify(experienceEdgeListenerMock, never()).onExperienceMaxReached();
        }
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
