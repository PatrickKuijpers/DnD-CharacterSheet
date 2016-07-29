package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberInput;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class MoneyInput extends BaseNumberInput {
    public MoneyInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    public MoneyInput(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    protected boolean shouldBeVisible() {
        return true;
    }

    public void setMoneyValue(int moneyValue) {
        setText(String.valueOf(moneyValue));
    }
}
