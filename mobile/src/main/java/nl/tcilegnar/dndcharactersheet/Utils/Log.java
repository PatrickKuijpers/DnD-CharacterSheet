package nl.tcilegnar.dndcharactersheet.Utils;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

public class Log {
    private static final String BASE_LOGTAG = "TEST";

    public static void v(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.v(finalLogTag(logTag), msg);
        }
    }

    public static void d(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.d(finalLogTag(logTag), msg);
        }
    }

    public static void i(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.i(finalLogTag(logTag), msg);
        }
    }

    public static void w(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.w(finalLogTag(logTag), msg);
        }
    }

    public static void e(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e(finalLogTag(logTag), msg);
        }
    }

    public static void wtf(String logTag, String msg) {
        if (BuildConfig.DEBUG) {
            android.util.Log.wtf(finalLogTag(logTag), msg);
        }
    }

    private static String finalLogTag(String logTag) {
        return BASE_LOGTAG + logTag;
    }
}
