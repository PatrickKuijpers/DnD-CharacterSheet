package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Animations.ExperienceProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceUpdatedListener;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceUpdater.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.Level.ViewGroup.LevelIndicatorView;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceCurrentLevel extends LinearLayout implements ExperienceUpdateListener,
        ExperienceUpdatedListener {
    private final Experience experience;
    private ProgressBar expProgressBar;
    private ExperienceProgressBarAnimation experienceProgressBarAnimation;

    public ExperienceCurrentLevel(Context context, AttributeSet attrs) {
        this(context, attrs, new Experience(), new ExperienceProgressBarAnimation());
    }

    @VisibleForTesting
    protected ExperienceCurrentLevel(Context context, AttributeSet attrs, Experience experience,
                                     ExperienceProgressBarAnimation expProgressBarAnimation) {
        super(context, attrs, R.attr.expCurrentLvlStyle);
        inflate(context, R.layout.experience_current_lvl, this);
        experience.setCurrentProjectedLevelListener(new LevelIndicatorView(App.getContext(), null));
        this.experience = experience;
        this.experienceProgressBarAnimation = expProgressBarAnimation;
        initViews();
    }

    private void initViews() {
        expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
        setProgressValues();
    }

    private void setProgressValues() {
        updateProgressText();
        expProgressBar.setMax(experience.getMax());
        expProgressBar.setProgress(experience.getCurrentExp());
    }

    private void updateProgressText() {
        String expLabelText = App.getResourceString(R.string.experience_label);
        String expText = expLabelText + experience.getCurrentExp();
        ((TextView) findViewById(R.id.experience_text)).setText(expText);
    }

    public Experience getExperience() {
        return experience;
    }

    public void save() {
        experience.save();
    }

    @Override
    public void onUpdateExperience(int expUpdateValue) {
        try {
            experience.updateExperience(expUpdateValue);
        } catch (ExpTooLowException e) {
            // TODO: iets hiermee doen?
        }
    }

    @Override
    public void onExperienceUpdated() {
        updateProgressText();
        animateExperienceProgressBar();
    }

    private void animateExperienceProgressBar() {
        experienceProgressBarAnimation.start(expProgressBar, experience);
    }

    @Override
    public boolean hasExperienceUpdateAnimationFinished() {
        return experienceProgressBarAnimation.isFinished();
    }
}
