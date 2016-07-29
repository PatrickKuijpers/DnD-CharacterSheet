package nl.tcilegnar.dndcharactersheet.Money.View;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class MoneyPicker extends NumberPicker {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;
    private Settings settings;

    public enum SavedValues {
        SAVED_INSTANCE, CURRENT_PICKER_INDEX
    }

    public MoneyPicker(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    public MoneyPicker(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
    }

    private void initViewsIfVisible() {
        //        boolean shouldBeVisible = settings.;
        boolean shouldBeVisible = true;
        if (shouldBeVisible) {
            init();
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    private void init() {
        initPickerValues();
    }

    private void initPickerValues() {
        String[] displayedValues = generateDisplayedValues();
        super.setDisplayedValues(displayedValues);
        super.setMinValue(MIN_VALUE);
        super.setMaxValue(displayedValues.length - 1);
        super.setValue(0);
    }

    private String[] generateDisplayedValues() {
        int pickerStepSize = 1; //settings.getExperiencePickerStepSize();
        int numberOfSteps = ((MAX_VALUE - MIN_VALUE) / pickerStepSize) + 1;
        String[] experienceValues = new String[numberOfSteps];
        int nextValue = MIN_VALUE;
        for (int i = 0; i < experienceValues.length; i++) {
            experienceValues[i] = String.valueOf(nextValue);
            nextValue = nextValue + pickerStepSize;
        }
        return experienceValues;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SavedValues.SAVED_INSTANCE.name(), super.onSaveInstanceState());
        bundle.putInt(SavedValues.CURRENT_PICKER_INDEX.name(), getValue());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setValue(bundle.getInt(SavedValues.CURRENT_PICKER_INDEX.name()));
            state = bundle.getParcelable(SavedValues.SAVED_INSTANCE.name());
        }
        super.onRestoreInstanceState(state);
    }

    public void updateSettingsData() {
        initViewsIfVisible();
    }

    public int getCurrentSelectedExpValue() {
        return Integer.valueOf(getDisplayedValues()[getValue()]);
    }
}
