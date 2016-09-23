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
