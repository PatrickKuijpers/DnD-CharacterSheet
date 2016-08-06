package nl.tcilegnar.dndcharactersheet.Utils;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

public class MyBuildConfig {
    public boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    }
}
