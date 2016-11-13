package nl.tcilegnar.dndcharactersheet.Money;

public class MoneyValues {
    private final int platinumValue;
    private final int goldValue;
    private final int silverValue;
    private final int bronzeValue;

    public MoneyValues(int platinumValue, int goldValue, int silverValue, int bronzeValue) {
        this.platinumValue = platinumValue;
        this.goldValue = goldValue;
        this.silverValue = silverValue;
        this.bronzeValue = bronzeValue;
    }

    public int getPlatinumValue() {
        return platinumValue;
    }

    public int getGoldValue() {
        return goldValue;
    }

    public int getSilverValue() {
        return silverValue;
    }

    public int getBronzeValue() {
        return bronzeValue;
    }

    public boolean isMoneyIncrease() {
        boolean isMoneyIncrease = false;
        if (platinumValue != 0) {
            isMoneyIncrease = platinumValue > 0;
        } else if (goldValue != 0) {
            isMoneyIncrease = goldValue > 0;
        } else if (silverValue != 0) {
            isMoneyIncrease = silverValue > 0;
        } else if (bronzeValue != 0) {
            isMoneyIncrease = bronzeValue > 0;
        }
        return isMoneyIncrease;
    }

    public boolean isMoneyDecrease() {
        return !isMoneyIncrease() && !isMoneyNotChanged();
    }

    private boolean isMoneyNotChanged() {
        return equals(new MoneyValues(0, 0, 0, 0));
    }

    public boolean isSingleCoinChanged() {
        return equals(new MoneyValues(-1, 0, 0, 0)) ||
                equals(new MoneyValues(0, -1, 0, 0)) ||
                equals(new MoneyValues(0, 0, -1, 0)) ||
                equals(new MoneyValues(0, 0, 0, -1)) ||
                equals(new MoneyValues(1, 0, 0, 0)) ||
                equals(new MoneyValues(0, 1, 0, 0)) ||
                equals(new MoneyValues(0, 0, 1, 0)) ||
                equals(new MoneyValues(0, 0, 0, 1));
    }

    public boolean isHighValueCoinChanged() {
        return platinumValue != 0 || goldValue != 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MoneyValues) {
            MoneyValues moneyValues = (MoneyValues) obj;
            boolean isSamePlatinumValue = moneyValues.getPlatinumValue() == getPlatinumValue();
            boolean isSameGoldValue = moneyValues.getGoldValue() == getGoldValue();
            boolean isSameSilverValue = moneyValues.getSilverValue() == getSilverValue();
            boolean isSameBronzeValue = moneyValues.getBronzeValue() == getBronzeValue();
            return isSamePlatinumValue && isSameGoldValue && isSameSilverValue && isSameBronzeValue;
        } else {
            return false;
        }
    }
}
