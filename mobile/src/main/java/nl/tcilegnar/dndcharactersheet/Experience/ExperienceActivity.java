package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Experience.Settings.ExperienceSettingsActivity;

public class ExperienceActivity extends BaseStorageActivity {
    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return ExperienceSettingsActivity.class;
    }

    public enum FragTag {
        Experience
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentManager.addFirstFragment(getExperienceFragment(), FragTag.Experience.name());
        }
    }

    public ExperienceFragment getExperienceFragment() {
        String tag = FragTag.Experience.name();
        ExperienceFragment experienceFragment = fragmentManager.getFragment(ExperienceFragment.class, tag);
        if (experienceFragment == null) {
            experienceFragment = new ExperienceFragment();
        }
        return experienceFragment;
    }
}
