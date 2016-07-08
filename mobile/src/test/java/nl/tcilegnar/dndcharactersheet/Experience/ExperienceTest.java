package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.BuildType;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceTest {
    private static Experience exp;
    private Storage storageMock;
    private BuildType buildTypeMock;

    @Before
    public void setUp() {
        storageMock = mock(Storage.class);
        buildTypeMock = mock(BuildType.class);
        doReturn(false).when(buildTypeMock).isDebug();
        exp = new Experience(storageMock, buildTypeMock);
    }

    @Test
    public void testConstructor_StorageValuePositive_IsSetAsCurrentExp() throws IOException {
        // Arrange
        final int CALLED_IN_CONSTRUCTOR = 1;
        int expectedSavedExp = 10;
        doReturn(expectedSavedExp).when(storageMock).loadExperience();
        verify(storageMock, times(CALLED_IN_CONSTRUCTOR)).loadExperience();

        // Act
        Experience exp = new Experience(storageMock, buildTypeMock);

        // Assert
        verify(storageMock, times(CALLED_IN_CONSTRUCTOR + 1)).loadExperience();
        assertEquals(expectedSavedExp, exp.getCurrentExp());
    }

    @Test
    public void testGetMax_NoMaxSet_BiggerThanZero() {
        // Arrange

        // Act
        int max = exp.getMax();

        // Assert
        assertTrue(max > 0);
    }

    @Test
    public void testGetCurrentExp_NoValueSet_ValueIsZero() {
        // Arrange

        // Act
        int currentExp = exp.getCurrentExp();

        // Assert
        assertEquals(0, currentExp);
    }

    @Test
    public void testUpdateCurrentExp_With0Exp_ExpSameAsInitialExp() throws ExpTooLowException {
        // Arrange
        int initialExp = exp.getCurrentExp();

        // Act
        int newExp = exp.updateExperience(0);

        // Assert
        assertEquals(initialExp, newExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10Add10Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException {
        // Arrange
        int initialExp = exp.getCurrentExp();

        // Act
        int newExp = exp.updateExperience(10);

        // Assert
        assertNotSame(initialExp, newExp);
        assertEquals(10, newExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10Add5Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException {
        // Arrange
        int initialExp = exp.updateExperience(10);

        // Act
        int newExp = exp.updateExperience(5);

        // Assert
        assertNotSame(initialExp, newExp);
        assertEquals(15, newExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10Substract5Exp_NotSameAsInitialExpAndDecreased() throws
            ExpTooLowException {
        // Arrange
        int initialExp = exp.updateExperience(10);

        // Act
        int newExp = exp.updateExperience(-5);

        // Assert
        assertNotSame(initialExp, newExp);
        assertEquals(5, newExp);
    }

    @Test
    public void testUpdateCurrentExp_StartWith10AddMaxExp_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        ExperienceEdgeListener mockLevel = mock(Level.class);
        exp.setExperienceEdgeListener(mockLevel);
        exp.updateExperience(10);

        // Act
        int newExp = exp.updateExperience(exp.getMax());

        // Assert
        assertEquals(10, newExp);
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExp_OnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        ExperienceEdgeListener mockLevel = mock(Level.class);
        exp.setExperienceEdgeListener(mockLevel);

        // Act
        exp.updateExperience(exp.getMax() + 1);

        // Assert
        verify(mockLevel, times(1)).onExperienceMaxReached();
    }

    @Test
    public void testUpdateCurrentExp_AddNotOverMaxExp_NotOnExperienceMaxReached() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        ExperienceEdgeListener mockLevel = mock(Level.class);
        exp.setExperienceEdgeListener(mockLevel);

        // Act
        exp.updateExperience(exp.getMax() - 1);

        // Assert
        verify(mockLevel, times(0)).onExperienceMaxReached();
    }

    @Test
    public void testUpdateCurrentExp_AddOverMaxExpAndMaxLevelReached_NewExpIsMax() throws ExpTooLowException,
            MaxLevelReachedException {
        // Arrange
        ExperienceEdgeListener mockLevel = mock(Level.class);
        doThrow(new Level().new MaxLevelReachedException()).when(mockLevel).onExperienceMaxReached();
        exp.setExperienceEdgeListener(mockLevel);

        // Act
        int newExp = exp.updateExperience(exp.getMax() + 1);

        // Assert
        verify(mockLevel, times(1)).onExperienceMaxReached();
        assertEquals(exp.getMax(), newExp);
    }

    @Test(expected = ExpTooLowException.class)
    public void testUpdateCurrentExp_SubstractOverMinExp_ExpTooLowException() throws ExpTooLowException {
        // Arrange
        exp.updateExperience(10);

        // Act
        exp.updateExperience(-25);

        // Assert
    }

    @Test
    public void testUpdateCurrentExp_SubstractOverMinExp_ExpNotUpdated() throws ExpTooLowException {
        // Arrange
        int initialExp = 10;
        exp.updateExperience(initialExp);

        // Act
        try {
            exp.updateExperience(-25);
        } catch (ExpTooLowException e) {
            // Doe niets
        }

        // Assert
        assertEquals(initialExp, exp.getCurrentExp());
    }

    @Test // TODO
    public void testUpdateCurrentExp_DebugSubstractOverMinExp_NewExpIsLeftoverExp() throws ExpTooLowException {
        // Arrange
        doReturn(true).when(buildTypeMock).isDebug();
        Experience exp = new Experience(storageMock, buildTypeMock);

        ExperienceEdgeListener mockLevel = mock(Level.class);
        exp.setExperienceEdgeListener(mockLevel);

        exp.updateExperience(10);

        // Act
        int newExp = exp.updateExperience(-exp.getMax());

        // Assert
        assertEquals(10, newExp);
    }

    @Test // TODO
    public void testUpdateCurrentExp_DebugSubstractOverMinExpAndMinLevelReached_NewExpIsMin() throws
            ExpTooLowException, Level.MinLevelReachedException {
        // Arrange
        doReturn(true).when(buildTypeMock).isDebug();
        Experience exp = new Experience(storageMock, buildTypeMock);

        ExperienceEdgeListener mockLevel = mock(Level.class);
        doThrow(new Level().new MinLevelReachedException()).when(mockLevel).onExperienceMinReached();
        exp.setExperienceEdgeListener(mockLevel);

        exp.updateExperience(10);

        // Act
        int newExp = exp.updateExperience(-25);

        // Assert
        verify(mockLevel, times(1)).onExperienceMinReached();
        assertEquals(exp.getMin(), newExp);
    }

    @Test
    public void testSaveExp_SaveZeroExp_StorageMethodCalledWithZero() {
        // Arrange
        Experience exp = new Experience(storageMock, buildTypeMock);

        // Act
        exp.saveExp();

        // Assert
        int expectedSavedExp = 0;
        verify(storageMock, times(1)).saveExperience(eq(expectedSavedExp));
    }

    @Test
    public void testSaveExp_SavePositiveExp_StorageMethodCalledWithSameValue() {
        // Arrange
        int expectedSavedExp = 10;
        doReturn(expectedSavedExp).when(storageMock).loadExperience();
        Experience exp = new Experience(storageMock, buildTypeMock);

        // Act
        exp.saveExp();

        // Assert
        verify(storageMock, times(1)).saveExperience(eq(expectedSavedExp));
    }
}
