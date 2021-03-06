package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Money.View.MoneyInput;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyPicker;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneySlider;
import nl.tcilegnar.dndcharactersheet.R;

public abstract class MoneyEditor extends LinearLayout {
    private MoneyInput moneyInput;
    private MoneySlider numberSlider;
    private MoneyPicker numberPicker;

    public MoneyEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, getLayoutResource(), this);
        initView();
        initValues();
    }

    protected abstract
    @LayoutRes
    int getLayoutResource();

    private void initView() {
        moneyInput = (MoneyInput) findViewById(R.id.money_edittext);
        numberSlider = (MoneySlider) findViewById(R.id.money_numberslider);
        numberPicker = (MoneyPicker) findViewById(R.id.money_numberpicker);
    }

    private void initValues() {
        // moneyInput.setMoneyValue(0); // Niet invullen, enkel een hint van 0 tonen!
        numberSlider.setMoneyValue(0);
        numberPicker.setMoneyValue(0);

        int maxValue = getMaxValue();
        numberSlider.setMaxMoneyValue(maxValue);
        numberPicker.setMaxMoneyValue(maxValue);
    }

    protected abstract int getMaxValue();

    public void updateSettingsData() {
        moneyInput.updateSettingsData();
        numberSlider.updateSettingsData();
        numberPicker.updateSettingsData();
    }

    public int getMoneyValue() {
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
