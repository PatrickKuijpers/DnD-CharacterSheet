package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class MoneyInput extends EditText {
    private final Settings settings;

    public MoneyInput(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    public MoneyInput(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
    }

    private void initViewsIfVisible() {
        //        boolean shouldBeVisible = settings.;
        boolean shouldBeVisible = true;
        if (shouldBeVisible) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public void updateSettingsData() {
        initViewsIfVisible();
    }

    public int getMoneyValue() {
        int expValue = 0;
        if (hasInput()) {
            String expValueInput = getInputText();
            expValue = Integer.valueOf(expValueInput);
        }
        return expValue;
    }

    public boolean hasInput() {
        return !getInputText().isEmpty();
    }

    private String getInputText() {
        return getText().toString();
    }
}
