package nl.tcilegnar.dndcharactersheet.Money;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyCalculator.MAX_BRONZE_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyCalculator.MAX_GOLD_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyCalculator.MAX_PLATINUM_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyCalculator.MAX_SILVER_VALUE;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyCalculatorTest {
    private static final int DEFAULT_PLATINUM = 3;
    private static final int DEFAULT_GOLD = 12;
    private static final int DEFAULT_SILVER = 45;
    private static final int DEFAULT_BRONZE = 98;

    private MoneyCalculator calculator;
    private MoneyValues defaultCurrentValuesMock;
    private MoneyValues newValues;

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
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddBronze() throws Exception {
        // Arrange
        int CHANGED = 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER, DEFAULT_BRONZE + CHANGED);
    }

    @Test
    public void calculateNewMoneyValues_AddSilver() throws Exception {
        // Arrange
        int CHANGED = 3;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER + CHANGED, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddGold() throws Exception {
        // Arrange
        int CHANGED = 11;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD + CHANGED, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddPlatinum() throws Exception {
        // Arrange
        int CHANGED = 1;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM + CHANGED, DEFAULT_GOLD, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddUpToMaxBronze_MaxBronzeConvertedTo1Silver() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_BRONZE_VALUE, DEFAULT_BRONZE);
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 0;
        int expectedSilver = DEFAULT_SILVER + 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_AddUpToMaxSilver_MaxSilverConvertedTo1Gold() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_SILVER_VALUE, DEFAULT_SILVER);
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedSilver = 0;
        int expectedGold = DEFAULT_GOLD + 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddUpToMaxGold_MaxGoldConvertedTo1Platinum() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_GOLD_VALUE, DEFAULT_GOLD);
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedGold = 0;
        int expectedPlatinum = DEFAULT_PLATINUM + 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test(expected = MoneyCalculator.MaxMoneyReachedException.class)
    public void calculateNewMoneyValues_AddUpToMaxPlatinum_MaxMoneyReachedException() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(MAX_PLATINUM_VALUE, DEFAULT_PLATINUM);
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo101Bronze_101BronzeConvertedTo1SilverAnd1Bronze() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(101, DEFAULT_BRONZE);
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 1;
        int expectedSilver = DEFAULT_SILVER + 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo200Bronze_200BronzeConvertedTo2Silver() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(200, DEFAULT_BRONZE);
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 0;
        int expectedSilver = DEFAULT_SILVER + 2;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo300Silver_300SilverConvertedTo3Gold() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(300, DEFAULT_SILVER);
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedSilver = 0;
        int expectedGold = DEFAULT_GOLD + 3;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo500Gold_500GoldConvertedTo5Platinum() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(101, DEFAULT_GOLD);
        MoneyValues changeValues = getMoneyValues(12, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedGold = 1;
        int expectedPlatinum = DEFAULT_PLATINUM + 12 + 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo1000Bronze_1000BronzeConvertedTo100Silver() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(1000, DEFAULT_BRONZE);
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 0;
        int expectedSilver = DEFAULT_SILVER + 10;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_AddUpTo10300Bronze_10300BronzeConvertedTo1GoldAnd3Silver() throws Exception {
        // Arrange
        int CHANGED = getChangedUpTo(10300, DEFAULT_BRONZE);
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 0;
        int expectedSilver = DEFAULT_SILVER + 3;
        int expectedGold = DEFAULT_GOLD + 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void
    calculateNewMoneyValues_AddUpTo19011030Bronze10205Silver101Gold12Platinum_AllTogetherConvertedTo45Platinum4Gold15SilverAnd30Bronze() throws Exception {
        // Arrange
        int CHANGED_BRONZE = getChangedUpTo(19011030, DEFAULT_BRONZE); // +19P +1G +10S +30B
        int CHANGED_SILVER = getChangedUpTo(100205, DEFAULT_SILVER); // +10P +2G +5S
        int CHANGED_GOLD = getChangedUpTo(101, DEFAULT_GOLD); // +1P +1G
        MoneyValues changeValues = getMoneyValues(12, CHANGED_GOLD, CHANGED_SILVER, CHANGED_BRONZE);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = 30;
        int expectedSilver = 5 + 10;
        int expectedGold = 1 + 2 + 1;
        int expectedPlatinum = DEFAULT_PLATINUM + 12 + 1 + 10 + 19;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractBronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE + 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER, DEFAULT_BRONZE + CHANGED);
    }

    @Test
    public void calculateNewMoneyValues_SubstractSilver() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_SILVER + 1;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER + CHANGED, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractGold() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_GOLD + 1;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD + CHANGED, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractPlatinum() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_PLATINUM + 1;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM + CHANGED, DEFAULT_GOLD, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownTo0Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, DEFAULT_SILVER, 0);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Bronze_Convert1SilverTo99Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = DEFAULT_SILVER - 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Silver_Convert1GoldTo99Silver() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_SILVER - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedSilver = MAX_SILVER_VALUE - 1;
        int expectedGold = DEFAULT_GOLD - 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1Gold_Convert1PlatinumTo99Gold() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_GOLD - 1;
        MoneyValues changeValues = getMoneyValues(0, CHANGED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedGold = MAX_GOLD_VALUE - 1;
        int expectedPlatinum = DEFAULT_PLATINUM - 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, DEFAULT_SILVER, DEFAULT_BRONZE);
    }

    @Test(expected = MoneyCalculator.NotEnoughMoneyException.class)
    public void calculateNewMoneyValues_SubstractDownToMinus1Platinum_NotEnoughMoneyException() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_PLATINUM - 1;
        MoneyValues changeValues = getMoneyValues(CHANGED, 0, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoSilverLeft_Convert1GoldTo99SilverAnd99Bronze
            () throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 1;
        int expectedGold = DEFAULT_GOLD - 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoSilverOrGoldLeft_Convert1PlatinumTo99Gold99SilverAnd99Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 1;
        int expectedGold = MAX_GOLD_VALUE - 1;
        int expectedPlatinum = DEFAULT_PLATINUM - 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test(expected = MoneyCalculator.NotEnoughMoneyException.class)
    public void calculateNewMoneyValues_SubstractDownToMinus1BronzeAndNoMoneyLeft_NotEnoughMoneyException() throws
            Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 1;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();
        doReturn(0).when(defaultCurrentValuesMock).getPlatinumValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus101Bronze_Convert2SilverTo99Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = DEFAULT_SILVER - 2;
        assertNewMoneyValues(DEFAULT_PLATINUM, DEFAULT_GOLD, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus101BronzeNoSilverLeft_Convert1GoldTo98SilverAnd99Bronze()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 2;
        int expectedGold = DEFAULT_GOLD - 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus101BronzeNoSilverOrGoldLeft_Convert1PlatinumTo99Gold98SilverAnd99Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 2;
        int expectedGold = MAX_GOLD_VALUE - 1;
        int expectedPlatinum = DEFAULT_PLATINUM - 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus10101Bronze_Convert1GoldEtc() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 10101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = DEFAULT_SILVER - 2;
        int expectedGold = DEFAULT_GOLD - 1;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus10101BronzeNoSilverLeft_Convert2GoldTo98SilverAnd99Bronze
            () throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 10101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 2;
        int expectedGold = DEFAULT_GOLD - 2;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void
    calculateNewMoneyValues_SubstractDownToMinus10101BronzeNoSilverOrGoldLeft_Convert1PlatinumTo98Gold98SilverAnd99Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_BRONZE - 10101;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1;
        int expectedSilver = MAX_SILVER_VALUE - 2;
        int expectedGold = MAX_GOLD_VALUE - 2;
        int expectedPlatinum = DEFAULT_PLATINUM - 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus101Silver_Convert2GoldTo99Silver() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_SILVER - 101;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedSilver = MAX_SILVER_VALUE - 1;
        int expectedGold = DEFAULT_GOLD - 2;
        assertNewMoneyValues(DEFAULT_PLATINUM, expectedGold, expectedSilver, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus101SilverNoGoldLeft_Convert1PlatinumTo98GoldAnd99Silver()
            throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_SILVER - 101;
        MoneyValues changeValues = getMoneyValues(0, 0, CHANGED, 0);
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedSilver = MAX_SILVER_VALUE - 1;
        int expectedGold = MAX_GOLD_VALUE - 2;
        int expectedPlatinum = DEFAULT_PLATINUM - 1;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, DEFAULT_BRONZE);
    }

    @Test
    public void calculateNewMoneyValues_SubstractDownToMinus10101BronzeMinus19SilverMinus3GoldAnd1Platinum___()
            throws Exception {
        // Arrange
        int CHANGED_BRONZE = -DEFAULT_BRONZE - 1120401; // -1P -13G -5S -1B
        int CHANGED_SILVER = -DEFAULT_SILVER - 219; // -2G -19S
        int CHANGED_GOLD = -DEFAULT_GOLD - 3; // -1P -3G
        int CHANGED_PLATINUM = -1;
        MoneyValues changeValues = getMoneyValues(CHANGED_PLATINUM, CHANGED_GOLD, CHANGED_SILVER, CHANGED_BRONZE);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedBronze = MAX_BRONZE_VALUE - 1; // 99
        int expectedSilver = MAX_SILVER_VALUE - 19 - 5; // 76
        int expectedGold = MAX_GOLD_VALUE - 3 - 2 - 13; // 82
        int expectedPlatinum = DEFAULT_PLATINUM - 1 - 1 - 1; // 0
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    public MoneyCalculator getDefaultMoneyCalculator() {
        defaultCurrentValuesMock = mock(MoneyValues.class);
        doReturn(DEFAULT_PLATINUM).when(defaultCurrentValuesMock).getPlatinumValue();
        doReturn(DEFAULT_GOLD).when(defaultCurrentValuesMock).getGoldValue();
        doReturn(DEFAULT_SILVER).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(DEFAULT_BRONZE).when(defaultCurrentValuesMock).getBronzeValue();
        return new MoneyCalculator(defaultCurrentValuesMock);
    }

    public MoneyValues getMoneyValues(int platinumValue, int goldValue, int silverValue, int bronzeValue) {
        return new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
    }

    private int getChangedUpTo(int addedUpTo, int defaultValue) {
        return addedUpTo - defaultValue;
    }

    private void assertNewMoneyValues(int expectedPlatinum, int expectedGold, int expectedSilver, int expectedBronze) {
        MoneyValues expectedValues = getMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
        assertEquals(expectedValues, newValues);
    }
}