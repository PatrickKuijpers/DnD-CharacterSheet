package nl.tcilegnar.dndcharactersheet.Money.Settings;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.DefaultSettingValue;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneySettings extends Settings {
    private static MoneySettings instance;

    public static MoneySettings getInstance() {
        if (instance == null) {
            instance = new MoneySettings();
        }
        return instance;
    }

    private MoneySettings() {
    }

    public void tearDown() {
        instance = null;
    }

    @Override
    protected String fileName() {
        return "MoneySettings";
    }

    public boolean isMoneyUpdateManual() {
        String key = getKey(R.string.setting_key_money_update_manual);
        return loadBoolean(key, Boolean.valueOf(DefaultSettingValue.MONEY_UPDATE_MANUAL.value));
    }

    String getMoneyUpdateType() {
        String key = getKey(R.string.setting_key_money_update_type);
        return loadString(key, DefaultSettingValue.MONEY_UPDATE_TYPE.value);
    }

    public boolean isMoneyUpdateTypeInput() {
        return getMoneyUpdateType().equals(getString(R.string.setting_entry_money_update_type_input));
    }

    public boolean isMoneyUpdateTypeNumberPicker() {
        return getMoneyUpdateType().equals(getString(R.string.setting_entry_money_update_type_numberpicker));
    }

    public boolean isMoneyUpdateTypeNumberSlider() {
        return getMoneyUpdateType().equals(getString(R.string.setting_entry_money_update_type_numberslider));
    }
}