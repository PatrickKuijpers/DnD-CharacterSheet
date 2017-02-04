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
import nl.tcilegnar.dndcharactersheet.Health.HpFragment;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.DeviceData;

import static android.view.View.OnClickListener;

public class HpIndicator extends LinearLayout implements OnClickListener {
    private final Hp hp;

    private TextView totalHpLabel;
    private TextView totalHpValue;
    private TextView currentHpValue;
    private TextView tempHpValue;
    private ProgressBar currentHpProgressBar;
    private ProgressBar tempHpProgressBar;
    private TextView healthStateValue;
    private HpFragment changeHpValueCall;

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
        initViews();
    }

    private void initViews() {
        totalHpLabel = (TextView) findViewById(R.id.total_hp_label);
        totalHpValue = (TextView) findViewById(R.id.total_hp_value);
        currentHpValue = (TextView) findViewById(R.id.current_hp_value);
        tempHpValue = (TextView) findViewById(R.id.temp_hp_value);
        currentHpProgressBar = (ProgressBar) findViewById(R.id.current_hp_progress);
        tempHpProgressBar = (ProgressBar) findViewById(R.id.temp_hp_progress);
        healthStateValue = (TextView) findViewById(R.id.current_health_state);
        updateHpValues();

        totalHpLabel.setOnClickListener(this);
        totalHpValue.setOnClickListener(this);
        currentHpValue.setOnClickListener(this);
        tempHpValue.setOnClickListener(this);
    }

    private void updateHpValues() {
        int totalHp = hp.getTotal();
        int currentHp = hp.getCurrent();
        int tempHp = hp.getTemp();
        updateTotalHp(totalHp);
        updateCurrentHp(currentHp);
        updateTempHp(tempHp);
        updateCurrentHpProgressBar(totalHp, currentHp);
        updateHealthState();
    }

    private void updateTotalHp(int totalHp) {
        totalHpValue.setText(String.valueOf(totalHp));
    }

    private void updateCurrentHp(int currentHp) {
        currentHpValue.setText(String.valueOf(currentHp));
    }

    private void updateTempHp(int tempHp) {
        tempHpValue.setText(String.valueOf(tempHp));
        tempHpProgressBar.setMax(5); // TODO: design van volledige HpIndicator verbeteren, dan is dit niet meer nodig
        tempHpProgressBar.setProgress(tempHp);

        if (tempHp > 0) {
            tempHpValue.setVisibility(VISIBLE);
            tempHpProgressBar.setVisibility(VISIBLE);
        } else {
            tempHpValue.setVisibility(INVISIBLE);
            tempHpProgressBar.setVisibility(INVISIBLE);
        }
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

    private void updateHealthState() {
        HealthState currentHealthState = hp.getCurrentHealthState();
        healthStateValue.setText(currentHealthState.toString());
        healthStateValue.setTextColor(ColorStateList.valueOf(currentHealthState.getColor()));
    }

    public Hp getHp() {
        return hp;
    }

    public void save() {
        hp.save();
    }

    @Override
    public void onClick(View clickedView) {
        int id = clickedView.getId();
        if (id == R.id.total_hp_label || id == R.id.total_hp_value || id == R.id.current_hp_value || id == R.id
                .temp_hp_value) {
            TextView viewWithValueToChange = getViewWithValueToChange(clickedView, id);
            changeHpValueCall.showDialog(viewWithValueToChange);
        }
    }

    private TextView getViewWithValueToChange(View clickedView, int id) {
        View viewWithValueToChange = clickedView;
        if (id == R.id.total_hp_label) {
            viewWithValueToChange = findViewById(R.id.total_hp_value);
        }
        return (TextView) viewWithValueToChange;
    }

    public void setChangeHpValueCallback(HpFragment changeHpValueCall) {
        this.changeHpValueCall = changeHpValueCall;
    }

    public void setNewHpValue(int newValue, int viewId) {
        if (viewId == R.id.total_hp_value) {
            hp.setTotal(newValue);
        } else if (viewId == R.id.current_hp_value) {
            hp.setCurrent(newValue);
        } else if (viewId == R.id.temp_hp_value) {
            hp.setTemp(newValue);
        }
        updateHpValues();
    }
}