package nl.tcilegnar.dndcharactersheet.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

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
        // stackoverflow.com/questions/14580085/android-how-to-get-list-of-all-preference-xmls-for-my-app-and-read-them
        Log.i("TEST", "--- defaultPrefs ---");
        printPrefs(PreferenceManager.getDefaultSharedPreferences(App.getContext()).getAll());
        Log.i("TEST", "--- extendedSharedPrefs_old ---");
        printPrefs(App.getContext().getSharedPreferences(null, MODE).getAll());
        Log.i("TEST", "--- 10001 ---");
        SharedPreferences settings1 = App.getContext().getSharedPreferences("10001", MODE);
        printPrefs(settings1.getAll());
        Log.i("TEST", "--- 10002 ---");
        SharedPreferences settings2 = App.getContext().getSharedPreferences("10002", MODE);
        printPrefs(settings2.getAll());
        Log.i("TEST", "--- 10003 ---");
        SharedPreferences settings3 = App.getContext().getSharedPreferences("10003", MODE);
        printPrefs(settings3.getAll());
        Log.i("TEST", "--- ExperienceSettings ---");
        SharedPreferences experienceSettings = App.getContext().getSharedPreferences("ExperienceSettings", MODE);
        printPrefs(experienceSettings.getAll()); // TODO: worden ook in default prefs opgeslagen?
    }

    private void printPrefs(Map<String, ?> keys) {
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("TEST", entry.getKey() + ": " + entry.getValue().toString());
        }
    }
}