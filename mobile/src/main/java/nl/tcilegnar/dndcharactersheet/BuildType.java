package nl.tcilegnar.dndcharactersheet;

public class BuildType {
    public boolean isDebug() {
        return BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug");
    }
}
