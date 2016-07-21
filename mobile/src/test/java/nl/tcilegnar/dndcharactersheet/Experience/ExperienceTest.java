package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.BuildType;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceTest {
    private static final int DEFAULT_EXP = Storage.Key.CURRENT_EXP.defaultValue;
    private static final boolean IS_DEBUG = true;
    private static final boolean IS_NOT_DEBUG = false;
    private static final boolean DEFAULT_BUILD_TYPE = IS_NOT_DEBUG;
    private static Experience exp;
    private Storage storageMock;
    private BuildType buildTypeMock;
    private ExperienceEdgeListener experienceEdgeListenerMock;
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
        Experience exp = new Experience(storageMock, buildTypeMock);

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
        int addedValue = 1;
        exp.updateExperience(addedValue);

        // Act
        exp.save();

        // Assert
        int expectedLevel = initialExp + addedValue;
        verify(storageMock).saveExperience(expectedLevel);
    }

    @Test
    public void testGetMin_NoMinSet_IsConstantZero() {
        // Arrange
        initExpDefault();

        // Act
        int min = exp.getMin();

        // Assert
        assertEquals(Experience.EXP_MIN, min);
    }

    @Test
    public void testGetMax_NoMaxSet_IsBiggerThanZero() {
        // Arrange
        initExpDefault();

        // Act
        int max = exp.getMax();

        // Assert
        assertTrue(max > 0);
    }

    @Test
    public void testGetCurrentExp_NoValueSet_ValueIsDefaultExp() {
        // Arrange
        initExpDefault();

        // Act
        int currentExp = exp.getCurrentExp();

        // Assert
        assertEquals(DEFAULT_EXP, currentExp);
    }

    @Test
    public void testUpdateCurrentExp_With0Exp_ExpSameAsInitialExp() throws ExpTooLowException {
        // Arrange
        initExpDefault();

        // Act
        int addedExp = 0;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith0Add10Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException {
        // Arrange
        initExpDefault();

        // Act
        int addedExp = 10;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10Add5Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = 5;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10Substract5Exp_NotSameAsInitialExpAndDecreased() throws
            ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -5;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdated(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExp_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = exp.getMax() + 1;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdatedOverMax(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExp_OnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = exp.getMax() + 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExpTwice_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = exp.getMax() * numberOfTimes + 1;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdatedOverMax(addedExp, numberOfTimes);
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExpTwice_OnExperienceMaxReachedTwice() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int numberOfTimes = 2;
        int addedExp = numberOfTimes * exp.getMax() + 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMaxReached();
    }

    @Test
    public void testUpdateCurrentExp_AddNotOverMaxExp_NotOnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = exp.getMax() - exp.getCurrentExp() - 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMaxReached();
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExpAndMaxLevelReached_NewExpIsMax() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        initExp(10);
        doThrow(new Level().new MaxLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMaxReached();

        // Act
        int addedExp = exp.getMax() + 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMaxReached();
        assertEquals(exp.getMax(), exp.getCurrentExp());
    }

    @Test(expected = ExpTooLowException.class)
    public void testUpdateCurrentExp_SubstractOverMinExp_ExpTooLowException() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -exp.getMax() - 1;
        exp.updateExperience(addedExp);

        // Assert
    }

    @Test
    public void testUpdateCurrentExp_SubstractOverMinExp_ExpNotUpdated() throws ExpTooLowException {
        // Arrange
        initExp(10);

        // Act
        int addedExp = -exp.getMax() - 1;
        try {
            exp.updateExperience(addedExp);
        } catch (ExpTooLowException e) {
            // Doe niets
        }

        // Assert
        assertExpIsUpdatedBelowMin(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractOverMinExp_NewExpIsLeftoverExp() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -exp.getMax() - 1;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdatedBelowMin(addedExp);
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractOverMinExp_OnExperienceMinReached() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -exp.getMax() - 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinReached();
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractOverMinExpTwice_NewExpIsLeftoverExp() throws ExpTooLowException,
            MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int numberOfTimes = 2;
        int addedExp = -exp.getMax() * numberOfTimes - 1;
        exp.updateExperience(addedExp);

        // Assert
        assertExpIsUpdatedBelowMin(addedExp, numberOfTimes);
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractOverMinExpTwice_OnExperienceMinReachedTwice() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int numberOfTimes = 2;
        int addedExp = -exp.getMax() * numberOfTimes - 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, times(2)).onExperienceMinReached();
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractNotOverMinExp_NotOnExperienceMinReached() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);

        // Act
        int addedExp = -exp.getCurrentExp() + 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock, never()).onExperienceMinReached();
    }

    @Test
    public void testUpdateCurrentExp_DebugSubstractOverMinExpAndMinLevelReached_NewExpIsMin() throws
            ExpTooLowException, MinLevelReachedException {
        // Arrange
        initExp(10, IS_DEBUG);
        doThrow(new Level().new MinLevelReachedException()).when(experienceEdgeListenerMock).onExperienceMinReached();

        // Act
        int addedExp = -exp.getMax() - 1;
        exp.updateExperience(addedExp);

        // Assert
        verify(experienceEdgeListenerMock).onExperienceMinReached();
        assertEquals(exp.getMin(), exp.getCurrentExp());
    }

    private void initExpDefault() {
        initExp(DEFAULT_EXP);
    }

    private void initExp(Integer initialExp) {
        initExp(initialExp, DEFAULT_BUILD_TYPE);
    }

    private void initExp(Integer initialExp, boolean initialBuildType) {
        this.initialExp = initialExp;
        exp = getNewExperienceWithMocksAndListeners(initialExp, initialBuildType);
    }

    private Experience getNewExperienceWithMocksAndListeners(int initialSavedExperience, boolean initialBuildType) {
        storageMock = mock(Storage.class);
        buildTypeMock = mock(BuildType.class);
        doReturn(initialSavedExperience).when(storageMock).loadExperience();
        doReturn(initialBuildType).when(buildTypeMock).isDebug();
        Experience exp = new Experience(storageMock, buildTypeMock);
        initListeners(exp);
        return exp;
    }

    private void initListeners(Experience exp) {
        experienceEdgeListenerMock = mock(ExperienceEdgeListener.class);
        exp.setExperienceEdgeListener(experienceEdgeListenerMock);
    }

    private void assertExpIsUpdated(int addedExp) {
        int expectedExp = initialExp + addedExp;
        int newExp = exp.getCurrentExp();
        String sum = "initialExp: " + initialExp + " + addedExp: " + addedExp;
        String errorMessage = "newExp is not equal to expectedExp (" + sum + ")";
        assertEquals(errorMessage, expectedExp, newExp);
    }

    private void assertExpIsUpdatedOverMax(int addedExp) {
        assertExpIsUpdatedOverMax(addedExp, 1);
    }

    private void assertExpIsUpdatedOverMax(int addedExp, int numberOfTimes) {
        assertExpIsUpdated(addedExp - exp.getMax() * numberOfTimes);
    }

    private void assertExpIsUpdatedBelowMin(int addedExp) {
        assertExpIsUpdatedBelowMin(addedExp, 1);
    }

    private void assertExpIsUpdatedBelowMin(int addedExp, int numberOfTimes) {
        if (buildTypeMock.isDebug()) {
            assertExpIsUpdated(addedExp + exp.getMax() * numberOfTimes);
        } else {
            assertExpIsNotUpdated();
        }
    }

    private void assertExpIsNotUpdated() {
        int newExp = exp.getCurrentExp();
        assertEquals(initialExp, newExp);
    }
}
