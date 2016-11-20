package nl.tcilegnar.dndcharactersheet.Money;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_BRONZE_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_GOLD_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_SILVER_VALUE;
import static org.mockito.Mockito.doReturn;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyCalculatorTest4x0 extends MoneyCalculatorTest {
    private static final int CORRECT_OVER_MAX_GOLD = -100;

    @Override
    public void calculateNewMoneyValues_Add10Gold_ConvertedTo1Platinum() throws Exception {
        calculateNewMoneyValues_Add10Gold_10GoldAddedAndNotConvertedToPlatinum();
    }

    private void calculateNewMoneyValues_Add10Gold_10GoldAddedAndNotConvertedToPlatinum() throws Exception {
        // Arrange
        int ADDED = 10;
        MoneyValues changeValues = getMoneyValues(0, ADDED, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + ADDED, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Override
    public void calculateNewMoneyValues_Add50Gold_ConvertedTo5Platinum() throws Exception {
        calculateNewMoneyValues_Add50Gold_50GoldAddedAndNotConvertedToPlatinum();
    }

    private void calculateNewMoneyValues_Add50Gold_50GoldAddedAndNotConvertedToPlatinum() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 50, 0, 0);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 50, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Override
    public void calculateNewMoneyValues_Add1000Bronze_ConvertedTo1Platinum() throws Exception {
        calculateNewMoneyValues_Add1000Bronze_ConvertedTo10Gold();
    }

    private void calculateNewMoneyValues_Add1000Bronze_ConvertedTo10Gold() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 1000);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        assertNewMoneyValues(DEFAULT_13_PLATINUM, DEFAULT_2_GOLD + 10, DEFAULT_5_SILVER, DEFAULT_7_BRONZE);
    }

    @Override
    public void calculateNewMoneyValues_Add2648Bronze_ConvertedTo2Platinum6Gold5SilverAnd8Bronze() throws Exception {
        calculateNewMoneyValues_Add2648Bronze_ConvertedTo26Gold5SilverAnd8Bronze();
    }

    private void calculateNewMoneyValues_Add2648Bronze_ConvertedTo26Gold5SilverAnd8Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(0, 0, 0, 2648);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedGold = DEFAULT_2_GOLD + 26 + ADD_EXTRA;
        int expectedSilver = DEFAULT_5_SILVER + 4 + ADD_EXTRA + CORRECT_OVER_MAX;
        int expectedBronze = DEFAULT_7_BRONZE + 8 + CORRECT_OVER_MAX;
        assertNewMoneyValues(DEFAULT_13_PLATINUM, expectedGold, expectedSilver, expectedBronze);
    }

    public void
    calculateNewMoneyValues_Add19113Bronze125Silver11Gold12Platinum_AllTogetherConvertedTo33Platinum4Gold6SilverAnd3Bronze() throws Exception {
        calculateNewMoneyValues_Add19113Bronze125Silver11Gold12Platinum_AllTogetherConvertedTo13Platinum114Gold6SilverAnd3Bronze();
    }

    private void
    calculateNewMoneyValues_Add19113Bronze125Silver11Gold12Platinum_AllTogetherConvertedTo13Platinum114Gold6SilverAnd3Bronze() throws Exception {
        // Arrange
        MoneyValues changeValues = getMoneyValues(12, 11, 125, 19113);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM + 12 + 1 + ADD_EXTRA;
        int expectedGold = DEFAULT_2_GOLD + 11 + 12 + 91 + ADD_EXTRA + CORRECT_OVER_MAX_GOLD;
        int expectedSilver = DEFAULT_5_SILVER + 5 + 1 + ADD_EXTRA + CORRECT_OVER_MAX;
        int expectedBronze = DEFAULT_7_BRONZE + 3 + CORRECT_OVER_MAX;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }

    @Override
    public void
    calculateNewMoneyValues_SubstractDownToMinus1111BronzeNoSilverOrGoldLeft_Convert2PlatinumTo8Gold8SilverAnd9Bronze
            () throws Exception {
        calculateNewMoneyValues_SubstractDownToMinus1111BronzeNoSilverOrGoldLeft_Convert1PlatinumTo88Gold8SilverAnd9Bronze();
    }

    private void
    calculateNewMoneyValues_SubstractDownToMinus1111BronzeNoSilverOrGoldLeft_Convert1PlatinumTo88Gold8SilverAnd9Bronze() throws Exception {
        // Arrange
        int CHANGED = -DEFAULT_7_BRONZE - 1111;
        MoneyValues changeValues = getMoneyValues(0, 0, 0, CHANGED);
        doReturn(0).when(defaultCurrentValuesMock).getSilverValue();
        doReturn(0).when(defaultCurrentValuesMock).getGoldValue();

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM - 1; // = 13 - 1 = 12
        int expectedGold = MAX_GOLD_VALUE - 11; // = 99 - 11 = 88
        assertNewMoneyValues(expectedPlatinum, expectedGold, MAX_SILVER_VALUE - 1, MAX_BRONZE_VALUE);
    }

    @Override
    public void calculateNewMoneyValues_SubstractDownToMinus1241BronzeMinus29SilverMinus3GoldAnd1Platinum_Etc()
            throws Exception {
        calculateNewMoneyValues_SubstractDownToEtc_EtcMinus12GoldInsteadOfMinus1Platinum();
    }

    private void calculateNewMoneyValues_SubstractDownToEtc_EtcMinus12GoldInsteadOfMinus1Platinum() throws Exception {
        // Arrange
        int CHANGED_PLATINUM = -1;
        int CHANGED_GOLD = -DEFAULT_2_GOLD - 3;
        int CHANGED_SILVER = -DEFAULT_5_SILVER - 29;
        int CHANGED_BRONZE = -DEFAULT_7_BRONZE - 1241;
        MoneyValues changeValues = getMoneyValues(CHANGED_PLATINUM, CHANGED_GOLD, CHANGED_SILVER, CHANGED_BRONZE);

        // Act
        newValues = calculator.calculateNewMoneyValues(changeValues);

        // Assert
        int expectedPlatinum = DEFAULT_13_PLATINUM - 1 - 1;
        int expectedGold = MAX_GOLD_VALUE + 1 - 3 - (1 + 2) - ADD_EXTRA - 12;
        int expectedSilver = MAX_SILVER_VALUE + 1 - 9 - (1 + 4) - CORRECT_OVER_MAX;
        int expectedBronze = MAX_BRONZE_VALUE;
        assertNewMoneyValues(expectedPlatinum, expectedGold, expectedSilver, expectedBronze);
    }
}