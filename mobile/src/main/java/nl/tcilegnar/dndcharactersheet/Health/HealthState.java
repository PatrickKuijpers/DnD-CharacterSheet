package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public enum HealthState {
    Alive(R.color.green_medium, R.color.green_medium, R.color.grey_light),
    Disabled(R.color.transparent, R.color.transparent, R.color.grey_medium),
    Dying(R.color.red, R.color.red, R.color.grey_dark),
    Dead(R.color.black, R.color.black, R.color.black);

    private final int colorResId;
    private final int secondaryColorResId;
    private final int backgroundColorResId;

    HealthState(@ColorRes int colorResId, @ColorRes int secondaryColorResId, @ColorRes int backgroundColorResId) {
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
        return ContextCompat.getColor(App.getContext(), colorResId);
    }
}

