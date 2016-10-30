package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nl.tcilegnar.dndcharactersheet.Money.MoneyValues;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyIndicatorPicker;
import nl.tcilegnar.dndcharactersheet.Money.View.MoneyIndicatorTextView;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public abstract class MoneyView extends LinearLayout {
    private static final int ROTATE_BY_DEGREES = 3 * 360;
    private static final long ROTATE_DURATION_MILLIS = (long) 2000;
    private static final int ROTATE_INTERPOLATOR = android.R.anim.accelerate_decelerate_interpolator;
    protected final Storage storage;

    private ImageView moneyIndicatorCoin;
    private MoneyIndicatorTextView moneyIndicatorTextView;
    private MoneyIndicatorPicker moneyIndicatorPicker;

    public MoneyView(Context context, AttributeSet attrs) {
        this(context, attrs, new Storage());
    }

    @VisibleForTesting
    protected MoneyView(Context context, AttributeSet attrs, Storage storage) {
        super(context, attrs);
        inflate(context, getLayoutResource(), this);
        this.storage = storage;
        initView();
    }

    protected abstract
    @LayoutRes
    int getLayoutResource();

    protected abstract int loadMoneyValue();

    protected abstract void saveMoneyValue(int value);

    protected abstract int getMoneyValue(MoneyValues moneyValues);

    private void initView() {
        moneyIndicatorCoin = (ImageView) findViewById(R.id.money_indicator_coin);
        moneyIndicatorTextView = (MoneyIndicatorTextView) findViewById(R.id.money_indicator_text_view);
        moneyIndicatorPicker = (MoneyIndicatorPicker) findViewById(R.id.money_indicator_numberpicker);
    }

    public void updateSettingsData() {
        moneyIndicatorTextView.updateSettingsData();
        moneyIndicatorPicker.updateSettingsData();
    }

    public void load() {
        int moneyValue = loadMoneyValue();
        setMoneyValue(moneyValue);
    }

    public int getCurrentMoneyValue() {
        if (moneyIndicatorTextView.getVisibility() == View.VISIBLE) {
            return moneyIndicatorTextView.getMoneyValue();
        } else if (moneyIndicatorPicker.getVisibility() == View.VISIBLE) {
            return moneyIndicatorPicker.getCurrentSelectedNumber();
        } else {
            return 0;
        }
    }

    public void changeMoneyValue(MoneyValues newMoneyValues) {
        int newMoneyValue = getMoneyValue(newMoneyValues);
        coinAnimation(newMoneyValue);
        setMoneyValue(newMoneyValue);
    }

    private void coinAnimation(int newMoneyValue) {
        boolean moneyValueChanged = newMoneyValue != getCurrentMoneyValue();
        if (moneyValueChanged) {
            ViewPropertyAnimator firstAnim = moneyIndicatorCoin.animate();
            firstAnim.rotationYBy(ROTATE_BY_DEGREES).setDuration(ROTATE_DURATION_MILLIS);
            firstAnim.setInterpolator(AnimationUtils.loadInterpolator(getContext(), ROTATE_INTERPOLATOR));
        }
    }

    public void setMoneyValue(int newMoneyValue) {
        moneyIndicatorTextView.setMoneyValue(newMoneyValue);
        moneyIndicatorPicker.setMoneyValue(newMoneyValue);
        save();
    }

    public void save() {
        int value = getCurrentMoneyValue();
        saveMoneyValue(value);
    }
}
