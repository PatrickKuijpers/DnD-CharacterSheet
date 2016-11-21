package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Money.MoneyConstants;
import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class BronzeView extends MoneyView {
    public BronzeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected BronzeView(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs, storage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_bronze_indicator;
    }

    @Override
    protected int getMaxValue() {
        return MoneyConstants.MAX_BRONZE_VALUE;
    }

    @Override
    protected int loadMoneyValue() {
        return storage.loadBronze();
    }

    @Override
    protected void saveMoneyValue(int value) {
        storage.saveBronze(value);
    }

    @Override
    protected int getMoneyValue(MoneyValues moneyValues) {
        return moneyValues.getBronzeValue();
    }
}
