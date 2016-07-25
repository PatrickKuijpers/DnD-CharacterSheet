package nl.tcilegnar.dndcharactersheet.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
    public static void hideKeyboard(Activity activity) {
        View currentViewInFocus = activity.getCurrentFocus();
        if (currentViewInFocus != null) {
            IBinder windowToken = currentViewInFocus.getWindowToken();
            getInputMethodManager(activity).hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static InputMethodManager getInputMethodManager(Activity activity) {
        return (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}