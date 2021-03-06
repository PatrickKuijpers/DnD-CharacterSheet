package nl.tcilegnar.dndcharactersheet.Money;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

public class MoneyConstants {
    private static final int MAX_PLATINUM_LENGTH = Res.getInt(R.integer.max_length_platinum_value);
    private static final int MAX_GOLD_LENGTH = Res.getInt(R.integer.max_length_gold_value);
    private static final int MAX_SILVER_LENGTH = Res.getInt(R.integer.max_length_silver_value);
    private static final int MAX_BRONZE_LENGTH = Res.getInt(R.integer.max_length_bronze_value);

    public static final int MAX_PLATINUM_VALUE = getMaxMoneyValue(MAX_PLATINUM_LENGTH);
    public static final int MAX_GOLD_VALUE = getMaxMoneyValue(MAX_GOLD_LENGTH);
    public static final int MAX_SILVER_VALUE = getMaxMoneyValue(MAX_SILVER_LENGTH);
    public static final int MAX_BRONZE_VALUE = getMaxMoneyValue(MAX_BRONZE_LENGTH);

    private static int getMaxMoneyValue(int maxLength) {
        return (int) Math.pow(10, maxLength) - 1;
    }
}
