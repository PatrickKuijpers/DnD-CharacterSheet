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
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class MoneyView extends LinearLayout {
    private final Settings settings;

    private MoneyInput moneyInput;
    private MoneySlider numberSlider;
    private MoneyPicker numberPicker;

    public MoneyView(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    @VisibleForTesting
    protected MoneyView(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        inflate(context, getLayoutResource(), this);
        this.settings = settings;
        init();
    }

    private void init() {
        moneyInput = (MoneyInput) findViewById(R.id.money_edittext);
        numberSlider = (MoneySlider) findViewById(R.id.money_numberslider);
        numberPicker = (MoneyPicker) findViewById(R.id.money_numberpicker);

        initValues();
    }

    private void initValues() {
        int moneyValue = load();
        moneyInput.setMoneyValue(moneyValue);
        numberSlider.setMoneyValue(moneyValue);
        numberPicker.setMoneyValue(moneyValue);
    }

    protected abstract int getLayoutResource();

    public void updateSettingsData() {
        moneyInput.updateSettingsData();
        numberSlider.updateSettingsData();
        numberPicker.updateSettingsData();
    }

    protected int getMoneyValue() {
        if (moneyInput.getVisibility() == View.VISIBLE) {
            return moneyInput.getInputNumber();
        } else if (numberSlider.getVisibility() == View.VISIBLE) {
            return numberSlider.getCurrentSelectedMoneyValue();
        } else if (numberPicker.getVisibility() == View.VISIBLE) {
            return numberPicker.getCurrentSelectedMoneyValue();
        }
        return 0;
    }

    public abstract int load();
}
