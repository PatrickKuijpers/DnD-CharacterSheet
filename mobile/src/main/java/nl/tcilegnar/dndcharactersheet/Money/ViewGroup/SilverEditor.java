package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class SilverEditor extends MoneyEditor {
    public SilverEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected SilverEditor(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs, storage);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_silver_editor;
    }

    @Override
    protected int loadMoneyValue() {
        return storage.loadSilver();
    }

    @Override
    protected void saveMoneyValue(int value) {
        storage.saveSilver(value);
    }
}