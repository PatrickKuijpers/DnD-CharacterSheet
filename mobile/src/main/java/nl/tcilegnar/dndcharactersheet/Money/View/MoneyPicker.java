package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class MoneyPicker extends MoneyBaseNumberPicker {
    public MoneyPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    public MoneyPicker(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected boolean shouldBeVisible() {
        return settings.isMoneyUpdateTypeNumberPicker();
    }
}
