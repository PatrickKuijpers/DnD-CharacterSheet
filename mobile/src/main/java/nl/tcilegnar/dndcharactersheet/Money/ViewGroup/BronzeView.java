package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class BronzeView extends MoneyView {
    public BronzeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected BronzeView(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_bronze_view;
    }

    public void save() {
        int value = getMoneyValue();
        new Storage().saveBronze(value);
    }

    @Override
    public int load() {
        return new Storage().loadBronze();
    }
}
