package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class ExperienceInput extends EditText {
    public ExperienceInput(Context context, AttributeSet attrs) {
        this(context, attrs, new Settings());
    }

    public ExperienceInput(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        boolean shouldBeVisible = settings.isExperienceUpdateTypeInput();
        if (shouldBeVisible) {
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    public int getExpValue() {
        int expValue = 0;
        if (hasInput()) {
            String expValueInput = getInputText();
            expValue = Integer.valueOf(expValueInput);
        }
        return expValue;
    }

    public boolean hasInput() {
        return !getInputText().isEmpty();
    }

    private String getInputText() {
        return getText().toString();
    }
}
