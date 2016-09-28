package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberPicker;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettings;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceSlider extends BaseNumberPicker {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 1000;
    private static final int INITIAL_VALUE = 0;

    public ExperienceSlider(Context context, AttributeSet attrs) {
        this(context, attrs, ExperienceSettings.getInstance());
    }

    @VisibleForTesting
    protected ExperienceSlider(Context context, AttributeSet attrs, ExperienceSettings settings) {
        super(context, attrs, settings);
    }

    @Override
    protected boolean shouldBeVisible() {
        return ((ExperienceSettings) settings).isExperienceUpdateTypeNumberSlider();
    }

    @Override
    protected void initView() {
        super.initView();
        setDividerColorTransparent();
    }

    @Override
    protected int getTextsizeDimenRes() {
        return R.dimen.textsize_numberslider;
    }

    @Override
    protected int minValue() {
        return MIN_VALUE;
    }

    @Override
    protected int maxValue() {
        return MAX_VALUE;
    }

    @Override
    protected int initialValue() {
        return INITIAL_VALUE;
    }

    @Override
    protected int getPickerStepSize() {
        return ((ExperienceSettings) settings).getExperiencePickerStepSize();
    }
}
