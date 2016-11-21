package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberPicker;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;

public abstract class MoneyBaseNumberPicker extends BaseNumberPicker {
    private static final int MIN_VALUE = 0;
    private int maxValue;
    private static final int INITIAL_VALUE = 0;
    private static final int PICKER_STEP_SIZE = 1;

    public MoneyBaseNumberPicker(Context context, AttributeSet attrs) {
        this(context, attrs, MoneySettings.getInstance());
    }

    @VisibleForTesting
    protected MoneyBaseNumberPicker(Context context, AttributeSet attrs, MoneySettings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected void initView() {
        // init na setten van maxValue
    }

    @Override
    protected int minValue() {
        return MIN_VALUE;
    }

    @Override
    protected int maxValue() {
        return maxValue;
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

    public void setMaxMoneyValue(int maxMoneyValue) {
        this.maxValue = maxMoneyValue;
        super.initView();
    }
}
