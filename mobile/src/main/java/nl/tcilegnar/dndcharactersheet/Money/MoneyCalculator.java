package nl.tcilegnar.dndcharactersheet.Money;

import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyCalculator {
    protected static final int MAX_BRONZE_VALUE = 100;
    protected static final int MAX_SILVER_VALUE = 100;
    protected static final int MAX_GOLD_VALUE = 100;

    private final MoneyValues currentMoneyValues;

    private int tempCurrentPlatinum;
    private int tempCurrentGold;
    private int tempCurrentSilver;
    private int tempCurrentBronze;

    public MoneyCalculator(MoneyValues currentMoneyValues) {
        this.currentMoneyValues = currentMoneyValues;
    }

    public MoneyValues calculateNewMoneyValues(MoneyValues changeMoneyValues) throws NotEnoughMoneyException {
        tempCurrentBronze = currentMoneyValues.getBronzeValue();
        tempCurrentSilver = currentMoneyValues.getSilverValue();
        tempCurrentGold = currentMoneyValues.getGoldValue();
        tempCurrentPlatinum = currentMoneyValues.getPlatinumValue();

        int changeBronze = changeMoneyValues.getBronzeValue();
        int newBronze = tempCurrentBronze + changeBronze;
        newBronze = correctBronzeValue(newBronze);

        int changeSilver = changeMoneyValues.getSilverValue();
        int newSilver = tempCurrentSilver + changeSilver;
        newSilver = correctSilverValue(newSilver);

        int changeGold = changeMoneyValues.getGoldValue();
        int newGold = tempCurrentGold + changeGold;
        newGold = correctGoldValue(newGold);

        int changePlatinum = changeMoneyValues.getPlatinumValue();
        int newPlatinum = tempCurrentPlatinum + changePlatinum;
        newPlatinum = correctPlatinumValue(newPlatinum);

        return new MoneyValues(newPlatinum, newGold, newSilver, newBronze);
    }

    private int correctBronzeValue(int newBronze) throws NotEnoughMoneyException {
        while (newBronze >= MAX_BRONZE_VALUE) {
            newBronze -= MAX_BRONZE_VALUE;
            tempCurrentSilver++;
            tempCurrentSilver = correctSilverValue(tempCurrentSilver);
        }
        while (newBronze < 0) { // || tempCurrentSilver < 0 || tempCurrentGold < 0
            newBronze += MAX_BRONZE_VALUE;
            tempCurrentSilver--;
            tempCurrentSilver = correctSilverValue(tempCurrentSilver);
        }
        return newBronze;
    }

    private int correctSilverValue(int newSilver) throws NotEnoughMoneyException {
        while (newSilver >= MAX_SILVER_VALUE) {
            newSilver -= MAX_SILVER_VALUE;
            tempCurrentGold++;
            tempCurrentGold = correctGoldValue(tempCurrentGold);
        }
        while (newSilver < 0) { // || tempCurrentGold < 0
            newSilver += MAX_SILVER_VALUE;
            tempCurrentGold--;
            tempCurrentGold = correctGoldValue(tempCurrentGold);
        }
        return newSilver;
    }

    private int correctGoldValue(int newGold) throws NotEnoughMoneyException {
        while (newGold >= MAX_GOLD_VALUE) {
            newGold -= MAX_GOLD_VALUE;
            tempCurrentPlatinum++;
        }
        while (newGold < 0) {
            newGold += MAX_GOLD_VALUE;
            tempCurrentPlatinum--;
            tempCurrentPlatinum = correctPlatinumValue(tempCurrentPlatinum);
        }
        return newGold;
    }

    private int correctPlatinumValue(int newPlatinum) throws NotEnoughMoneyException {
        if (newPlatinum < 0) {
            throw new NotEnoughMoneyException(newPlatinum);
        }
        return newPlatinum;
    }

    protected class NotEnoughMoneyException extends Exception {
        public NotEnoughMoneyException(int newPlatinum) {
            super(App.getAppResources().getString(R.string.not_enough_money_exception));
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
