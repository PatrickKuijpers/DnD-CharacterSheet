package nl.tcilegnar.dndcharactersheet.Experience;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceUpdater;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceFragment extends Fragment implements Experience.ExperienceUpdateFinishedListener {
	private Experience exp = new Experience();

	private ProgressBar expProgressBar;
	private TextView expTextView;
	private ExperienceUpdater expUpdater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_experience, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initExperienceCurrentLevel(view);
		initExperienceUpdater(view);

		super.onViewCreated(view, savedInstanceState);
	}

	private void initExperienceCurrentLevel(View view) {
		expTextView = (TextView) view.findViewById(R.id.experience_text);
		expProgressBar = (ProgressBar) view.findViewById(R.id.experience_progressBar);
		expProgressBar.setMax(exp.getMax());

		updateExperienceViewValues(exp.getCurrentExp());

		exp.setExperienceUpdateFinishedListener(this);
	}

	private void updateExperienceViewValues(int newExperience) {
		String expText = getString(R.string.experience_label) + " " + newExperience;
		expTextView.setText(expText);

		expProgressBar.setProgress(newExperience);
	}

	private void initExperienceUpdater(View view) {
		expUpdater = (ExperienceUpdater) view.findViewById(R.id.experience_updater);
		expUpdater.setExperienceUpdateListener(exp);
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
	public void onExperienceUpdated(int newExp) {
		updateExperienceViewValues(newExp);
	}
}
