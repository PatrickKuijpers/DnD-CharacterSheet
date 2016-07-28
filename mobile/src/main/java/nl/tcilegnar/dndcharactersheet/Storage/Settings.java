package nl.tcilegnar.dndcharactersheet.Storage;

import android.preference.Preference;
import android.support.annotation.StringRes;

import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class Settings extends SharedPrefs {
    private static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    private Settings() {
    }

    public void tearDown() {
        instance = null;
    }

    private BaseStorageFragment settingsChangedListener;

    @Override
    protected String fileName() {
        return "Settings";
    }

    protected enum DefaultValue {
        SHOW_HINTS(R.string.setting_defaultvalue_show_hints),
        EXP_UPDATE_TYPE(R.string.setting_defaultvalue_experience_update_type),
        EXP_UPDATE_PICKER_STEPSIZE(R.string.setting_defaultvalue_experience_picker_steps),
        ALLOW_LEVEL_DOWN(R.string.setting_defaultvalue_allow_level_down);

        protected final String value;

        DefaultValue(@StringRes int resId) {
            value = App.getContext().getString(resId);
        }
    }

    public boolean shouldShowHints() {
        String key = getKey(R.string.setting_key_show_hints);
        return loadBoolean(key, Boolean.valueOf(DefaultValue.SHOW_HINTS.value));
    }

    public String getExperienceUpdateType() {
        String key = getKey(R.string.setting_key_experience_update_type);
        return loadString(key, DefaultValue.EXP_UPDATE_TYPE.value);
    }

    public int getExperiencePickerStepSize() {
        String key = getKey(R.string.setting_key_experience_picker_steps);
        String experiencePickerStepSize = loadString(key, DefaultValue.EXP_UPDATE_PICKER_STEPSIZE.value);
        return Integer.valueOf(experiencePickerStepSize);
    }

    public boolean isLevelDownAllowed() {
        String key = getKey(R.string.setting_key_allow_level_down);
        return loadBoolean(key, Boolean.valueOf(DefaultValue.ALLOW_LEVEL_DOWN.value));
    }

    public boolean isExperienceUpdateTypeInput() {
        return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_input));
    }

    public boolean isExperienceUpdateTypeNumberPicker() {
        return getExperienceUpdateType().equals(getString(R.string.setting_entry_experience_update_type_numberpicker));
    }

    public boolean savePreferenceValue(Preference preference, Object newValue) {
        settingsChangedListener.onSettingsChanged();
        String key = preference.getKey();
        boolean isSaved = true;
        if (newValue instanceof Boolean) {
            save(key, (Boolean) newValue);
        } else if (newValue instanceof String) {
            save(key, (String) newValue);
        } else if (newValue instanceof Integer) {
            save(key, (Integer) newValue);
        } else if (newValue instanceof Float) {
            save(key, (Float) newValue);
        } else if (newValue instanceof Long) {
            save(key, (Long) newValue);
        } else if (newValue instanceof Set<?>) {
            save(key, (Set<String>) newValue);
        } else {
            isSaved = false;
        }
        return isSaved;
    }

    public void setSettingsChangedListener(BaseStorageFragment settingsChangedListener) {
        this.settingsChangedListener = settingsChangedListener;
    }
}