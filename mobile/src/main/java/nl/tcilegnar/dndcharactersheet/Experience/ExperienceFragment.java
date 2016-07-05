package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceCurrentLevel;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceUpdater;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceFragment extends BaseFragment {
    private Level level;
    private ExperienceCurrentLevel expCurrentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_experience, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        level = new Level();

        expCurrentLevel = (ExperienceCurrentLevel) view.findViewById(R.id.experience_current_level);
        expCurrentLevel.setExperienceEdgeListener(level);

        ExperienceUpdater expUpdater = (ExperienceUpdater) view.findViewById(R.id.experience_updater);
        expUpdater.setExperienceUpdateListener(expCurrentLevel);
    }

    protected void onSaveData() {
        expCurrentLevel.saveExp();
        level.saveLevel();
    }
}
