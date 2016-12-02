package nl.tcilegnar.dndcharactersheet.MainMenu.Settings;

import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.DefaultSettingValue;
import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;

public class MainSettings extends Settings {
    private static MainSettings instance;

    public static MainSettings getInstance() {
        if (instance == null) {
            instance = new MainSettings();
        }
        return instance;
    }

    private MainSettings() {
    }

    public void tearDown() {
        instance = null;
    }

    @Override
    protected String fileName() {
        return "MainSettings";
    }

    public boolean shouldShowHints() {
        String key = getKey(R.string.setting_key_show_hints);
        return loadBoolean(key, Boolean.valueOf(DefaultSettingValue.SHOW_HINTS.value));
    }

    public boolean shouldPlaySounds() {
        String key = getKey(R.string.setting_key_play_sounds);
        return loadBoolean(key, Boolean.valueOf(DefaultSettingValue.PLAY_SOUND.value));
    }
}