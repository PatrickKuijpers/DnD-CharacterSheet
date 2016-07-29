package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class MoneySlider extends MoneyBaseNumberPicker {
    public MoneySlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    public MoneySlider(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected void init() {
        super.init();
        setDividerColor(R.color.grey_light);
    }

    @Override
    protected boolean shouldBeVisible() {
        return settings.isMoneyUpdateTypeNumberSlider();
    }
}
