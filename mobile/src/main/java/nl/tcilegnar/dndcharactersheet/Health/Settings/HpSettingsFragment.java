package nl.tcilegnar.dndcharactersheet.Health.Settings;

import android.preference.Preference;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class HpSettingsFragment extends SettingsFragment {

    @Override
    protected int getSettingsResource() {
        return R.xml.settings_hp;
    }

    @Override
    protected HpSettings getSettings() {
        return HpSettings.getInstance();
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initValues() {
    }

    @Override
    protected void setPreferenceChangeListeners() {
    }

    @Override
    protected void updateDependencies(Preference preference, Object newValue) {
    }
}