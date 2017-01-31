package nl.tcilegnar.dndcharactersheet.Experience;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettingsActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class ExperienceActivity extends BaseStorageActivity {
    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.EXPERIENCE;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return ExperienceSettingsActivity.class;
    }
}
