package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettings;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceCurrentLevel;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.LevelChangedListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup.LevelChangeView;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup.LevelChangeView.LevelUpIconVisibilityChangedListener;
import nl.tcilegnar.dndcharactersheet.Level.ViewGroup.LevelIndicatorView;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Main.MainSettings;
import nl.tcilegnar.dndcharactersheet.Utils.KeyboardUtil;

public class ExperienceFragment extends BaseStorageFragment implements LevelUpIconVisibilityChangedListener,
        LevelChangedListener {
    private ExperienceCurrentLevel expCurrentLevel;
    private LevelIndicatorView levelIndicatorView;
    private LevelChangeView levelChangeView;
    private ExperienceEditor expEditor;
    private Snackbar levelInfoSnackbar;

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
        Level level = levelIndicatorView.getLevel();
        level.addLevelChangedListener(this);
        levelChangeView = levelIndicatorView.getLevelChangeView();
        levelChangeView.setLevelUpIconVisibilityChangedListener(this);

        expCurrentLevel = (ExperienceCurrentLevel) view.findViewById(R.id.experience_current_level);
        Experience experience = expCurrentLevel.getExperience();
        experience.addExperienceEdgeListener(level);
        experience.addExperienceEdgeListener(expCurrentLevel);
        experience.setCurrentProjectedLevelListener(levelIndicatorView);

        expEditor = (ExperienceEditor) view.findViewById(R.id.experience_editor);
        expEditor.setExperienceUpdateListener(expCurrentLevel);

        toggleLevelInfoSnackbar();
    }

    @Override
    protected ExperienceSettings getSettings() {
        return ExperienceSettings.getInstance();
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onSaveData() {
        expCurrentLevel.save();
        levelIndicatorView.save();
    }

    @Override
    protected void updateSettingsData() {
        expEditor.updateSettingsData();
        toggleLevelInfoSnackbar();
    }

    @Override
    public void onLevelUpIconVisibilityChanged() {
        toggleLevelInfoSnackbar();
    }

    @Override
    public void onLevelChanged() {
        hideSnackbar();
    }

    private void toggleLevelInfoSnackbar() {
        if (MainSettings.getInstance().shouldShowHints() && levelChangeView.isReadyForLevelChange()) {
            showSnackbar();
        } else {
            hideSnackbar();
        }
    }

    private void showSnackbar() {
        getLevelInfoSnackbar().show();
        KeyboardUtil.hideKeyboard(getActivity());
    }

    private void hideSnackbar() {
        getLevelInfoSnackbar().dismiss();
    }

    private Snackbar getLevelInfoSnackbar() {
        if (levelInfoSnackbar == null) {
            CoordinatorLayout layout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
            levelInfoSnackbar = Snackbar.make(layout, getLevelUpOrDownText(), Snackbar.LENGTH_INDEFINITE);
        } else {
            levelInfoSnackbar.setText(getLevelUpOrDownText());
        }
        return levelInfoSnackbar;
    }

    public String getLevelUpOrDownText() {
        // TODO: deze is nog nodig omdat je bij initViews nog niet weet wat numberOfLevelsReadyForChange is
        String levelUpOrDownText = getString(R.string.level_change_info_unknown);

        boolean isLevelUp = levelChangeView.getNumberOfLevelsReadyForChange() > 0;
        if (isLevelUp) {
            levelUpOrDownText = getString(R.string.level_change_info_up);
        }

        boolean isLeveldown = levelChangeView.getNumberOfLevelsReadyForChange() < 0;
        if (isLeveldown) {
            levelUpOrDownText = getString(R.string.level_change_info_down);
        }

        return levelUpOrDownText;
    }
}
