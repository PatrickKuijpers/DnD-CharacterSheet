package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class BasicInfoActivity extends BaseActivity {
    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.BASIC_INFO;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }
}
