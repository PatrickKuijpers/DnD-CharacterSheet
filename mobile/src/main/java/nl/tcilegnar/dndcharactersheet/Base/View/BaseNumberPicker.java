package nl.tcilegnar.dndcharactersheet.Base.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public abstract class BaseNumberPicker extends NumberPicker {
    protected final Settings settings;

    public enum SavedValues {
        SAVED_INSTANCE, CURRENT_PICKER_INDEX
    }

    public <T extends Settings> BaseNumberPicker(Context context, AttributeSet attrs, T settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
    }

    private void initViewsIfVisible() {
        if (shouldBeVisible()) {
            initView();
            showView();
        } else {
            hideView();
        }
    }

    protected void showView() {
        setVisibility(View.VISIBLE);
    }

    protected void hideView() {
        setVisibility(View.GONE);
    }

    protected abstract boolean shouldBeVisible();

    protected void initView() {
        initPickerValues();
    }

    private void initPickerValues() {
        setValue(initialValue());
        setMinValue(minValue());

        String[] displayedValues = generateDisplayedValues();
        int maxValue = displayedValues.length - 1;
        if (maxValue > getMaxValue()) {
            setDisplayedValues(displayedValues);
            setMaxValue(maxValue);
        } else {
            setMaxValue(maxValue);
            setDisplayedValues(displayedValues);
        }
    }

    protected abstract int minValue();

    protected abstract int maxValue();

    protected abstract int initialValue();

    private String[] generateDisplayedValues() {
        int pickerStepSize = getPickerStepSize();
        int numberOfSteps = ((maxValue() - minValue()) / pickerStepSize) + 1;
        String[] experienceValues = new String[numberOfSteps];
        int nextValue = minValue();
        for (int i = 0; i < experienceValues.length; i++) {
            experienceValues[i] = String.valueOf(nextValue);
            nextValue = nextValue + pickerStepSize;
        }
        return experienceValues;
    }

    protected abstract int getPickerStepSize();

    @Override
    public Parcelable onSaveInstanceState() {
        super.onSaveInstanceState();
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

    public int getCurrentSelectedNumber() {
        return Integer.valueOf(getDisplayedValues()[getValue()]);
    }

    protected void setDividerColorTransparent() {
        setDividerColor(R.color.transparent);
    }

    protected void setDividerColor(@ColorRes int colorRes) {
        int color = ContextCompat.getColor(App.getContext(), colorRes);

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
}
