package nl.tcilegnar.dndcharactersheet.abilities;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class AbilitiesActivity extends BaseStorageActivity {
    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }

    public enum FragTag {
        ABILITIES
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentManager.addFirstFragment(getAbilitiesFragment(), FragTag.ABILITIES.name());
        }
    }

    public AbilitiesFragment getAbilitiesFragment() {
        String tag = FragTag.ABILITIES.name();
        AbilitiesFragment abilitiesFragment = fragmentManager.getFragment(AbilitiesFragment.class, tag);
        if (abilitiesFragment == null) {
            abilitiesFragment = new AbilitiesFragment();
        }
        return abilitiesFragment;
    }
}
