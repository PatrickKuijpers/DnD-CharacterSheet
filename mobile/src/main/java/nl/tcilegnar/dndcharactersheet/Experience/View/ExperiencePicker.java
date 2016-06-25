package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.SharedPreference.SettingCollector;

public class ExperiencePicker extends NumberPicker {
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 1000;

	public ExperiencePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		setDividerColorTransparent();
	}

	private void init() {
		String[] displayedValues = generateDisplayedValues();
		super.setDisplayedValues(displayedValues);
		super.setMinValue(MIN_VALUE);
		super.setMaxValue(displayedValues.length - 1);
		super.setValue(0);
		//		expPicker.setOnValueChangedListener(this);
	}

	private String[] generateDisplayedValues() {
		int pickerStepSize = SettingCollector.getExperiencePickerStepSize();
		int numberOfSteps = ((MAX_VALUE - MIN_VALUE) / pickerStepSize) + 1;
		String[] experienceValues = new String[numberOfSteps];
		int nextValue = MIN_VALUE;
		for (int i = 0; i < experienceValues.length; i++) {
			experienceValues[i] = String.valueOf(nextValue);
			nextValue = nextValue + pickerStepSize;
		}
		return experienceValues;
	}

	public int getCurrentSelectedIndex() {
		return getValue();
	}

	public int getCurrentSelectedExpValue() {
		return Integer.valueOf(getDisplayedValues()[getCurrentSelectedIndex()]);
	}

	private void setDividerColorTransparent() {
		//		int color = App.getAppResources().getColor(R.color.transparent);
		int color = ContextCompat.getColor(App.getContext(), R.color.transparent);
		java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
		for (java.lang.reflect.Field pf : pickerFields) {
			if (pf.getName().equals("mSelectionDivider")) {
				pf.setAccessible(true);
				try {
					ColorDrawable colorDrawable = new ColorDrawable(color);
					pf.set(this, colorDrawable);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Resources.NotFoundException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
