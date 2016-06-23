package nl.tcilegnar.dndcharactersheet.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Manager.Storage;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.SharedPreference.SettingCollector;

public class ExperienceFragment extends Fragment implements View.OnClickListener {
	private static final int EXP_DEFAULT = 1050;
	private static final int EXP_MAX = 2500;
	private static final int PICKER_MIN_VALUE = 0;
	private static final int PICKER_MAX_VALUE = 1000;

	public enum SavedValues {
		CURRENT_PICKER_INDEX
	}

	private int currentExperience = EXP_DEFAULT;
	private int currentPickerIndex = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_experience, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			currentPickerIndex = savedInstanceState.getInt(SavedValues.CURRENT_PICKER_INDEX.name());
		} else {
			currentPickerIndex = 0;
		}

		initExperienceBar();
		initNumberPicker();

		setListeners();

		super.onViewCreated(view, savedInstanceState);
	}

	private void initExperienceBar() {
		ProgressBar expProgressBar = (ProgressBar) getView().findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(EXP_MAX);
	}

	private void initNumberPicker() {
		NumberPicker numberPicker = (NumberPicker) getView().findViewById(R.id.numberPicker);
		String[] expValues = getExperienceValues(PICKER_MIN_VALUE, PICKER_MAX_VALUE);
		numberPicker.setDisplayedValues(expValues);
		numberPicker.setMaxValue(expValues.length - 1);
		numberPicker.setMinValue(PICKER_MIN_VALUE);
		numberPicker.setValue(currentPickerIndex);
	}

	private String[] getExperienceValues(int minValue, int maxValue) {
		int pickerStepSize = SettingCollector.getExperiencePickerStepSize();
		int numberOfSteps = ((maxValue - minValue) / pickerStepSize) + 1;
		String[] experienceValues = new String[numberOfSteps];
		int nextValue = minValue;
		for (int i = 0; i < experienceValues.length; i++) {
			experienceValues[i] = String.valueOf(nextValue);
			nextValue = nextValue + pickerStepSize;
		}
		return experienceValues;
	}

	private void setListeners() {
		(getView().findViewById(R.id.experience_plus_button)).setOnClickListener(this);
		(getView().findViewById(R.id.experience_min_button)).setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		currentExperience = Storage.getSharedPreference(Storage.Key.CURRENT_EXP);
		updateExperienceViews(currentExperience);
	}

	private void updateExperienceViews(int newExperience) {
		TextView expTextView = (TextView) getView().findViewById(R.id.experience_text);
		String expText = getString(R.string.experience_label) + " " + newExperience;
		expTextView.setText(expText);

		ProgressBar expProgressBar = (ProgressBar) getView().findViewById(R.id.experience_progressBar);
		expProgressBar.setProgress(newExperience);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int currentPickerIndex = ((NumberPicker) getView().findViewById(R.id.numberPicker)).getValue();
		outState.putInt(SavedValues.CURRENT_PICKER_INDEX.name(), currentPickerIndex);
	}

	@Override
	public void onPause() {
		Storage.saveSharedPreference(Storage.Key.CURRENT_EXP, currentExperience);
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.experience_plus_button) {
			addExperience(getCurrentNumberPickerValue());
		}
		if (viewId == R.id.experience_min_button) {
			subtractExperience(getCurrentNumberPickerValue());
		}
	}

	public int getCurrentNumberPickerValue() {
		NumberPicker numberPicker = (NumberPicker) getView().findViewById(R.id.numberPicker);
		int currentIndex = numberPicker.getValue();
		return Integer.valueOf(numberPicker.getDisplayedValues()[currentIndex]);
	}

	private void addExperience(int addedExperience) {
		currentExperience = currentExperience + addedExperience;
		updateExperienceViews(currentExperience);
	}

	private void subtractExperience(int subtractedExperience) {
		currentExperience = currentExperience - subtractedExperience;
		updateExperienceViews(currentExperience);
	}
}
