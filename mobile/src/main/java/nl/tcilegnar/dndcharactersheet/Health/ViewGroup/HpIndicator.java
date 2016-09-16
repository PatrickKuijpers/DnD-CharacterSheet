package nl.tcilegnar.dndcharactersheet.Health.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Health.Hp;
import nl.tcilegnar.dndcharactersheet.R;

public class HpIndicator extends LinearLayout {
    private final Hp hp;

    private TextView totalHpValue;
    private TextView currentHpValue;
    private TextView tempHpValue;
    private ProgressBar currentHpProgressBar;
    private ProgressBar tempHpProgressBar;

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
        testSetValues();
        initViews();
    }

    @Deprecated
    private void testSetValues() {
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
        updateHpValues();
    }

    private void updateHpValues() {
        int totalHp = hp.getTotal();
        String totalHpLabelText = App.getAppResources().getString(R.string.total_hp_label);
        String totalHpText = totalHpLabelText + " " + totalHp;
        totalHpValue.setText(totalHpText);

        int currentHp = hp.getCurrent();
        currentHpValue.setText(String.valueOf(currentHp));
        currentHpProgressBar.setMax(totalHp);
        currentHpProgressBar.setProgress(currentHp);

        int tempHp = hp.getTemp();
        tempHpValue.setText(String.valueOf(tempHp));
        tempHpProgressBar.setMax(5);
        tempHpProgressBar.setProgress(tempHp);
    }

    public Hp getHp() {
        return hp;
    }

    public void save() {
        hp.save();
    }
}