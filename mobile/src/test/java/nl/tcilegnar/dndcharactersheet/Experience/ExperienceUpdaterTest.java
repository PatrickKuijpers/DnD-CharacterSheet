package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.MyBuildConfig;
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
    private static final boolean IS_DEBUG = true;
    private static final boolean IS_NOT_DEBUG = false;
    private static final boolean DEFAULT_BUILD_TYPE = IS_NOT_DEBUG;
    private static ExperienceUpdater experienceUpdater;
    private Experience experienceMock;
    private MyBuildConfig buildConfigMock;
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
    public void getUpdatedExperience_AddExactlyUpToMaxExp_UpdatedExpIsMinExp() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void getUpdatedExperience_AddExactlyUpToMaxExp_OnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() - experienceMock.getCurrentExp();
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExp_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedOverMax(addedExp);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExp_OnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = experienceMock.getMax() + 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwice_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = experienceMock.getMax() * numberOfTimes + 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedOverMax(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_AddOverMaxExpTwice_OnExperienceMaxReachedTwice() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = numberOfTimes * experienceMock.getMax() + 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMaxReached();
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
    public void getUpdatedExperience_AddOverMaxExpAndMaxLevelReached_UpdatedExpIsMax() throws ExpTooLowException,
            MaxLevelReachedException {
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
    public void getUpdatedExperience_DebugSubstractExactlyUpToMinExp_NewExpIsMinExp() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -experienceMock.getCurrentExp();
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    @Test
    public void getUpdatedExperience_DebugSubstractExactlyUpToMinExp_NotOnExperienceMinPassed() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -experienceMock.getCurrentExp();
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
    }

    @Test
    public void getUpdatedExperience_DebugSubstractOverMinExp_NewExpIsLeftoverExp() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedBelowMin(addedExp);
    }

    @Test
    public void getUpdatedExperience_DebugSubstractOverMinExp_OnExperienceMinPassed() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinPassed();
    }

    @Test
    public void getUpdatedExperience_DebugSubstractOverMinExpTwice_NewExpIsLeftoverExp() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        assertExpIsUpdatedBelowMin(addedExp, numberOfTimes);
    }

    @Test
    public void getUpdatedExperience_DebugSubstractOverMinExpTwice_OnExperienceMinPassedTwice() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int numberOfTimes = 2;
        int addedExp = -experienceMock.getMax() * numberOfTimes - 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMinPassed();
    }

    @Test
    public void getUpdatedExperience_DebugSubstractNotOverMinExp_NotOnExperienceMinPassed() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -experienceMock.getCurrentExp() + 1;
        experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMinPassed();
    }

    @Test
    public void getUpdatedExperience_DebugSubstractOverMinExpAndMinLevelPassed_UpdatedExpIsMin() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);
        doThrow(new Level().new MinLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMinPassed();

        // Act
        int addedExp = -experienceMock.getMax() - 1;
        updatedExp = experienceUpdater.getUpdatedExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinPassed();
        assertEquals(Experience.EXP_MIN, updatedExp);
    }

    private void initExpUpdaterDefault() {
        initExp(DEFAULT_EXP, DEFAULT_BUILD_TYPE);
    }

    private void initExp(int initialExp) {
        initExp(initialExp, DEFAULT_BUILD_TYPE);
    }

    private void initExp(int initialExp, boolean initialBuildType) {
        this.initialExp = initialExp;
        experienceUpdater = getNewExperienceUpdaterWithMocksAndListeners(initialExp, initialBuildType);
    }

    private ExperienceUpdater getNewExperienceUpdaterWithMocksAndListeners(int initialSavedExperience, boolean
            initialBuildType) {
        experienceMock = mock(Experience.class);
        buildConfigMock = mock(MyBuildConfig.class);
        doReturn(initialSavedExperience).when(experienceMock).getCurrentExp();
        doReturn(Experience.EXP_MIN).when(experienceMock).getMin();
        doReturn(DEFAULT_EXP_MAX).when(experienceMock).getMax();
        doReturn(initialBuildType).when(buildConfigMock).isDebug();
        ExperienceUpdater experienceUpdater = new ExperienceUpdater(experienceMock, buildConfigMock);
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
        if (buildConfigMock.isDebug()) {
            assertExpIsUpdatedCorrectly(addedExp + experienceMock.getMax() * numberOfTimes);
        } else {
            assertExpIsNotUpdated();
        }
    }

    private void assertExpIsNotUpdated() {
        assertEquals(initialExp, updatedExp);
    }
}
