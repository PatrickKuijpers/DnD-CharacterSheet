package nl.tcilegnar.dndcharactersheet.Utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class AppData {
    public static String getAppName() {
        return App.getResourceString(R.string.app_name);
    }

    public static String getAppVersionName() {
        Context context = App.getContext();
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return context.getString(R.string.version_unknown);
        }
    }
}
