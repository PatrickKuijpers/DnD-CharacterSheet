package nl.tcilegnar.dndcharactersheet.Money;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverView;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyFragment extends BaseStorageFragment implements OnClickListener {
    private PlatinumView platinumView;
    private GoldView goldView;
    private SilverView silverView;
    private BronzeView bronzeView;
    private ImageButton moneyChangeButton;

    private ChangeMoneyListener changeMoneyListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChangeMoneyListener) {
            changeMoneyListener = (ChangeMoneyListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        platinumView = (PlatinumView) view.findViewById(R.id.platinum_view);
        goldView = (GoldView) view.findViewById(R.id.gold_view);
        silverView = (SilverView) view.findViewById(R.id.silver_view);
        bronzeView = (BronzeView) view.findViewById(R.id.bronze_view);

        moneyChangeButton = (ImageButton) view.findViewById(R.id.money_change_button);

        updateButtonsVisibility();
    }

    @Override
    protected MoneySettings getSettings() {
        return MoneySettings.getInstance();
    }

    @Override
    protected void updateSettingsData() {
        updateButtonsVisibility();
        platinumView.updateSettingsData();
        goldView.updateSettingsData();
        silverView.updateSettingsData();
        bronzeView.updateSettingsData();
    }

    private void updateButtonsVisibility() {
        boolean moneyUpdateManual = getSettings().isMoneyUpdateManual();
        if (moneyUpdateManual) {
            moneyChangeButton.setVisibility(View.GONE);
            moneyChangeButton.setOnClickListener(null);
        } else {
            moneyChangeButton.setVisibility(View.VISIBLE);
            moneyChangeButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.money_change_button) {
            MoneyValues currentMoneyValues = getMoneyValues();
            changeMoneyListener.onChangeMoney(currentMoneyValues);
        }
    }

    @Override
    protected void onLoadData() {
        platinumView.load();
        goldView.load();
        silverView.load();
        bronzeView.load();
    }

    @Override
    protected void onSaveData() {
        // TODO Niet meer nodig?
        platinumView.save();
        goldView.save();
        silverView.save();
        bronzeView.save();
    }

    public void changeMoney(MoneyValues newMoneyValues) {
        platinumView.changeMoneyValue(newMoneyValues);
        goldView.changeMoneyValue(newMoneyValues);
        silverView.changeMoneyValue(newMoneyValues);
        bronzeView.changeMoneyValue(newMoneyValues);
    }

    public MoneyValues getMoneyValues() {
        int platinumValue = platinumView.getCurrentMoneyValue();
        int goldValue = goldView.getCurrentMoneyValue();
        int silverValue = silverView.getCurrentMoneyValue();
        int bronzeValue = bronzeView.getCurrentMoneyValue();
        return new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
    }

    public interface ChangeMoneyListener {
        void onChangeMoney(MoneyValues currentMoneyValues);
    }
}
