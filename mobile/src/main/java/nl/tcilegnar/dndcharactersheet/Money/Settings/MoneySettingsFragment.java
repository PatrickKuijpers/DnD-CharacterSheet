package nl.tcilegnar.dndcharactersheet.Money.Settings;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneySettingsFragment extends SettingsFragment {
    private CheckBoxPreference moneyUpdateCalculatedPref;
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
        moneyUpdateCalculatedPref = (CheckBoxPreference) findPreference(R.string.setting_key_money_update_calculated);
        moneyUpdateTypePref = (ListPreference) findPreference(R.string.setting_key_money_update_type);
    }

    @Override
    protected void initValues() {
        MoneySettings settings = getSettings();

        boolean isCalculated = settings.isMoneyUpdateCalculatedAutomatically();
        moneyUpdateCalculatedPref.setChecked(isCalculated);

        String moneyUpdateType = settings.getMoneyUpdateType();
        moneyUpdateTypePref.setValue(moneyUpdateType);
    }

    @Override
    protected void setPreferenceChangeListeners() {
        moneyUpdateCalculatedPref.setOnPreferenceChangeListener(this);
        moneyUpdateTypePref.setOnPreferenceChangeListener(this);
    }
}