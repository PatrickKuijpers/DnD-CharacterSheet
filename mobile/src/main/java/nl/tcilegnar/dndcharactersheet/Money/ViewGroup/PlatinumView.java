package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class PlatinumView extends MoneyView {
    public PlatinumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected PlatinumView(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_platinum_view;
    }

    public void save() {
        int value = getValue();
        new Storage().savePlatinum(value);
    }

    @Override
    public int load() {
        return new Storage().loadPlatinum();
    }
}
