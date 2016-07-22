package nl.tcilegnar.dndcharactersheet;

public class MyBuildConfig {
    public boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    }
}
