package nl.tcilegnar.dndcharactersheet.Base.Exceptions;

import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;

public abstract class CustomToastException extends Exception {
    public CustomToastException(String message) {
        super(message);
        SingleToast.INSTANCE.show(App.getContext(), getMessage(), Toast.LENGTH_LONG);
        SingleToast.INSTANCE.show(App.getContext(), "some other message", Toast.LENGTH_LONG);
    }
}
