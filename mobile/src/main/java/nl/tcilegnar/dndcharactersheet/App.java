package nl.tcilegnar.dndcharactersheet;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void restart() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) { //TODO Tijdelijk nullpointercheck vanwege falende unittests
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            System.exit(0);
        }
    }

    /** stackoverflow.com/questions/37998266/android-studio-many-error-could-not-find-class-android-xxx#38666791 */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            // Work around Robolectric causing multi dex installation to fail, see
            // https://code.google.com/p/android/issues/detail?id=82007.
            boolean isUnderUnitTest;

            try {
                Class<?> robolectric = Class.forName("org.robolectric.Robolectric");
                isUnderUnitTest = (robolectric != null);
            } catch (ClassNotFoundException e) {
                isUnderUnitTest = false;
            }

            if (!isUnderUnitTest) {
                // Re-throw if this does not seem to be triggered by Robolectric.
                throw multiDexException;
            }
        }
    }
}
