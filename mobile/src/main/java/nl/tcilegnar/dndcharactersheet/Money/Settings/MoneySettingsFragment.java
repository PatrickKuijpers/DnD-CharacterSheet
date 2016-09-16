package nl.tcilegnar.dndcharactersheet.Money.Settings;

import android.preference.ListPreference;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;

public class MoneySettingsFragment extends SettingsFragment {
    private ListPreference moneyUpdateTypePref;

    @Override
    protected int getSettingsResource() {
        return R.xml.settings_money;
    }

    @Override
    protected MoneySettings getSettings() {
        return MoneySettings.getInstance();
    }

    @Override
    protected void initViews() {
        moneyUpdateTypePref = (ListPreference) findPreference(getString(R.string.setting_key_money_update_type));
    }

    @Override
    protected void initValues() {
        MoneySettings settings = getSettings();
        String moneyUpdateType = settings.getMoneyUpdateType();
        moneyUpdateTypePref.setValue(moneyUpdateType);
    }

    @Override
    protected void setPreferenceChangeListeners() {
        moneyUpdateTypePref.setOnPreferenceChangeListener(this);
    }
}