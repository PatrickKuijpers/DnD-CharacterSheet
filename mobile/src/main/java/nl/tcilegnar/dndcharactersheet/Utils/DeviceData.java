package nl.tcilegnar.dndcharactersheet.Utils;

import android.os.Build;

public class DeviceData {
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getDeviceType() {
        return Build.MODEL;
    }

    public static String getOS() {
        return "Android";
    }

    public static String getOSVersionReleaseNr() {
        return String.valueOf(Build.VERSION.RELEASE);
    }

    public static String getOSVersionSdkNr() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static boolean isAtLeastLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
