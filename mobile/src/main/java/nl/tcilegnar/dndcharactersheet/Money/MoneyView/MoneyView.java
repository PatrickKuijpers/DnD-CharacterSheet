package nl.tcilegnar.dndcharactersheet.Money.MoneyView;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class MoneyView extends LinearLayout {
    private final Settings settings;

    public MoneyView(Context context, AttributeSet attrs) {
        this(context, attrs, Settings.getInstance());
    }

    @VisibleForTesting
    protected MoneyView(Context context, AttributeSet attrs, Settings settings) {
        super(context, attrs);
        inflate(context, getLayoutResource(), this);
        this.settings = settings;
        init(context);
    }

    private void init(Context context) {
    }

    protected abstract int getLayoutResource();
}
