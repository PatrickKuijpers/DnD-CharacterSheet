package nl.tcilegnar.dndcharactersheet.Experience;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceCurrentLevel;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceUpdater;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceFragment extends Fragment {
	private ExperienceCurrentLevel expCurrentLvl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_experience, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initExperienceViews(view);
	}

	private void initExperienceViews(View view) {
		expCurrentLvl = (ExperienceCurrentLevel) view.findViewById(R.id.experience_current_lvl);
		((ExperienceUpdater) view.findViewById(R.id.experience_updater)).setExperienceUpdateListener(expCurrentLvl);
	}

	@Override
	public void onPause() {
		onSaveData();
		super.onPause();
	}

	private void onSaveData() {
		expCurrentLvl.saveExp();
	}
}
