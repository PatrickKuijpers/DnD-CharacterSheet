package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class BronzeEditor extends MoneyEditor {
    public BronzeEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected BronzeEditor(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs, storage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_bronze_editor;
    }

    @Override
    protected int loadMoneyValue() {
        return storage.loadBronze();
    }

    @Override
    protected void saveMoneyValue(int value) {
        storage.saveBronze(value);
    }
}
