package nl.tcilegnar.dndcharactersheet.Experience.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;

import nl.tcilegnar.dndcharactersheet.Base.View.BaseNumberInput;
import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public class ExperienceInput extends BaseNumberInput {
    public ExperienceInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @VisibleForTesting
    public ExperienceInput(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs, settings);
    }

    protected boolean shouldBeVisible() {
        return settings.isExperienceUpdateTypeInput();
    }
}
