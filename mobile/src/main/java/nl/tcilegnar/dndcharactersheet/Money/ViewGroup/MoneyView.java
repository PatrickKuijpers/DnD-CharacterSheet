package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Money.View.MoneyInput;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyPicker;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneySlider;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public abstract class MoneyView extends LinearLayout {
    protected final Storage storage;

    private MoneyInput moneyInput;
    private MoneySlider numberSlider;
    private MoneyPicker numberPicker;

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

    protected abstract int getLayoutResource();

    protected abstract int loadMoneyValue();

    protected abstract void saveMoneyValue(int value);

    private void initView() {
        moneyInput = (MoneyInput) findViewById(R.id.money_edittext);
        numberSlider = (MoneySlider) findViewById(R.id.money_numberslider);
        numberPicker = (MoneyPicker) findViewById(R.id.money_numberpicker);
    }

    public void updateSettingsData() {
        moneyInput.updateSettingsData();
        numberSlider.updateSettingsData();
        numberPicker.updateSettingsData();
    }

    public void load() {
        int moneyValue = loadMoneyValue();
        setMoneyValue(moneyValue);
    }

    private void setMoneyValue(int moneyValue) {
        moneyInput.setMoneyValue(moneyValue);
        numberSlider.setMoneyValue(moneyValue);
        numberPicker.setMoneyValue(moneyValue);
    }

    public void save() {
        int value = getMoneyValue();
        saveMoneyValue(value);
    }

    private int getMoneyValue() {
        if (moneyInput.getVisibility() == View.VISIBLE) {
            return moneyInput.getInputNumber();
        } else if (numberSlider.getVisibility() == View.VISIBLE) {
            return numberSlider.getCurrentSelectedNumber();
        } else if (numberPicker.getVisibility() == View.VISIBLE) {
            return numberPicker.getCurrentSelectedNumber();
        } else {
            return 0;
        }
    }
}
