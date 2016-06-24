package nl.tcilegnar.dndcharactersheet.Experience;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceFragment extends Fragment implements View.OnClickListener {
	private Experience exp = new Experience();

	private ProgressBar expProgressBar;
	private TextView expTextView;
	private ExperiencePicker expPicker;
	
	private int currentPickerIndex = 0;

	public enum SavedValues {
		CURRENT_PICKER_INDEX
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_experience, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			currentPickerIndex = savedInstanceState.getInt(SavedValues.CURRENT_PICKER_INDEX.name());
		}

		initExperienceViews(view);
		initNumberPicker(view);

		setListeners(view);

		super.onViewCreated(view, savedInstanceState);
	}

	private void initExperienceViews(View view) {
		expTextView = (TextView) view.findViewById(R.id.experience_text);
		expProgressBar = (ProgressBar) view.findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(exp.getMax());

		updateExperienceViewValues(exp.getCurrentExp());
	}

	private void updateExperienceViewValues(int newExperience) {
		String expText = getString(R.string.experience_label) + " " + newExperience;
		expTextView.setText(expText);

		expProgressBar.setProgress(newExperience);
	}

	private void initNumberPicker(View view) {
		expPicker = (ExperiencePicker) view.findViewById(R.id.experiencePicker);
		expPicker.setValue(currentPickerIndex);
	}

	private void setListeners(View view) {
		(view.findViewById(R.id.experience_plus_button)).setOnClickListener(this);
		(view.findViewById(R.id.experience_min_button)).setOnClickListener(this);
	}

	@Override
	public void onPause() {
		onSave();
		super.onPause();
	}

	private void onSave() {
		exp.saveExp();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SavedValues.CURRENT_PICKER_INDEX.name(), expPicker.getCurrentSelectedIndex());
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if (viewId == R.id.experience_plus_button) {
			exp.addExperience(expPicker.getCurrentSelectedExpValue());
			updateExperienceViewValues(exp.getCurrentExp());
		}
		if (viewId == R.id.experience_min_button) {
			exp.subtractExperience(expPicker.getCurrentSelectedExpValue());
			updateExperienceViewValues(exp.getCurrentExp());
		}
	}
}
