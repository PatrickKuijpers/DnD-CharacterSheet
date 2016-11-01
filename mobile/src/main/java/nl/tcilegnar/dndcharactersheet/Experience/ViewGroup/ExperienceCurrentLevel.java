package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.ViewGroup.LevelIndicatorView;
import nl.tcilegnar.dndcharactersheet.R;

import static nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExperienceEdgeListener;

public class ExperienceCurrentLevel extends LinearLayout implements ExperienceUpdateListener, ExperienceEdgeListener {
    public static final int PROGRESS_DURATION_MILLIS = 1000;
    private final Experience experience;
    private ProgressBar expProgressBar;

    public ExperienceCurrentLevel(Context context, AttributeSet attrs) {
        this(context, attrs, new Experience());
    }

    @VisibleForTesting
    protected ExperienceCurrentLevel(Context context, AttributeSet attrs, Experience experience) {
        super(context, attrs, R.attr.expCurrentLvlStyle);
        inflate(context, R.layout.experience_current_lvl, this);
        experience.setCurrentProjectedLevelListener(new LevelIndicatorView(App.getContext(), null));
        this.experience = experience;
        initViews();
    }

    private void initViews() {
        expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
        updateAllValues();
    }

    private void updateAllValues() {
        updateProgressText();
        updateProgressBarValues();
    }

    private void updateProgressText() {
        String expLabelText = App.getResourceString(R.string.experience_label);
        String expText = expLabelText + getCurrentExp();
        ((TextView) findViewById(R.id.experience_text)).setText(expText);
    }

    private void updateProgressBarValues() {
        expProgressBar.setMax(experience.getMax());
        updateProgressBarProgress();
    }

    private void updateProgressBarProgress() {
        ProgressBarAnimation anim = new ProgressBarAnimation(expProgressBar, getCurrentExp());
        anim.setDuration(PROGRESS_DURATION_MILLIS);
        expProgressBar.startAnimation(anim);
    }

    public Experience getExperience() {
        return experience;
    }

    private int getCurrentExp() {
        return experience.getCurrentExp();
    }

    public void save() {
        experience.save();
    }

    @Override
    public void onUpdateExperience(int expUpdateValue) {
        try {
            experience.updateExperience(expUpdateValue);
            updateCurrentExpValues();
        } catch (ExpTooLowException e) {
            // TODO: iets hiermee doen?
        }
    }

    private void updateCurrentExpValues() {
        updateProgressText();
        updateProgressBarProgress();
    }

    @Override
    public void onExperienceMinPassed() throws MinLevelReachedException {
        updateProgressBarValues();
    }

    @Override
    public void onExperienceMaxReached() throws MaxLevelReachedException {
        updateProgressBarValues();
    }
}
