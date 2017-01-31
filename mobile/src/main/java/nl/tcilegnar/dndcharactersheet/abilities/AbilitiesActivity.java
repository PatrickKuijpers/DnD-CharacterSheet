package nl.tcilegnar.dndcharactersheet.abilities;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class AbilitiesActivity extends BaseStorageActivity {
    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.ABILITIES;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return null;
    }
}
