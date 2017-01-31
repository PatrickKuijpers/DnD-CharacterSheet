package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettingsActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class HpActivity extends BaseStorageActivity {
    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.HP;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return HpSettingsActivity.class;
    }
}
