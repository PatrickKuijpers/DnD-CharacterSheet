package nl.tcilegnar.dndcharactersheet.Experience;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExpTooHighException;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExperienceTest {
	private static Experience exp;

	@Before
	public void setUp() {
		exp = new Experience();
	}

	@Test
	public void testConstructor_StorageValueZero_IsSetAsCurrentExp() throws IOException {
		// Arrange
		Storage storageMock = mock(Storage.class);
		int expectedSavedExp = 0;
		doReturn(expectedSavedExp).when(storageMock).getExperience();

		// Act
		exp = new Experience(storageMock);

		// Assert
		verify(storageMock, times(1)).getExperience();
		assertEquals(expectedSavedExp, exp.getCurrentExp());
	}

	@Test
	public void testConstructor_StorageValuePositive_IsSetAsCurrentExp() throws IOException {
		// Arrange
		Storage storageMock = mock(Storage.class);
		int expectedSavedExp = 10;
		doReturn(expectedSavedExp).when(storageMock).getExperience();

		// Act
		exp = new Experience(storageMock);

		// Assert
		verify(storageMock, times(1)).getExperience();
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
	public void testUpdateCurrentExp_With0Exp_ExpSameAsInitialExp() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		int initialExp = exp.getCurrentExp();

		// Act
		int newExp = exp.updateExperience(0);

		// Assert
		assertEquals(initialExp, newExp);
	}

	@Test
	public void testUpdateCurrentExp_StartWith10Add10Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		int initialExp = exp.getCurrentExp();

		// Act
		int newExp = exp.updateExperience(10);

		// Assert
		assertNotSame(initialExp, newExp);
		assertEquals(10, newExp);
	}

	@Test
	public void testUpdateCurrentExp_StartWith10Add5Exp_NotSameAsInitialExpAndIncreased() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		int initialExp = exp.updateExperience(10);

		// Act
		int newExp = exp.updateExperience(5);

		// Assert
		assertNotSame(initialExp, newExp);
		assertEquals(15, newExp);
	}

	@Test
	public void testUpdateCurrentExp_StartWith10Substract5Exp_NotSameAsInitialExpAndDecreased() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		int initialExp = exp.updateExperience(10);

		// Act
		int newExp = exp.updateExperience(-5);

		// Assert
		assertNotSame(initialExp, newExp);
		assertEquals(5, newExp);
	}

	@Test(expected = ExpTooHighException.class)
	public void testUpdateCurrentExp_StartWith10AddMaxExp_ExpTooHighException() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		exp.updateExperience(10);

		// Act
		exp.updateExperience(exp.getMax());

		// Assert
		// Verwacht ExpTooHighException
	}

	@Test(expected = ExpTooLowException.class)
	public void testUpdateCurrentExp_StartWith10Substract25Exp_ExpTooLowException() throws ExpTooLowException, ExpTooHighException {
		// Arrange
		exp.updateExperience(10);

		// Act
		exp.updateExperience(-25);

		// Assert
		// Verwacht ExpTooLowException
	}

	@Test
	public void testSaveExp_SaveZeroExp_StorageMethodCalledWithZeroExp() throws IOException {
		// Arrange
		Storage storageMock = mock(Storage.class);
		exp = new Experience(storageMock);

		// Act
		exp.saveExp();

		// Assert
		int expectedSavedExp = 0;
		verify(storageMock, times(1)).saveExperience(eq(expectedSavedExp));
	}

	@Test
	public void testSaveExp_SavePositiveExp_StorageMethodCalledWithSameExp() throws IOException {
		// Arrange
		Storage storageMock = mock(Storage.class);
		int expectedSavedExp = 10;
		doReturn(expectedSavedExp).when(storageMock).getExperience();
		exp = new Experience(storageMock);

		// Act
		exp.saveExp();

		// Assert
		verify(storageMock, times(1)).saveExperience(eq(expectedSavedExp));
	}
}
