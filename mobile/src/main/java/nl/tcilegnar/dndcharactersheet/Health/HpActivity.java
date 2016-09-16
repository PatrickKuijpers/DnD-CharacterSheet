package nl.tcilegnar.dndcharactersheet.Health;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettingsActivity;

public class HpActivity extends BaseStorageActivity {
    @Override
    protected Class<? extends PreferenceActivity> getSettingsActivityClass() {
        return HpSettingsActivity.class;
    }

    public enum FragTag {
        Hp
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentManager.addFirstFragment(getHpFragment(), FragTag.Hp.name());
        }
    }

    public HpFragment getHpFragment() {
        String tag = FragTag.Hp.name();
        HpFragment hpFragment = fragmentManager.getFragment(HpFragment.class, tag);
        if (hpFragment == null) {
            hpFragment = new HpFragment();
        }
        return hpFragment;
    }
}
