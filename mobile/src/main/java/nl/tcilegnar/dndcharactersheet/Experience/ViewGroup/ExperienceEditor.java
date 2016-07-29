package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Experience.View.ExperienceInput;
import nl.tcilegnar.dndcharactersheet.Experience.View.ExperiencePicker;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class ExperienceEditor extends LinearLayout implements OnClickListener, TextView.OnEditorActionListener {
    private final Settings settings;
    private ExperiencePicker expPicker;
    private ExperienceInput expInput;

    private ExperienceUpdateListener experienceUpdateListener;

    public ExperienceEditor(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
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
        expInput.setOnEditorActionListener(this);

        (findViewById(R.id.experience_plus_button)).setOnClickListener(this);
        (findViewById(R.id.experience_min_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.experience_plus_button) {
            addExperience();
        }
        if (viewId == R.id.experience_min_button) {
            substractExperience();
        }
    }

    private void addExperience() {
        int expValue = getExpValue();
        experienceUpdateListener.onUpdateExperience(expValue);
    }

    private void substractExperience() {
        int expValue = getExpValue();
        experienceUpdateListener.onUpdateExperience(-expValue);
    }

    private int getExpValue() {
        if (settings.isExperienceUpdateTypeInput()) {
            return expInput.getInputNumber();
        } else if (settings.isExperienceUpdateTypeNumberPicker()) {
            return expPicker.getCurrentSelectedNumber();
        } else {
            return 0;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            addExperience();
        }
        return false; // Voer de standaard action uit: done = verberg soft keyboard
    }

    public void updateSettingsData() {
        expInput.updateSettingsData();
        expPicker.updateSettingsData();
    }

    public void setExperienceUpdateListener(ExperienceUpdateListener experienceUpdateListener) {
        this.experienceUpdateListener = experienceUpdateListener;
    }

    public interface ExperienceUpdateListener {
        void onUpdateExperience(int expUpdateValue);
    }
}
