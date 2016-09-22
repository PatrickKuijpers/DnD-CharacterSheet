package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Money.View.MoneyIndicatorTextView;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public abstract class MoneyView extends LinearLayout {
    protected final Storage storage;

    private MoneyIndicatorTextView moneyIndicatorTextView;

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

    private void initView() {
        moneyIndicatorTextView = (MoneyIndicatorTextView) findViewById(R.id.money_indicator_text_view);
    }

    public void load() {
        int moneyValue = loadMoneyValue();
        setMoneyValue(moneyValue);
    }

    private int getMoneyValue() {
        return moneyIndicatorTextView.getMoneyValue();
    }

    private void setMoneyValue(int moneyValue) {
        moneyIndicatorTextView.setMoneyValue(moneyValue);
    }

    public void changeMoneyValue(int moneyValueChange) {
        int currentValue = getMoneyValue();
        int newValue = currentValue + moneyValueChange;
        moneyIndicatorTextView.setMoneyValue(newValue);
    }

    public void save() {
        int value = moneyIndicatorTextView.getMoneyValue();
        saveMoneyValue(value);
    }
}
