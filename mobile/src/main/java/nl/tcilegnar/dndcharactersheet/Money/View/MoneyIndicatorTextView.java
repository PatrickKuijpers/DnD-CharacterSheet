package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneyIndicatorTextView extends TextView {
    public final Settings settings;

    public MoneyIndicatorTextView(Context context, AttributeSet attrs) {
        this(context, attrs, MoneySettings.getInstance());
    }

    @VisibleForTesting
    public MoneyIndicatorTextView(Context context, AttributeSet attrs, MoneySettings settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
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

    public void updateSettingsData() {
        initViewsIfVisible();
    }

    private void initViewsIfVisible() {
        if (shouldBeVisible()) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    private boolean shouldBeVisible() {
        return !((MoneySettings) settings).isMoneyUpdateManual();
    }
}
