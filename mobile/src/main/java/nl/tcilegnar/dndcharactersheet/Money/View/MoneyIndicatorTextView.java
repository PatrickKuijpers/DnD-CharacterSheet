package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MoneyIndicatorTextView extends TextView {
    private int moneyValue;

    public MoneyIndicatorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMoneyValue(int moneyValue) {
        setText(String.valueOf(moneyValue));
    }

    public int getMoneyValue() {
        return Integer.valueOf(getText().toString());
    }
}
