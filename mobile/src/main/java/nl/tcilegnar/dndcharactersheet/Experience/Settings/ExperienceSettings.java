package nl.tcilegnar.dndcharactersheet.Experience.Settings;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.DefaultSettingValue;
import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;

public class ExperienceSettings extends Settings {
    private static ExperienceSettings instance;

    public static ExperienceSettings getInstance() {
        if (instance == null) {
            instance = new ExperienceSettings();
        }
        return instance;
    }

    private ExperienceSettings() {
    }

    public void tearDown() {
        instance = null;
    }

    @Override
    protected String fileName() {
        return "ExperienceSettings";
    }

    public int getExperiencePickerStepSize() {
        String key = getKey(R.string.setting_key_experience_picker_steps);
        String experiencePickerStepSize = loadString(key, DefaultSettingValue.EXP_UPDATE_PICKER_STEPSIZE.value);
        return Integer.valueOf(experiencePickerStepSize);
    }

    String getExperienceUpdateType() {
        String key = getKey(R.string.setting_key_experience_update_type);
        return loadString(key, DefaultSettingValue.EXP_UPDATE_TYPE.value);
    }

    public boolean isLevelDownAllowed() {
        String key = getKey(R.string.setting_key_allow_level_down);
        return loadBoolean(key, Boolean.valueOf(DefaultSettingValue.ALLOW_LEVEL_DOWN.value));
    }

    public boolean isExperienceUpdateTypeInput() {
        return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_input));
    }

    public boolean isExperienceUpdateTypeNumberSlider() {
        return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_number_slider));
    }
}