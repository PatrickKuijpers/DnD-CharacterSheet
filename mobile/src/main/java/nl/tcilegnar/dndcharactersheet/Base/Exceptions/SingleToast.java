package nl.tcilegnar.dndcharactersheet.Base.Exceptions;

import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;

public enum SingleToast {
    INSTANCE;

    private Toast currentToast;
    private String currentMessage;

    public void show(String message, int duration) {
        if (message.equals(currentMessage)) {
            currentToast.cancel();
        }

        currentToast = Toast.makeText(App.getContext(), message, duration);
        currentToast.show();

        currentMessage = message;
    }
}
