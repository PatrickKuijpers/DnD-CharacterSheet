package nl.tcilegnar.dndcharactersheet.Money;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyValuesTest {
    private static final int PLATINUM_VALUE = 1;
    private static final int GOLD_VALUE = 12;
    private static final int SILVER_VALUE = 45;
    private static final int BRONZE_VALUE = 99;
    private static final int HIGHEST_VALUE = 99;

    private MoneyValues moneyValues;

    @Before
    public void setUp() {
        moneyValues = new MoneyValues(PLATINUM_VALUE, GOLD_VALUE, SILVER_VALUE, BRONZE_VALUE);
    }

    @Test
    public void getPlatinumValue() {
        // Arrange

        // Act
        int platinumValue = moneyValues.getPlatinumValue();

        // Assert
        assertEquals(PLATINUM_VALUE, platinumValue);
    }

    @Test
    public void getGoldValue() {
        // Arrange

        // Act
        int goldValue = moneyValues.getGoldValue();

        // Assert
        assertEquals(GOLD_VALUE, goldValue);
    }

    @Test
    public void getSilverValue() {
        // Arrange

        // Act
        int silverValue = moneyValues.getSilverValue();

        // Assert
        assertEquals(SILVER_VALUE, silverValue);
    }

    @Test
    public void getBronzeValue() {
        // Arrange

        // Act
        int bronzeValue = moneyValues.getBronzeValue();

        // Assert
        assertEquals(BRONZE_VALUE, bronzeValue);
    }

    @Test
    public void isMoneyIncrease_AllValuesZero() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 0);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertFalse("Alle moneyvalues 0  betekent geen verandering", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_PositivePlatinumLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(1, -HIGHEST_VALUE, -HIGHEST_VALUE, -HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertTrue("1 platinum zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_NegativePlatinumLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(-1, HIGHEST_VALUE, HIGHEST_VALUE, HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertFalse("1 platinum zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_PositiveGoldLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 1, -HIGHEST_VALUE, -HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertTrue("1 goud zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_NegativeGoldLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, -1, HIGHEST_VALUE, HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertFalse("1 goud zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_PositiveSilverLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 1, -HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertTrue("1 silver zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_NegativeSilverLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, -1, HIGHEST_VALUE);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertFalse("1 silver zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_PositiveBronze() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 1);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertTrue("1 brons zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyIncrease_NegativeBronze() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, -1);

        // Act
        boolean isMoneyIncrease = moneyValues.isMoneyIncrease();

        // Assert
        assertFalse("1 brons zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyIncrease);
    }

    @Test
    public void isMoneyDecrease_AllValuesZero() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 0);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertFalse("Alle moneyvalues 0  betekent geen verandering", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_PositivePlatinumLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(1, -HIGHEST_VALUE, -HIGHEST_VALUE, -HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertFalse("1 platinum zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_NegativePlatinumLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(-1, HIGHEST_VALUE, HIGHEST_VALUE, HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertTrue("1 platinum zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_PositiveGoldLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 1, -HIGHEST_VALUE, -HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertFalse("1 goud zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_NegativeGoldLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, -1, HIGHEST_VALUE, HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertTrue("1 goud zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_PositiveSilverLowerValuesNegative() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 1, -HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertFalse("1 silver zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_NegativeSilverLowerValuesPositive() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, -1, HIGHEST_VALUE);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertTrue("1 silver zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_PositiveBronze() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 1);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertFalse("1 brons zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isMoneyDecrease_NegativeBronze() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, -1);

        // Act
        boolean isMoneyDecrease = moneyValues.isMoneyDecrease();

        // Assert
        assertTrue("1 brons zou altijd meer moeten zijn dan alle waarden eronder bij elkaar", isMoneyDecrease);
    }

    @Test
    public void isSingleCoinChanged_SinglePositiveCoinOfAnyType() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(1, 0, 0, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, 1, 0, 0);
        MoneyValues moneyValues3 = new MoneyValues(0, 0, 1, 0);
        MoneyValues moneyValues4 = new MoneyValues(0, 0, 0, 1);

        // Act
        boolean isSingleCoinChanged1 = moneyValues1.isSingleCoinChanged();
        boolean isSingleCoinChanged2 = moneyValues2.isSingleCoinChanged();
        boolean isSingleCoinChanged3 = moneyValues3.isSingleCoinChanged();
        boolean isSingleCoinChanged4 = moneyValues4.isSingleCoinChanged();

        // Assert
        assertTrue(isSingleCoinChanged1);
        assertTrue(isSingleCoinChanged2);
        assertTrue(isSingleCoinChanged3);
        assertTrue(isSingleCoinChanged4);
    }

    @Test
    public void isSingleCoinChanged_SingleNegativeCoinOfAnyType() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(-1, 0, 0, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, -1, 0, 0);
        MoneyValues moneyValues3 = new MoneyValues(0, 0, -1, 0);
        MoneyValues moneyValues4 = new MoneyValues(0, 0, 0, -1);

        // Act
        boolean isSingleCoinChanged1 = moneyValues1.isSingleCoinChanged();
        boolean isSingleCoinChanged2 = moneyValues2.isSingleCoinChanged();
        boolean isSingleCoinChanged3 = moneyValues3.isSingleCoinChanged();
        boolean isSingleCoinChanged4 = moneyValues4.isSingleCoinChanged();

        // Assert
        assertTrue(isSingleCoinChanged1);
        assertTrue(isSingleCoinChanged2);
        assertTrue(isSingleCoinChanged3);
        assertTrue(isSingleCoinChanged4);
    }

    @Test
    public void isSingleCoinChanged_SeveralCoinsOfAnyType() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(1, 1, 0, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, -1, -1, 0);
        MoneyValues moneyValues3 = new MoneyValues(0, 0, 1, -1);
        MoneyValues moneyValues4 = new MoneyValues(19, 1, 0, 5);

        // Act
        boolean isSingleCoinChanged = moneyValues.isSingleCoinChanged();
        boolean isSingleCoinChanged1 = moneyValues1.isSingleCoinChanged();
        boolean isSingleCoinChanged2 = moneyValues2.isSingleCoinChanged();
        boolean isSingleCoinChanged3 = moneyValues3.isSingleCoinChanged();
        boolean isSingleCoinChanged4 = moneyValues4.isSingleCoinChanged();

        // Assert
        assertFalse(isSingleCoinChanged);
        assertFalse(isSingleCoinChanged1);
        assertFalse(isSingleCoinChanged2);
        assertFalse(isSingleCoinChanged3);
        assertFalse(isSingleCoinChanged4);
    }

    @Test
    public void isSingleCoinChanged_NoCoinsChanged() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 0);

        // Act
        boolean isSingleCoinChanged = moneyValues.isSingleCoinChanged();

        // Assert
        assertFalse(isSingleCoinChanged);
    }

    @Test
    public void isHighValueCoinChanged_SinglePlatinumOrGoldPositive_PlatinumAndGoldAreConsideredHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(1, 0, 0, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, 1, 0, 0);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();

        // Assert
        assertTrue(isHighValueCoinChanged1);
        assertTrue(isHighValueCoinChanged2);
    }

    @Test
    public void isHighValueCoinChanged_SinglePlatinumOrGoldNegative_PlatinumAndGoldAreConsideredHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(-1, 0, 0, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, -1, 0, 0);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();

        // Assert
        assertTrue(isHighValueCoinChanged1);
        assertTrue(isHighValueCoinChanged2);
    }

    @Test
    public void isHighValueCoinChanged_SingleSilverOrBronzePositive_SilverAndBronzeAreNotConsideredHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(0, 0, 1, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, 0, 0, 1);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();

        // Assert
        assertFalse(isHighValueCoinChanged1);
        assertFalse(isHighValueCoinChanged2);
    }

    @Test
    public void isHighValueCoinChanged_SingleSilverOrBronzeNegative_SilverAndBronzeAreNotConsideredHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(0, 0, -1, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, 0, 0, -1);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();

        // Assert
        assertFalse(isHighValueCoinChanged1);
        assertFalse(isHighValueCoinChanged2);
    }

    @Test
    public void
    isHighValueCoinChanged_SeveralCoinsIncludingGoldOrPlatinumChanged_MinimumOneGoldOrPlatinumIsConsiderenHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(1, 0, -1, 0);
        MoneyValues moneyValues2 = new MoneyValues(0, 2, 0, -5);
        MoneyValues moneyValues3 = new MoneyValues(3, 5, 52, -11);
        MoneyValues moneyValues4 = new MoneyValues(-32, -4, 44, -1);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();
        boolean isHighValueCoinChanged3 = moneyValues3.isHighValueCoinChanged();
        boolean isHighValueCoinChanged4 = moneyValues4.isHighValueCoinChanged();

        // Assert
        assertTrue(isHighValueCoinChanged1);
        assertTrue(isHighValueCoinChanged2);
        assertTrue(isHighValueCoinChanged3);
        assertTrue(isHighValueCoinChanged4);
    }

    @Test
    public void
    isHighValueCoinChanged_SeveralCoinsExcludingGoldOrPlatinumChanged_OnlySilverOrBronzeIsNotConsiderenHighValue() {
        // Arrange
        MoneyValues moneyValues1 = new MoneyValues(0, 0, 1, 14);
        MoneyValues moneyValues2 = new MoneyValues(0, 0, -12, -5);
        MoneyValues moneyValues3 = new MoneyValues(0, 0, 52, -11);

        // Act
        boolean isHighValueCoinChanged1 = moneyValues1.isHighValueCoinChanged();
        boolean isHighValueCoinChanged2 = moneyValues2.isHighValueCoinChanged();
        boolean isHighValueCoinChanged3 = moneyValues3.isHighValueCoinChanged();

        // Assert
        assertFalse(isHighValueCoinChanged1);
        assertFalse(isHighValueCoinChanged2);
        assertFalse(isHighValueCoinChanged3);
    }

    @Test
    public void isHighValueCoinChanged_NoCoinsChanged() {
        // Arrange
        MoneyValues moneyValues = new MoneyValues(0, 0, 0, 0);

        // Act
        boolean isHighValueCoinChanged = moneyValues.isHighValueCoinChanged();

        // Assert
        assertFalse(isHighValueCoinChanged);
    }

    @Test
    public void equals_SameValues_IsEqual() {
        // Arrange
        MoneyValues compareMoneyValues = new MoneyValues(PLATINUM_VALUE, GOLD_VALUE, SILVER_VALUE, BRONZE_VALUE);

        // Act
        boolean isEqual = moneyValues.equals(compareMoneyValues);

        // Assert
        assertTrue("", isEqual);
    }

    @Test
    public void equals_DifferentPlatinumValue_IsNotEqual() {
        // Arrange
        MoneyValues compareMoneyValues = new MoneyValues(PLATINUM_VALUE + 1, GOLD_VALUE, SILVER_VALUE, BRONZE_VALUE);

        // Act
        boolean isEqual = moneyValues.equals(compareMoneyValues);

        // Assert
        int currentPlatinum = moneyValues.getPlatinumValue();
        int comparePlatinum = compareMoneyValues.getPlatinumValue();
        assertFalse("Platinum waarden zijn niet verschillend: " + currentPlatinum + " & " + comparePlatinum, isEqual);
    }

    @Test
    public void equals_DifferentGoldValue_IsNotEqual() {
        // Arrange
        MoneyValues compareMoneyValues = new MoneyValues(PLATINUM_VALUE, GOLD_VALUE + 1, SILVER_VALUE, BRONZE_VALUE);

        // Act
        boolean isEqual = moneyValues.equals(compareMoneyValues);

        // Assert
        int currentGold = moneyValues.getGoldValue();
        int compareGold = compareMoneyValues.getGoldValue();
        assertFalse("Gold waarden zijn niet verschillend: " + currentGold + " & " + compareGold, isEqual);
    }

    @Test
    public void equals_DifferentSilverValue_IsNotEqual() {
        // Arrange
        MoneyValues compareMoneyValues = new MoneyValues(PLATINUM_VALUE, GOLD_VALUE, SILVER_VALUE + 1, BRONZE_VALUE);

        // Act
        boolean isEqual = moneyValues.equals(compareMoneyValues);

        // Assert
        int currentSilver = moneyValues.getSilverValue();
        int compareSilver = compareMoneyValues.getSilverValue();
        assertFalse("Silver waarden zijn niet verschillend: " + currentSilver + " & " + compareSilver, isEqual);
    }

    @Test
    public void equals_DifferentBronzeValue_IsNotEqual() {
        // Arrange
        MoneyValues compareMoneyValues = new MoneyValues(PLATINUM_VALUE, GOLD_VALUE, SILVER_VALUE, BRONZE_VALUE + 1);

        // Act
        boolean isEqual = moneyValues.equals(compareMoneyValues);

        // Assert
        int currentBronze = moneyValues.getBronzeValue();
        int compareBronze = compareMoneyValues.getBronzeValue();
        assertFalse("Bronze waarden zijn niet verschillend: " + currentBronze + " & " + compareBronze, isEqual);
    }

    @Test
    public void equals_DifferentObject_IsNotEqual() {
        // Arrange
        Exception compareObject = new Exception();

        // Act
        boolean isEqual = moneyValues.equals(compareObject);

        // Assert
        assertFalse("Verschillende objecten zijn gek genoeg gelijk...", isEqual);
    }
}