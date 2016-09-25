package nl.tcilegnar.dndcharactersheet.Money;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.Exceptions.CustomToastException;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyCalculator {
    protected static final int MAX_BRONZE_VALUE = 100;
    protected static final int MAX_SILVER_VALUE = 100;
    protected static final int MAX_GOLD_VALUE = 100;
    protected static final int MAX_PLATINUM_VALUE = (int) Math.pow(10, App.getAppResources().getInteger(R.integer
            .max_lenght_platinum_value));

    private final MoneyValues currentMoneyValues;

    private int tempCurrentPlatinum;
    private int tempCurrentGold;
    private int tempCurrentSilver;
    private int tempCurrentBronze;

    public MoneyCalculator(MoneyValues currentMoneyValues) {
        this.currentMoneyValues = currentMoneyValues;
    }

    public MoneyValues calculateNewMoneyValues(MoneyValues changeMoneyValues) throws MaxMoneyReachedException,
            NotEnoughMoneyException {
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

    private int correctBronzeValue(int newBronze) throws MaxMoneyReachedException, NotEnoughMoneyException {
        while (newBronze >= MAX_BRONZE_VALUE) {
            newBronze -= MAX_BRONZE_VALUE;
            tempCurrentSilver++;
            tempCurrentSilver = correctSilverValue(tempCurrentSilver);
        }
        while (newBronze < 0) {
            newBronze += MAX_BRONZE_VALUE;
            tempCurrentSilver--;
            tempCurrentSilver = correctSilverValue(tempCurrentSilver);
        }
        return newBronze;
    }

    private int correctSilverValue(int newSilver) throws MaxMoneyReachedException, NotEnoughMoneyException {
        while (newSilver >= MAX_SILVER_VALUE) {
            newSilver -= MAX_SILVER_VALUE;
            tempCurrentGold++;
            tempCurrentGold = correctGoldValue(tempCurrentGold);
        }
        while (newSilver < 0) {
            newSilver += MAX_SILVER_VALUE;
            tempCurrentGold--;
            tempCurrentGold = correctGoldValue(tempCurrentGold);
        }
        return newSilver;
    }

    private int correctGoldValue(int newGold) throws MaxMoneyReachedException, NotEnoughMoneyException {
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

    private int correctPlatinumValue(int newPlatinum) throws MaxMoneyReachedException, NotEnoughMoneyException {
        if (newPlatinum >= MAX_PLATINUM_VALUE) {
            throw new MaxMoneyReachedException(newPlatinum);
        }
        if (newPlatinum < 0) {
            throw new NotEnoughMoneyException();
        }
        return newPlatinum;
    }

    protected class MaxMoneyReachedException extends CustomToastException {
        public MaxMoneyReachedException(int newPlatinum) {
            super(String.format(App.getAppResources().getString(R.string.max_money_reached_exception), newPlatinum));
        }
    }

    protected class NotEnoughMoneyException extends CustomToastException {
        public NotEnoughMoneyException() {
            super(App.getAppResources().getString(R.string.not_enough_money_exception));
        }
    }
}