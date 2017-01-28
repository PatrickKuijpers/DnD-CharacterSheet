package nl.tcilegnar.dndcharactersheet.Base.Settings;

import android.preference.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Storage.SharedPrefs;

public abstract class Settings extends SharedPrefs {
    private List<BaseFragment> settingsChangedListeners = new ArrayList<>();

    public boolean savePreferenceValue(Preference preference, Object newValue) {
        notifyListeners();
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

    private void notifyListeners() {
        for (BaseFragment settingsChangedListener : settingsChangedListeners) {
            settingsChangedListener.onSettingsChanged();
        }
    }

    public void addSettingsChangedListener(BaseFragment settingsChangedListener) {
        this.settingsChangedListeners.add(settingsChangedListener);
    }

    public void removeSettingsChangedListener(BaseFragment settingsChangedListener) {
        this.settingsChangedListeners.remove(settingsChangedListener);
    }
}