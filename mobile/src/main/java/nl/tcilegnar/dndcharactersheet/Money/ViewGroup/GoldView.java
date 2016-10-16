package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class GoldView extends MoneyView {
    public GoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected GoldView(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs, storage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_gold_indicator;
    }

    @Override
    protected int loadMoneyValue() {
        return storage.loadGold();
    }

    @Override
    protected void saveMoneyValue(int value) {
        storage.saveGold(value);
    }

    @Override
    protected int getMoneyValue(MoneyValues moneyValues) {
        return moneyValues.getGoldValue();
    }
}
