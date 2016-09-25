package nl.tcilegnar.dndcharactersheet.Base.Exceptions;

import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;

public abstract class CustomToastException extends Exception {
    public CustomToastException(String message) {
        super(message);
        showToastWithWarningMessage();
    }

    private void showToastWithWarningMessage() {
        Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
    }
}
