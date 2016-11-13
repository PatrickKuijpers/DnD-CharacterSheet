package nl.tcilegnar.dndcharactersheet.Money;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyConstants {
    private static final int MAX_MONEY_LENGTH = App.getResourceInteger(R.integer.max_length_money_value);
    private static final int MAX_PLATINUM_LENGTH = App.getResourceInteger(R.integer.max_length_platinum_value);

    public static final int MAX_MONEY_VALUE = getMaxMoneyValue();
    public static final int MAX_BRONZE_VALUE = MAX_MONEY_VALUE;
    public static final int MAX_SILVER_VALUE = MAX_MONEY_VALUE;
    public static final int MAX_GOLD_VALUE = MAX_MONEY_VALUE;
    public static final int MAX_PLATINUM_VALUE = getMaxPlatinumValue();

    private static int getMaxMoneyValue() {
        return (int) Math.pow(10, MAX_MONEY_LENGTH) - 1;
    }

    private static int getMaxPlatinumValue() {
        return (int) Math.pow(10, MAX_PLATINUM_LENGTH) - 1;
    }
}
