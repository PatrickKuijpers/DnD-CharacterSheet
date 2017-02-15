package nl.tcilegnar.dndcharactersheet.Health.ViewGroup;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Health.HealthState;
import nl.tcilegnar.dndcharactersheet.Health.Hp;
import nl.tcilegnar.dndcharactersheet.Health.HpFragment;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.DeviceData;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

import static android.view.View.OnClickListener;

public class HpIndicator extends LinearLayout implements OnClickListener {
    private final Hp hp;

    private TextView totalHpLabel;
    private TextView totalHpValue;
    private TextView currentHpValue;
    private ProgressBar currentHpProgressBar;

    private View tempHpDummyView;
    private TextView tempHpValue;
    private ProgressBar tempHpProgressBar;
    private ImageView tempHpIcon;

    private TextView healthStateValue;
    private HpFragment changeHpValueCall;

    public enum HpType {
        TotalHp(R.string.total_hp), CurrentHp(R.string.current_hp), TempHp(R.string.temp_hp);

        private final String text;

        HpType(@StringRes int resId) {
            this.text = Res.getString(resId);
        }

        @Override
        public String toString() {
            return text;
        }
    }

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
        currentHpProgressBar = (ProgressBar) findViewById(R.id.current_hp_progress);

        tempHpDummyView = findViewById(R.id.total_hp_value_dummy_height);
        tempHpValue = (TextView) findViewById(R.id.temp_hp_value);
        tempHpProgressBar = (ProgressBar) findViewById(R.id.temp_hp_progress);
        tempHpIcon = (ImageView) findViewById(R.id.temp_hp_icon);

        healthStateValue = (TextView) findViewById(R.id.current_health_state);
        updateHpValues();

        totalHpLabel.setOnClickListener(this);
        totalHpValue.setOnClickListener(this);
        currentHpValue.setOnClickListener(this);
        tempHpValue.setOnClickListener(this);
        tempHpIcon.setOnClickListener(this);
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
            showTempHpViews();
        } else {
            hideTempHpViews();
        }
    }

    private void showTempHpViews() {
        tempHpDummyView.setVisibility(VISIBLE);
        tempHpValue.setVisibility(VISIBLE);
        tempHpProgressBar.setVisibility(VISIBLE);
        tempHpIcon.setVisibility(GONE);
    }

    private void hideTempHpViews() {
        tempHpDummyView.setVisibility(GONE);
        tempHpValue.setVisibility(GONE);
        tempHpProgressBar.setVisibility(GONE);
        tempHpIcon.setVisibility(VISIBLE);
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

            tempHpProgressBar.setProgressBackgroundTintList(ColorStateList.valueOf(backgroundColor));
            tempHpProgressBar.setProgressTintList(ColorStateList.valueOf(secondaryColor));
        } else {
            // TODO: hoe op oudere versies mooi maken? ... backgroundColor???
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
        TextView viewWithValueToChange = getViewWithValueToChange(clickedView, id);
        if (id == R.id.total_hp_label || id == R.id.total_hp_value || id == R.id.current_hp_value || id == R.id
                .temp_hp_value || id == R.id.temp_hp_icon) {
            String value = viewWithValueToChange.getText().toString();
            HpType hpType = getHpTypeToChange(viewWithValueToChange.getId());
            changeHpValueCall.showDialog(value, hpType);
        }
    }

    private TextView getViewWithValueToChange(View clickedView, int id) {
        View viewWithValueToChange = clickedView;
        if (id == R.id.total_hp_label) {
            viewWithValueToChange = findViewById(R.id.total_hp_value);
        } else if (id == R.id.temp_hp_icon) {
            viewWithValueToChange = findViewById(R.id.temp_hp_value);
        }
        return (TextView) viewWithValueToChange;
    }

    private HpType getHpTypeToChange(int viewId) {
        if (viewId == R.id.total_hp_value) {
            return HpType.TotalHp;
        } else if (viewId == R.id.current_hp_value) {
            return HpType.CurrentHp;
        } else if (viewId == R.id.temp_hp_value) {
            return HpType.TempHp;
        } else {
            return null;
        }
    }

    public void setChangeHpValueCallback(HpFragment changeHpValueCall) {
        this.changeHpValueCall = changeHpValueCall;
    }

    public void setNewHpValue(int newValue, HpType hpType) {
        switch (hpType) {
            case TotalHp:
                hp.setTotal(newValue);
                break;
            case CurrentHp:
                int newCurrentHp = getNewCurrentHp(newValue);
                hp.setCurrent(newCurrentHp);
                break;
            case TempHp:
                hp.setTemp(newValue);
                break;
        }
        updateHpValues();
    }

    private int getNewCurrentHp(int newValue) {
        int newCurrentHp = hp.getCurrent() + newValue;
        if (newCurrentHp > hp.getTotal()) {
            return hp.getTotal();
        } else {
            return newCurrentHp;
        }
    }
}