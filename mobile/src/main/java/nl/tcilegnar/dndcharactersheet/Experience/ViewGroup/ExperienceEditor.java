package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceInput;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperiencePicker;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class ExperienceEditor extends LinearLayout implements OnClickListener {
    private final Settings settings;
    private ExperiencePicker expPicker;
    private ExperienceInput expInput;

    private ExperienceUpdateListener experienceUpdateListener;

    public ExperienceEditor(Context context, AttributeSet attrs) {
        this(context, attrs, new Settings());
    }

    @VisibleForTesting
    protected ExperienceEditor(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, R.attr.expEditorStyle);
        inflate(context, R.layout.experience_editor, this);
        this.settings = settings;
        init();
    }

    private void init() {
        expPicker = (ExperiencePicker) findViewById(R.id.experience_picker);
        expInput = (ExperienceInput) findViewById(R.id.experience_input);

        (findViewById(R.id.experience_plus_button)).setOnClickListener(this);
        (findViewById(R.id.experience_min_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.experience_plus_button) {
            experienceUpdateListener.onUpdateExperience(getExpValue());
        }
        if (viewId == R.id.experience_min_button) {
            experienceUpdateListener.onUpdateExperience(-getExpValue());
        }
    }

    private int getExpValue() {
        if (settings.isExperienceUpdateTypeInput()) {
            return expInput.getExpValue();
        } else if (settings.isExperienceUpdateTypeNumberPicker()) {
            return expPicker.getCurrentSelectedExpValue();
        } else {
            return 0;
        }
    }

    public void setExperienceUpdateListener(ExperienceUpdateListener experienceUpdateListener) {
        this.experienceUpdateListener = experienceUpdateListener;
    }

    public interface ExperienceUpdateListener {
        void onUpdateExperience(int expUpdateValue);
    }
}
