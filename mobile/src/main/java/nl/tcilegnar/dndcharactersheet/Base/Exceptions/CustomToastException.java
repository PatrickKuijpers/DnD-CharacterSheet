package nl.tcilegnar.dndcharactersheet.Base.Exceptions;

import android.widget.Toast;

public abstract class CustomToastException extends Exception {
    public CustomToastException(String message) {
        super(message);
        SingleToast.INSTANCE.show(getMessage(), Toast.LENGTH_LONG);
    }
}
