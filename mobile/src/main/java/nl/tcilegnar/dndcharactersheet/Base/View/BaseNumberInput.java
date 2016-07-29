package nl.tcilegnar.dndcharactersheet.Base.View;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class BaseNumberInput extends EditText {
    protected final Settings settings;

    public BaseNumberInput(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    @VisibleForTesting
    public BaseNumberInput(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        this.settings = settings;
        initViewsIfVisible();
    }

    protected void initViewsIfVisible() {
        if (shouldBeVisible()) {
            initView();
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    protected abstract boolean shouldBeVisible();

    protected void initView() {
    }

    public void updateSettingsData() {
        initViewsIfVisible();
    }

    public String getInputText() {
        return getText().toString();
    }

    public int getInputNumber() {
        return hasInput() ? Integer.valueOf(getInputText()) : 0;
    }

    protected boolean hasInput() {
        return !getInputText().isEmpty();
    }
}
