package nl.tcilegnar.dndcharactersheet.Health.ViewGroup;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Health.HealthState;
import nl.tcilegnar.dndcharactersheet.Health.Hp;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.DeviceData;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

public class HpIndicator extends LinearLayout {
    private final Hp hp;

    private TextView totalHpValue;
    private TextView currentHpValue;
    private TextView tempHpValue;
    private ProgressBar currentHpProgressBar;
    private ProgressBar tempHpProgressBar;
    private TextView healthStateValue;

    public HpIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, new Hp());
    }

    @VisibleForTesting
    protected HpIndicator(Context context, AttributeSet attrs, Hp hp) {
        super(context, attrs, R.attr.hpIndicatorStyle);
        this.hp = hp;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.hp_indicator, this);
        setDummyValues();
        initViews();
    }

    @Deprecated
    private void setDummyValues() {
        hp.setTotal(10);
        hp.setCurrent(7);
        hp.setTemp(2);
    }

    private void initViews() {
        totalHpValue = (TextView) findViewById(R.id.total_hp_value);
        currentHpValue = (TextView) findViewById(R.id.current_hp_value);
        tempHpValue = (TextView) findViewById(R.id.temp_hp_value);
        currentHpProgressBar = (ProgressBar) findViewById(R.id.current_hp_progress);
        tempHpProgressBar = (ProgressBar) findViewById(R.id.temp_hp_progress);
        healthStateValue = (TextView) findViewById(R.id.current_health_state);
        updateHpValues();
        updateHealthState();
    }

    private void updateHealthState() {
        HealthState currentHealthState = hp.getCurrentHealthState();
        healthStateValue.setText(currentHealthState.toString());
        healthStateValue.setTextColor(ColorStateList.valueOf(currentHealthState.getColor()));
    }

    private void updateHpValues() {
        int totalHp = hp.getTotal();
        int currentHp = hp.getCurrent();
        int tempHp = hp.getTemp();
        updateTotalHp(totalHp);
        updateCurrentHp(totalHp, currentHp);
        updateTempHp(tempHp);
    }

    private void updateTotalHp(int totalHp) {
        String totalHpLabelText = Res.getString(R.string.total_hp_label);
        String totalHpText = totalHpLabelText + totalHp;
        totalHpValue.setText(totalHpText);
    }

    private void updateCurrentHp(int totalHp, int currentHp) {
        currentHpValue.setText(String.valueOf(currentHp));
        updateCurrentHpProgressBar(totalHp, currentHp);
    }

    private void updateCurrentHpProgressBar(int totalHp, int currentHp) {
        HealthState currentHealthState = hp.getCurrentHealthState();
        switch (currentHealthState) {
            case Alive:
            case Disabled:
                currentHpProgressBar.setMax(totalHp);
                currentHpProgressBar.setProgress(currentHp);
                break;
            case Dying:
            case Dead:
                currentHpProgressBar.setMax(Hp.DYING_HP);
                int dyingProgress = Hp.DYING_HP + currentHp;
                currentHpProgressBar.setProgress(dyingProgress);
                break;
        }

        updateCurrentHpProgressBarColors(currentHealthState);
    }

    private void updateCurrentHpProgressBarColors(HealthState currentHealthState) {
        @ColorInt int progressColor = currentHealthState.getColor();
        @ColorInt int secondaryColor = currentHealthState.getSecondaryColor();
        @ColorInt int backgroundColor = currentHealthState.getBackgroundColor();

        if (DeviceData.isAtLeastLollipop()) {
            currentHpProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            currentHpProgressBar.setProgressTintList(ColorStateList.valueOf(progressColor));
            //currentHpProgressBar.setSecondaryProgressTintList(ColorStateList.valueOf(secondaryColor));

            tempHpProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            tempHpProgressBar.setProgressTintList(ColorStateList.valueOf(secondaryColor));
        } else {
            // TODO: hoe op oudere versies mooi maken?
            currentHpProgressBar.getProgressDrawable().setColorFilter(progressColor, PorterDuff.Mode.SRC_IN);
            tempHpProgressBar.getProgressDrawable().setColorFilter(secondaryColor, PorterDuff.Mode.SRC_OUT);
        }
    }

    private void updateTempHp(int tempHp) {
        tempHpValue.setText(String.valueOf(tempHp));
        tempHpProgressBar.setMax(5); // TODO: design van volledige HpIndicator verbeteren, dan is dit niet meer nodig
        tempHpProgressBar.setProgress(tempHp);

        if (tempHp > 0) {
            tempHpValue.setVisibility(View.VISIBLE);
            tempHpProgressBar.setVisibility(View.VISIBLE);
        } else {
            tempHpValue.setVisibility(View.INVISIBLE);
            tempHpProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public Hp getHp() {
        return hp;
    }

    public void save() {
        hp.save();
    }
}