package nl.tcilegnar.dndcharactersheet.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.io.File;
import java.util.Map;
import java.util.Set;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Utils.Log;

public abstract class SharedPrefs {
    private static final int MODE = Context.MODE_PRIVATE;
    protected static final String ROOT = null;

    protected abstract String fileName();

    protected SharedPreferences getPrefs() {
        SharedPreferences prefs;
        if (fileName() == ROOT) {
            prefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        } else {
            prefs = App.getContext().getSharedPreferences(fileName(), MODE);
        }
        return prefs;
    }

    private Editor prefsEdit() {
        return getPrefs().edit();
    }

    // Booleans
    protected void save(String key, boolean value) {
        prefsEdit().putBoolean(key, value).apply();
    }

    protected boolean loadBoolean(String key) {
        return loadBoolean(key, false);
    }

    protected boolean loadBoolean(String key, boolean defaultValue) {
        return getPrefs().getBoolean(key, defaultValue);
    }

    protected boolean loadBoolean(String key, String defaultValue) {
        return loadBoolean(key, Boolean.valueOf(defaultValue));
    }

    // Strings
    protected void save(String key, String value) {
        prefsEdit().putString(key, value).apply();
    }

    protected String loadString(String key) {
        return loadString(key, null);
    }

    protected String loadString(String key, String defaultValue) {
        return getPrefs().getString(key, defaultValue);
    }

    // Integers
    protected void save(String key, int value) {
        prefsEdit().putInt(key, value).apply();
    }

    protected int loadInt(String key) {
        return loadInt(key, 0);
    }

    protected int loadInt(String key, int defaultValue) {
        return getPrefs().getInt(key, defaultValue);
    }

    protected int loadInt(String key, String defaultValue) {
        return loadInt(key, Integer.valueOf(defaultValue));
    }

    // Floats
    protected void save(String key, float value) {
        prefsEdit().putFloat(key, value).apply();
    }

    protected float loadFloat(String key) {
        return loadFloat(key, 0);
    }

    protected float loadFloat(String key, float defaultValue) {
        return getPrefs().getFloat(key, defaultValue);
    }

    protected float loadFloat(String key, String defaultValue) {
        return loadFloat(key, Float.valueOf(defaultValue));
    }

    // Longs
    protected void save(String key, long value) {
        prefsEdit().putLong(key, value).apply();
    }

    protected long loadLong(String key) {
        return loadLong(key, 0);
    }

    protected long loadLong(String key, long defaultValue) {
        return getPrefs().getLong(key, defaultValue);
    }

    protected long loadLong(String key, String defaultValue) {
        return loadLong(key, Long.valueOf(defaultValue));
    }

    // StringSets
    protected void save(String key, Set<String> value) {
        prefsEdit().putStringSet(key, value).apply();
    }

    protected Set<String> loadStringSet(String key) {
        return loadStringSet(key, null);
    }

    protected Set<String> loadStringSet(String key, Set<String> defaultValue) {
        return getPrefs().getStringSet(key, defaultValue);
    }

    protected String getKey(@StringRes int resId) {
        return getString(resId);
    }

    protected String getString(@StringRes int resId) {
        return App.getContext().getString(resId);
    }

    public void print() {
        Log.d("SharedPrefs", "================================");
        Log.d("SharedPrefs", "=== Printing all preferences ===");
        Log.d("SharedPrefs", "=== defaultPrefs ===");
        printPrefs(PreferenceManager.getDefaultSharedPreferences(App.getContext()).getAll());
        Log.d("SharedPrefs", "=== extendedSharedPrefs_old ===");
        printPrefs(App.getContext().getSharedPreferences(null, MODE).getAll());

        Log.d("SharedPrefs", "=== ExperienceSettings ===");
        SharedPreferences experienceSettings = App.getContext().getSharedPreferences("ExperienceSettings", MODE);
        printPrefs(experienceSettings.getAll()); // TODO: worden ook in default prefs opgeslagen?
        Log.d("SharedPrefs", "! Also saved in default prefs!?!?!");

        Log.d("SharedPrefs", "=== All prefs files ===");
        for (String prefFileName : getAllPrefFiles()) {
            if (prefFileName.contains(fileName())) {
                Log.i("SharedPrefs", "Current character: " + prefFileName);
                prefFileName = getFileNameWithoutExtension(prefFileName, ".xml");
                printPrefs(App.getContext().getSharedPreferences(prefFileName, MODE).getAll());
            } else {
                Log.d("SharedPrefs", prefFileName);
            }
        }
        Log.d("SharedPrefs", "=== End printing ===");
        Log.d("SharedPrefs", "====================");
    }

    protected void printPrefs(Map<String, ?> keys) {
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("SharedPrefs", "- " + entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    @NonNull
    protected String[] getAllPrefFiles() {
        File prefsdir = getPrefsDir();
        if (prefsdir.exists() && prefsdir.isDirectory()) {
            return prefsdir.list();
        }
        return new String[]{};
    }

    @NonNull
    protected File getPrefsDir() {
        return new File(App.getContext().getApplicationInfo().dataDir, "shared_prefs");
    }

    @NonNull
    protected String getFileNameWithoutExtension(String prefFileName, String extension) {
        int position = prefFileName.lastIndexOf(extension);
        if (position != -1) {
            prefFileName = prefFileName.substring(0, position);
        } else {
            Log.w("SharedPrefs", "Extension: " + extension + " not found");
        }
        return prefFileName;
    }

    protected void clear() {
        Map<String, ?> allPrefs = getPrefs().getAll();
        Log.d("SharedPrefs", "clearing " + fileName() + " (" + allPrefs.size() + " prefs):");
        printPrefs(allPrefs);
        getPrefs().edit().clear().apply();
    }
}