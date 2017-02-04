package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

public enum HealthState {
    Alive(R.string.alive, R.color.green_medium, R.color.green_medium, R.color.grey_medium),
    Disabled(R.string.disabled, R.color.grey_medium, R.color.grey_medium, R.color.grey_dark),
    Dying(R.string.dying, R.color.red, R.color.red, R.color.grey_dark),
    Dead(R.string.dead, R.color.black, R.color.black, R.color.black),;

    private final String text;
    private final int colorResId;
    private final int secondaryColorResId;
    private final int backgroundColorResId;

    HealthState(@StringRes int textResId, @ColorRes int colorResId, @ColorRes int secondaryColorResId, @ColorRes int
            backgroundColorResId) {
        this.text = Res.getString(textResId);
        this.colorResId = colorResId;
        this.secondaryColorResId = secondaryColorResId;
        this.backgroundColorResId = backgroundColorResId;
    }

    public
    @ColorInt
    int getColor() {
        return ContextCompat.getColor(App.getContext(), colorResId);
    }

    public
    @ColorInt
    int getSecondaryColor() {
        return ContextCompat.getColor(App.getContext(), secondaryColorResId);
    }

    public
    @ColorInt
    int getBackgroundColor() {
        return ContextCompat.getColor(App.getContext(), backgroundColorResId);
    }

    @Override
    public String toString() {
        return text;
    }
}

