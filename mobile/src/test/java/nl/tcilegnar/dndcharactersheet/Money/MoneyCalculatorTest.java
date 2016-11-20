package nl.tcilegnar.dndcharactersheet.Money;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_BRONZE_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_GOLD_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_PLATINUM_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_SILVER_VALUE;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class MoneyCalculatorTest {
    protected static final int DEFAULT_13_PLATINUM = 13;
    protected static final int DEFAULT_2_GOLD = 2;
    protected static final int DEFAULT_5_SILVER = 5;
    protected static final int DEFAULT_7_BRONZE = 7;

    protected static final int CORRECT_OVER_MAX = -10; // Als eenheid over max is gegaan
    protected static final int ADD_EXTRA = 1; // Als de direct lagere eenheid over max is gegaan

    protected MoneyCalculator calculator;
    protected MoneyValues newValues;
    protected MoneyValues defaultCurrentValuesMock;

    @Before
    public void setUp() {
        calculator = getDefaultMoneyCalculator();
    }

    @Test
    public void calculateNewMoneyValues_AddNothing_NewMoneyValuesSameAsCurrentMoneyValues() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddBronze() throws Exception {
        // Arrange
        int CHANGED = 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE + CHANGED);
    }

    @Test
    public void calculateNewMoneyValues_AddSilver() throws Exception {
        // Arrange
        int CHANGED = 3;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER + CHANGED, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddGold() throws Exception {
        // Arrange
        int CHANGED = 1;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + CHANGED, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddPlatinum() throws Exception {
        // Arrange
        int CHANGED = 1;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM + CHANGED, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddUpToMaxPlatinum_MaxPlatinumAndNoMaxMoneyReachedException() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_PLATINUM_VALUE, DEFAULT_13_PLATINUM);
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(MAX_PLATINUM_VALUE, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test(expected = MoneyCalculator.MaxMoneyReachedException.class)
    public void calculateNewMoneyValues_AddOverMaxPlatinum_MaxMoneyReachedException() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_PLATINUM_VALUE + 1, DEFAULT_13_PLATINUM);
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void calculateNewMoneyValues_Add10Bronze_ConvertedTo1Silver() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 10);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER + 1, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add10Silver_ConvertedTo1Gold() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 10, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 1, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add10Gold_ConvertedTo1Platinum() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 10, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM + 1, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add11Bronze_ConvertedTo1SilverAnd1Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 11);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER + 1, DEFAULT_7_BRONZE + 1);
    }

    @Test
    public void calculateNewMoneyValues_Add20Bronze_ConvertedTo2Silver() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 20);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER + 2, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add30Silver_ConvertedTo3Gold() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 30, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 3, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add50Gold_ConvertedTo5Platinum() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 50, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM + 5, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add100Bronze_ConvertedTo1Gold() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 100);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 1, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add1000Bronze_ConvertedTo1Platinum() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 1000);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM + 1, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_Add132Bronze_ConvertedTo1Gold3SilverAnd2Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 132);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 1, DEFAULT_5_SILVER + 3, DEFAULT_7_BRONZE + 2);
    }

    @Test
    public void calculateNewMoneyValues_Add2648Bronze_ConvertedTo2Platinum6Gold5SilverAnd8Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 2648);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM + 2;
        int expectedGold = DEFAULT_2_GOLD + 6 + ADD_EXTRA;
        int expectedSilver = DEFAULT_5_SILVER + 4 + ADD_EXTRA + CORRECT_OVER_MAX;
        int expectedBronze = DEFAULT_7_BRONZE + 8 + CORRECT_OVER_MAX;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void
    calculateNewMoneyValues_Add19113Bronze125Silver11Gold12Platinum_AllTogetherConvertedTo33Platinum4Gold6SilverAnd3Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(12, 11, 125, 19113);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM + 19 + 1 + 1 + 12;
        int expectedGold = DEFAULT_2_GOLD + 1 + 2 + 1 + ADD_EXTRA;
        int expectedSilver = DEFAULT_5_SILVER + 5 + 1 + ADD_EXTRA + CORRECT_OVER_MAX;
        int expectedBronze = DEFAULT_7_BRONZE + 3 + CORRECT_OVER_MAX;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractBronze() throws Exception {
        // Arrange
        int CHANGED = -6;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE + CHANGED);
    }

    @Test
    public void calculateNewMoneyValues_SubstractSilver() throws Exception {
        // Arrange
        int CHANGED = -4;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER + CHANGED, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractGold() throws Exception {
        // Arrange
        int CHANGED = -1;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + CHANGED, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractPlatinum() throws Exception {
        // Arrange
        int CHANGED = -11;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM + CHANGED, DEFAULT_2_GOLD, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownTo0Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER, 0);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Bronze_Convert1SilverToMaxBronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Silver_Convert1GoldToMaxSilver() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_5_SILVER - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 1, MAX_SILVER_VALUE, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Gold_Convert1PlatinumToMaxGold() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_2_GOLD - 1;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM - 1, MAX_GOLD_VALUE, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Test(expected = MoneyCalculator.NotEnoughMoneyException.class)
    public void calculateNewMoneyValues_SubstractDownToMinus1Platinum_NotEnoughMoneyException() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_13_PLATINUM - 1;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoSilverLeft_Convert1GoldToMaxSilverAndMaxBronze() throws
            Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 1, MAX_SILVER_VALUE, MAX_BRONZE_VALUE);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoSilverOrGoldLeft_Convert1PlatinumToMaxGoldMaxSilverAndMaxBronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM - 1, MAX_GOLD_VALUE, MAX_SILVER_VALUE, MAX_BRONZE_VALUE);
    }

    @Test(expected = MoneyCalculator.NotEnoughMoneyException.class)
    public void calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoMoneyLeft_NotEnoughMoneyException() throws
            Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();
        doReturn(0).when(defaultCurrentValuesMock).getPlatinumValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus11Bronze_Convert2SilverTo9Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 11;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD, DEFAULT_5_SILVER - 2, MAX_BRONZE_VALUE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus11BronzeNoSilverLeft_Convert1GoldTo8SilverAnd9Bronze()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 11;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 1, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus11BronzeNoSilverOrGoldLeft_Convert1PlatinumTo9Gold8SilverAnd9Bronze()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 11;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM - 1, MAX_GOLD_VALUE, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus111Bronze_Convert1GoldEtc() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 111;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 1, DEFAULT_5_SILVER - 2, MAX_BRONZE_VALUE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus111BronzeNoSilverLeft_Convert2GoldTo8SilverAnd9Bronze()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 111;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 2, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus111BronzeNoSilverOrGoldLeft_Convert1PlatinumTo8Gold8SilverAnd9Bronze
            () throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 111;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM - 1, MAX_GOLD_VALUE - 1, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus1111BronzeNoSilverOrGoldLeft_Convert2PlatinumTo8Gold8SilverAnd9Bronze
            () throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1111;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM - ADD_EXTRA - 1; // = 13 - 1 - 1 = 11
        int expectedGold = MAX_GOLD_VALUE - CORRECT_OVER_MAX - 11; // = 9 + 10 - 11 = 8
        assertNewMoneyValues(expectedPlatinum, expectedGold, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus11Silver_Convert2GoldToMaxSilver() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_5_SILVER - 11;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD - 2, MAX_SILVER_VALUE, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus11SilverNoGoldLeft_Convert1PlatinumTo8GoldAndMaxSilver()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_5_SILVER - 11;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM - 1, MAX_GOLD_VALUE - 1, MAX_SILVER_VALUE, DEFAULT_7_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1241BronzeMinus29SilverMinus3GoldAnd1Platinum_Etc()
            throws Exception {
        // Arrange
        int CHANGED_PLATINUM = -1;
        int CHANGED_GOLD = -DEFAULT_2_GOLD - 3; // -1P -3G
        int CHANGED_SILVER = -DEFAULT_5_SILVER - 29; // -(1+2)G -9S
        int CHANGED_BRONZE = -DEFAULT_7_BRONZE - 1241;// - 1241; // -1P -3G -5S -1B
        MoneyValues changeValues = getMoneyValues(CHANGED_PLATINUM, CHANGED_GOLD, CHANGED_SILVER, CHANGED_BRONZE);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM - 1 - 1 - 1;
        int expectedGold = MAX_GOLD_VALUE + 1 - 3 - (1 + 2) - ADD_EXTRA - 2;
        int expectedSilver = MAX_SILVER_VALUE + 1 - 9 - (1 + 4) - CORRECT_OVER_MAX;
        int expectedBronze = MAX_BRONZE_VALUE;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    public MoneyCalculator getDefaultMoneyCalculator() {
        defaultCurrentValuesMock = mock(MoneyValues.class);
        doReturn(DEFAULT_13_PLATINUM).when(defaultCurrentValuesMock).getPlatinumValue();
        doReturn(DEFAULT_2_GOLD).when(defaultCurrentValuesMock).getGoldValue();
        doReturn(DEFAULT_5_SILVER).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(DEFAULT_7_BRONZE).when(defaultCurrentValuesMock).getBronzeValue();
        return new MoneyCalculator(defaultCurrentValuesMock);
    }

    public MoneyValues getMoneyValues(int platinumValue, int goldValue, int silverValue, int bronzeValue) {
        return new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
    }

    protected int getChangedUpTo(int addedUpTo, int defaultValue) {
        return addedUpTo - defaultValue;
    }

    protected void assertNewMoneyValues(int expectedPlatinum, int expectedGold, int expectedSilver, int
            expectedBronze) {
        assertEquals(expectedPlatinum, newValues.getPlatinumValue());
        assertEquals(expectedGold, newValues.getGoldValue());
        assertEquals(expectedSilver, newValues.getSilverValue());
        assertEquals(expectedBronze, newValues.getBronzeValue());
    }
}