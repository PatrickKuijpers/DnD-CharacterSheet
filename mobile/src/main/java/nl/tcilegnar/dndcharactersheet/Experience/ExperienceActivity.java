package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.FragmentManager;

public class ExperienceActivity extends BaseStorageActivity {
    private FragmentManager fragmentManager = new FragmentManager(this);

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

    @Override
    protected void updateSettingsData() {
        getExperienceFragment().updateSettingsData();
    }
}
