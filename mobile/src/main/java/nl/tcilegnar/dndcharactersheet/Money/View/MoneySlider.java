package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneySlider extends MoneyBaseNumberPicker {
    public MoneySlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    protected MoneySlider(Context context, AttributeSet attrs, MoneySettings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected void initView() {
        super.initView();
        setDividerColor(R.color.grey_light);
    }

    @Override
    protected boolean shouldBeVisible() {
        return ((MoneySettings) settings).isMoneyUpdateTypeNumberSlider();
    }

    @Override
    protected int getTextsizeDimenRes() {
        return R.dimen.numberslider_textsize;
    }
}
