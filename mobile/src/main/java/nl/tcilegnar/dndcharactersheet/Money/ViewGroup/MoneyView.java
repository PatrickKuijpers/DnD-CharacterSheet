package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyIndicatorPicker;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyIndicatorTextView;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public abstract class MoneyView extends LinearLayout {
    protected final Storage storage;

    private MoneyIndicatorTextView moneyIndicatorTextView;
    private MoneyIndicatorPicker moneyIndicatorPicker;

    public MoneyView(Context context, AttributeSet attrs) {
        this(context, attrs, new Storage());
    }

    @VisibleForTesting
    protected MoneyView(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs);
        inflate(context, getLayoutResource(), this);
        this.storage = storage;
        initView();
    }

    protected abstract
    @LayoutRes
    int getLayoutResource();

    protected abstract int loadMoneyValue();

    protected abstract void saveMoneyValue(int value);

    protected abstract int getMoneyValue(MoneyValues moneyValues);

    private void initView() {
        moneyIndicatorTextView = (MoneyIndicatorTextView) findViewById(R.id.money_indicator_text_view);
        moneyIndicatorPicker = (MoneyIndicatorPicker) findViewById(R.id.money_indicator_numberpicker);
    }

    public void updateSettingsData() {
        moneyIndicatorTextView.updateSettingsData();
        moneyIndicatorPicker.updateSettingsData();
    }

    public void load() {
        int moneyValue = loadMoneyValue();
        setMoneyValue(moneyValue);
    }

    public int getMoneyValue() {
        if (moneyIndicatorTextView.getVisibility() == View.VISIBLE) {
            return moneyIndicatorTextView.getMoneyValue();
        } else if (moneyIndicatorPicker.getVisibility() == View.VISIBLE) {
            return moneyIndicatorPicker.getCurrentSelectedNumber();
        } else {
            return 0;
        }
    }

    public void setMoneyValue(MoneyValues moneyValues) {
        int moneyValue = getMoneyValue(moneyValues);
        setMoneyValue(moneyValue);
    }

    public void setMoneyValue(int moneyValue) {
        moneyIndicatorTextView.setMoneyValue(moneyValue);
        moneyIndicatorPicker.setMoneyValue(moneyValue);
        save();
    }

    public void save() {
        int value = getMoneyValue();
        saveMoneyValue(value);
    }
}
