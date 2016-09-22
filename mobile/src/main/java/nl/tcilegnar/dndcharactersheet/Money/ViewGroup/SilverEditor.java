package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;

public class SilverEditor extends MoneyEditor {
    public SilverEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.money_silver_editor;
    }
}