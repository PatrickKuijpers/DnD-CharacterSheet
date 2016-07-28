package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class ExperiencePicker extends NumberPicker {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 1000;
    private Settings settings;

    public enum SavedValues {
        SAVED_INSTANCE, CURRENT_PICKER_INDEX
    }

    public ExperiencePicker(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    public ExperiencePicker(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
    }

    private void initViewsIfVisible() {
        boolean shouldBeVisible = settings.isExperienceUpdateTypeNumberPicker();
        if (shouldBeVisible) {
            init();
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    private void init() {
        initPickerValues();
        setDividerColorTransparent();
    }

    private void initPickerValues() {
        String[] displayedValues = generateDisplayedValues();
        super.setDisplayedValues(displayedValues);
        super.setMinValue(MIN_VALUE);
        super.setMaxValue(displayedValues.length - 1);
        super.setValue(0);
    }

    private String[] generateDisplayedValues() {
        int pickerStepSize = settings.getExperiencePickerStepSize();
        int numberOfSteps = ((MAX_VALUE - MIN_VALUE) / pickerStepSize) + 1;
        String[] experienceValues = new String[numberOfSteps];
        int nextValue = MIN_VALUE;
        for (int i = 0; i < experienceValues.length; i++) {
            experienceValues[i] = String.valueOf(nextValue);
            nextValue = nextValue + pickerStepSize;
        }
        return experienceValues;
    }

    private void setDividerColorTransparent() {
        int color = ContextCompat.getColor(App.getContext(), R.color.transparent);

        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(this, colorDrawable);
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
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
