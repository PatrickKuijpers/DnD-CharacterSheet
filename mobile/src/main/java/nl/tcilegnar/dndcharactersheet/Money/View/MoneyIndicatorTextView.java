package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MoneyIndicatorTextView extends TextView {
    public MoneyIndicatorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getMoneyValue() {
        String s = getText().toString();
        if (s.length() > 0) {
            return Integer.valueOf(s);
        } else {
            return 0;
        }
    }

    public void setMoneyValue(int moneyValue) {
        setText(String.valueOf(moneyValue));
    }
}
