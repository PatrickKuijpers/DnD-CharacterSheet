package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceCurrentLevel;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.ViewGroup.LevelIndicatorView;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceFragment extends BaseFragment {
    private ExperienceCurrentLevel expCurrentLevel;
    private LevelIndicatorView levelIndicatorView;
    private Level level;
    private Experience experience;

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
        levelIndicatorView = (LevelIndicatorView) view.findViewById(R.id.level_indicator_view);
        level = levelIndicatorView.getLevel();

        expCurrentLevel = (ExperienceCurrentLevel) view.findViewById(R.id.experience_current_level);
        experience = expCurrentLevel.getExperience();
        experience.addExperienceEdgeListener(level);
        experience.addExperienceEdgeListener(expCurrentLevel);
        experience.setCurrentProjectedLevelListener(levelIndicatorView);

        ExperienceEditor expEditor = (ExperienceEditor) view.findViewById(R.id.experience_editor);
        expEditor.setExperienceUpdateListener(expCurrentLevel);
    }

    protected void onSaveData() {
        expCurrentLevel.save();
        levelIndicatorView.save();
    }
}
