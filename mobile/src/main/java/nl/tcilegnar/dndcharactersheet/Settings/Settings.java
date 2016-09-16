package nl.tcilegnar.dndcharactersheet.Settings;

import android.preference.Preference;

import java.util.Set;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Storage.SharedPrefs;

public abstract class Settings extends SharedPrefs {
    private BaseStorageFragment settingsChangedListener;

    @Override
    protected abstract String fileName();

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
        if (settingsChangedListener != null) {
            settingsChangedListener.onSettingsChanged();
        }
    }

    public void setSettingsChangedListener(BaseStorageFragment settingsChangedListener) {
        this.settingsChangedListener = settingsChangedListener;
    }
}