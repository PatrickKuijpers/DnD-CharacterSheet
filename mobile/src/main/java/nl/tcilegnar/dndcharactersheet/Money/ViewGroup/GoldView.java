package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class GoldView extends MoneyView {
    public GoldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected GoldView(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_gold_view;
    }

    public void save() {
        int value = getMoneyValue();
        new Storage().saveGold(value);
    }

    @Override
    public int load() {
        return new Storage().loadGold();
    }
}
