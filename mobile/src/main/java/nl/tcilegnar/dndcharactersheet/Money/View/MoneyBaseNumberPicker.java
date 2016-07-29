package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberPicker;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class MoneyBaseNumberPicker extends BaseNumberPicker {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;
    private static final int INITIAL_VALUE = 0;
    private static final int PICKER_STEP_SIZE = 1;

    public MoneyBaseNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoneyBaseNumberPicker(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected int minValue() {
        return MIN_VALUE;
    }

    @Override
    protected int maxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int initialValue() {
        return INITIAL_VALUE;
    }

    @Override
    protected int getPickerStepSize() {
        return PICKER_STEP_SIZE;
    }

    public void setMoneyValue(int moneyValue) {
        setValue(moneyValue);
    }
}
