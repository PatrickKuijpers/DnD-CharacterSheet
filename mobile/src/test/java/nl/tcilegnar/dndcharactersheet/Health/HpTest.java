package nl.tcilegnar.dndcharactersheet.Health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HpTest {
    private static final int DEFAULT_TOTAL_HP = 12;
    private static final int DEFAULT_CURRENT_HP = 4;
    private static final int DEFAULT_TEMP_HP = 2;

    private Storage storageMock;

    @Test
    public void hpDefaultConstructor_NoExceptionThrown() {
        // Arrange

        // Act
        new Hp();

        // Assert
        assertTrue("Er is iets fout gegaan in de constructor", true);
    }

    @Test
    public void save_Default_ValuesSaved() {
        // Arrange
        Hp hp = getDefaultHp();

        // Act
        hp.save();

        // Assert
        verify(storageMock).saveTotalHp(DEFAULT_TOTAL_HP);
        verify(storageMock).saveCurrentHp(DEFAULT_CURRENT_HP);
        verify(storageMock).saveTempHp(DEFAULT_TEMP_HP);
    }

    @Test
    public void setTotal_PositiveValue_GetSameValue() {
        // Arrange
        int expectedTotal = DEFAULT_TOTAL_HP + 1;
        Hp hp = getDefaultHp();

        // Act
        hp.setTotal(expectedTotal);

        // Assert
        assertEquals(expectedTotal, hp.getTotal());
    }

    @Test(expected = Hp.TotalHpTooLowException.class)
    public void setTotal_ZeroValue_TotalHpTooLowException() {
        // Arrange
        int expectedTotal = 0;
        Hp hp = getDefaultHp();

        // Act
        hp.setTotal(expectedTotal);

        // Assert
    }

    @Test(expected = Hp.TotalHpTooLowException.class)
    public void setTotal_NegativeValue_TotalHpTooLowException() {
        // Arrange
        int expectedTotal = -1;
        Hp hp = getDefaultHp();

        // Act
        hp.setTotal(expectedTotal);

        // Assert
    }

    @Test
    public void setTotal_IncreasedValue_CurrentIncreasedWithSameAmount() {
        // Arrange
        int increasedTotalValue = 2;
        int newTotal = DEFAULT_TOTAL_HP + increasedTotalValue;
        Hp hp = getDefaultHp();
        int previousCurrent = hp.getCurrent();

        // Act
        hp.setTotal(newTotal);

        // Assert
        int expectedCurrent = previousCurrent + increasedTotalValue;
        assertEquals(expectedCurrent, hp.getCurrent());
    }

    @Test
    public void setTotal_NotChangedValue_CurrentNotChanged() {
        // Arrange
        int increasedTotalValue = 0;
        int newTotal = DEFAULT_TOTAL_HP + increasedTotalValue;
        Hp hp = getDefaultHp();
        int previousCurrent = hp.getCurrent();

        // Act
        hp.setTotal(newTotal);

        // Assert
        assertEquals(previousCurrent, hp.getCurrent());
    }

    @Test
    //TODO: Klopt dit? Wanneer wordt total verlaagd / wanneer komt dit in de praktijk voor?
    public void setTotal_DecreasedValueNotSmallerThanCurrent_CurrentNotChanged() {
        // Arrange
        int newTotal = DEFAULT_TOTAL_HP - 1;
        Hp hp = getDefaultHp();
        int previousCurrent = hp.getCurrent();

        // Act
        hp.setTotal(newTotal);

        // Assert
        assertEquals(previousCurrent, hp.getCurrent());
    }

    @Test
    //TODO: Klopt dit? Wanneer wordt total verlaagd / wanneer komt dit in de praktijk voor?
    public void setTotal_DecreasedValueSmallerThanCurrent_CurrentIsDecreased() {
        // Arrange
        int newTotal = DEFAULT_CURRENT_HP - 1;
        Hp hp = getDefaultHp();

        // Act
        hp.setTotal(newTotal);

        // Assert
        int expectedCurrent = newTotal;
        assertEquals(expectedCurrent, hp.getCurrent());
    }

    @Test(expected = Hp.CurrentHpTooHighException.class)
    public void setCurrent_CurrentBiggerThanTotal_CurrentHpTooHighException() {
        // Arrange
        int expectedCurrent = DEFAULT_TOTAL_HP + 1;
        Hp hp = getDefaultHp();

        // Act
        hp.setCurrent(expectedCurrent);

        // Assert
    }

    @Test
    public void setCurrent_CurrentSameAsTotal_GetSameValue() {
        // Arrange
        int expectedCurrent = DEFAULT_TOTAL_HP;
        Hp hp = getDefaultHp();

        // Act
        hp.setCurrent(expectedCurrent);

        // Assert
        assertEquals(expectedCurrent, hp.getCurrent());
    }

    @Test
    public void setCurrent_CurrentSmallerThanTotal_GetSameValue() {
        // Arrange
        int expectedCurrent = DEFAULT_TOTAL_HP - 1;
        Hp hp = getDefaultHp();

        // Act
        hp.setCurrent(expectedCurrent);

        // Assert
        assertEquals(expectedCurrent, hp.getCurrent());
    }

    @Test
    public void setCurrent_CurrentNegative_GetSameValue() {
        // Arrange
        int expectedCurrent = -1;
        Hp hp = getDefaultHp();

        // Act
        hp.setCurrent(expectedCurrent);

        // Assert
        assertEquals(expectedCurrent, hp.getCurrent());
    }

    @Test
    public void setTemp_PositiveValue_GetSameValue() {
        // Arrange
        int expectedTemp = DEFAULT_TEMP_HP + 1;
        Hp hp = getDefaultHp();

        // Act
        hp.setTemp(expectedTemp);

        // Assert
        assertEquals(expectedTemp, hp.getTemp());
    }

    @Test
    public void setTemp_Zero_GetSameValue() {
        // Arrange
        int expectedTemp = 0;
        Hp hp = getDefaultHp();

        // Act
        hp.setTemp(expectedTemp);

        // Assert
        assertEquals(expectedTemp, hp.getTemp());
    }

    @Test(expected = Hp.TempHpTooLowException.class)
    public void setTemp_NegativeValue_TempHpTooLowException() {
        // Arrange
        int expectedTemp = -1;
        Hp hp = getDefaultHp();

        // Act
        hp.setTemp(expectedTemp);

        // Assert
    }

    @Test
    public void getCurrentComplete_IsCurrentPlusTempHp() {
        // Arrange
        Hp hp = getDefaultHp();

        // Act
        int currentComplete = hp.getCurrentComplete();

        // Assert
        int expectedValue = DEFAULT_CURRENT_HP + DEFAULT_TEMP_HP;
        assertEquals(expectedValue, currentComplete);
    }

    private Hp getDefaultHp() {
        Storage storageMock = getDefaultStorageMock();
        return new Hp(storageMock);
    }

    Storage getDefaultStorageMock() {
        storageMock = mock(Storage.class);
        doReturn(DEFAULT_TOTAL_HP).when(storageMock).loadTotalHp();
        doReturn(DEFAULT_CURRENT_HP).when(storageMock).loadCurrentHp();
        doReturn(DEFAULT_TEMP_HP).when(storageMock).loadTempHp();
        return storageMock;
    }
}