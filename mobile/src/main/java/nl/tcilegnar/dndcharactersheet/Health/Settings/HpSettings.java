package nl.tcilegnar.dndcharactersheet.Health.Settings;

import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;

public class HpSettings extends Settings {
    private static HpSettings instance;

    public static HpSettings getInstance() {
        if (instance == null) {
            instance = new HpSettings();
        }
        return instance;
    }

    private HpSettings() {
    }

    public void tearDown() {
        instance = null;
    }

    @Override
    protected String fileName() {
        return "HpSettings";
    }
}