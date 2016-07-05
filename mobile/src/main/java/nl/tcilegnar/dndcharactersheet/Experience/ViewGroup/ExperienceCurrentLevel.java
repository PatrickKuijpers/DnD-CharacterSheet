package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        this.experience = experience;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.experience_current_lvl, this);
        initViews();
        updateViewValues(experience.getCurrentExp());
    }

    private void initViews() {
        expProgressBar = (ProgressBar) findViewById(R.id.experience_progressBar);
        expProgressBar.setMax(experience.getMax());
    }

    private void updateViewValues(int newExp) {
        String expLabelText = App.getAppResources().getString(R.string.experience_label);
        String expText = expLabelText + " " + newExp;
        ((TextView) findViewById(R.id.experience_text)).setText(expText);

        expProgressBar.setProgress(newExp);
    }

    public Experience getExperience() {
        return experience;
    }

    @Override
    public void onUpdateExperience(int expUpdateValue) {
        try {
            int newExp = experience.updateExperience(expUpdateValue);
            updateViewValues(newExp);
        } catch (ExpTooLowException e) {
            e.printStackTrace();
            Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
