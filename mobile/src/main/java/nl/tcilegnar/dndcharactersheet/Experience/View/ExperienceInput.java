package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberInput;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettings;

public class ExperienceInput extends BaseNumberInput {
    public ExperienceInput(Context context, AttributeSet attrs) {
        this(context, attrs, ExperienceSettings.getInstance());
    }

    @VisibleForTesting
    protected ExperienceInput(Context context, AttributeSet attrs, ExperienceSettings settings) {
        super(context, attrs, settings);
    }

    protected boolean shouldBeVisible() {
        return ((ExperienceSettings) settings).isExperienceUpdateTypeInput();
    }
}
