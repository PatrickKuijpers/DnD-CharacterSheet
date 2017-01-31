package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;

public class BasicInfoActivity extends BaseActivity {
    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }

    public enum FragTag {
        BasicInfo
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            String tag = FragTag.BasicInfo.name();
            fragmentManager.addFirstFragment(getBasicInfoFragment(), tag);
        }
    }

    public BasicInfoFragment getBasicInfoFragment() {
        String tag = FragTag.BasicInfo.name();
        BasicInfoFragment fragment = fragmentManager.getFragment(BasicInfoFragment.class, tag);
        if (fragment == null) {
            fragment = new BasicInfoFragment();
        }
        return fragment;
    }
}
