package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExpTooLowException;
import nl.tcilegnar.dndcharactersheet.Experience.ViewGroup.ExperienceEditor.ExperienceUpdateListener;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceCurrentLevel extends LinearLayout implements ExperienceUpdateListener {
    private final Experience experience;
    private ProgressBar expProgressBar;

    public ExperienceCurrentLevel(Context context, AttributeSet attrs) {
        this(context, attrs, new Experience());
    }

    @VisibleForTesting
    protected ExperienceCurrentLevel(Context context, AttributeSet attrs, Experience experience) {
        super(context, attrs, R.attr.expCurrentLvlStyle);
        inflate(context, R.layout.experience_current_lvl, this);
        this.experience = experience;
        initViews();
    }

    private void initViews() {
        expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
        expProgressBar.setMax(experience.getMax());

        updateViewValues();
    }

    private void updateViewValues() {
        int exp = experience.getCurrentExp();
        String expLabelText = App.getAppResources().getString(R.string.experience_label);
        String expText = expLabelText + " " + exp;
        ((TextView) findViewById(R.id.experience_text)).setText(expText);

        expProgressBar.setProgress(exp);
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
            updateViewValues();
        } catch (ExpTooLowException e) {
            e.printStackTrace();
        }
    }
}
