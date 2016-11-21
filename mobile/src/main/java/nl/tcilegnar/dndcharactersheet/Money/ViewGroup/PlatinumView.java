package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Money.MoneyConstants;
import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class PlatinumView extends MoneyView {
    public PlatinumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected PlatinumView(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs, storage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_platinum_indicator;
    }

    @Override
    protected int getMaxValue() {
        return MoneyConstants.MAX_PLATINUM_VALUE;
    }

    @Override
    protected int loadMoneyValue() {
        return storage.loadPlatinum();
    }

    @Override
    protected void saveMoneyValue(int value) {
        storage.savePlatinum(value);
    }

    @Override
    protected int getMoneyValue(MoneyValues moneyValues) {
        return moneyValues.getPlatinumValue();
    }
}
