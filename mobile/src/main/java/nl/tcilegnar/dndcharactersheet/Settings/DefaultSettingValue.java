package nl.tcilegnar.dndcharactersheet.Settings;

import android.support.annotation.StringRes;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public enum DefaultSettingValue {
    // Algemeen
    SHOW_HINTS(R.string.setting_defaultvalue_show_hints),
    // Experience
    EXP_UPDATE_TYPE(R.string.setting_defaultvalue_experience_update_type),
    EXP_UPDATE_PICKER_STEPSIZE(R.string.setting_defaultvalue_experience_picker_steps),
    ALLOW_LEVEL_DOWN(R.string.setting_defaultvalue_allow_level_down),
    // Money
    MONEY_UPDATE_MANUAL(R.string.setting_defaultvalue_money_update_manual),
    MONEY_UPDATE_TYPE(R.string.setting_defaultvalue_money_update_type);

    public final String value;

    DefaultSettingValue(@StringRes int resId) {
        value = App.getContext().getString(resId);
    }
}
