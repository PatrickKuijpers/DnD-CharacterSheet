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