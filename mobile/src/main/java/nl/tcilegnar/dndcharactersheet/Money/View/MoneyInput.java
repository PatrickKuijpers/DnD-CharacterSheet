package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberInput;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;

public class MoneyInput extends BaseNumberInput {
    public MoneyInput(Context context, AttributeSet attrs) {
        this(context, attrs, MoneySettings.getInstance());
    }

    @VisibleForTesting
    protected MoneyInput(Context context, AttributeSet attrs, MoneySettings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected boolean shouldBeVisible() {
        return ((MoneySettings) settings).isMoneyUpdateTypeInput();
    }

    public void setMoneyValue(int moneyValue) {
        setText(String.valueOf(moneyValue));
    }
}
