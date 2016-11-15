package nl.tcilegnar.dndcharactersheet.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import nl.tcilegnar.dndcharactersheet.App;

public class KeyboardUtil {
    public static void hideKeyboard(Activity activity) {
        View currentViewInFocus = activity.getCurrentFocus();
        hideKeyboard(currentViewInFocus);
    }

    public static void hideKeyboard(View currentViewInFocus) {
        if (currentViewInFocus != null) {
            IBinder windowToken = currentViewInFocus.getWindowToken();
            getInputMethodManager().hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static InputMethodManager getInputMethodManager() {
        return (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}