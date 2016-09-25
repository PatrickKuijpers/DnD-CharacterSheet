package nl.tcilegnar.dndcharactersheet.Money;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverView;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneyFragment extends BaseStorageFragment implements OnClickListener {
    private PlatinumView platinumView;
    private GoldView goldView;
    private SilverView silverView;
    private BronzeView bronzeView;
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
        initClickListeners(view);
    }

    private void initViews(View view) {
        platinumView = (PlatinumView) view.findViewById(R.id.platinum_view);
        goldView = (GoldView) view.findViewById(R.id.gold_view);
        silverView = (SilverView) view.findViewById(R.id.silver_view);
        bronzeView = (BronzeView) view.findViewById(R.id.bronze_view);
    }

    private void initClickListeners(View view) {
        (view.findViewById(R.id.change_money_plus_button)).setOnClickListener(this);
        (view.findViewById(R.id.change_money_min_button)).setOnClickListener(this);
    }

    @Override
    protected Settings getSettings() {
        return MoneySettings.getInstance();
    }

    @Override
    protected void updateSettingsData() {
        platinumView.updateSettingsData();
        goldView.updateSettingsData();
        silverView.updateSettingsData();
        bronzeView.updateSettingsData();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.change_money_plus_button) {
            changeMoneyListener.onAddMoneyClicked();
        } else if (viewId == R.id.change_money_min_button) {
            changeMoneyListener.onSubstractMoneyClicked();
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

    public void changeMoney(MoneyValues moneyValues) {
        // TODO: kan abstracter?
        platinumView.setMoneyValue(moneyValues.getPlatinumValue());
        goldView.setMoneyValue(moneyValues.getGoldValue());
        silverView.setMoneyValue(moneyValues.getSilverValue());
        bronzeView.setMoneyValue(moneyValues.getBronzeValue());
    }

    public MoneyValues getMoneyValues() {
        int platinumValue = platinumView.getMoneyValue();
        int goldValue = goldView.getMoneyValue();
        int silverValue = silverView.getMoneyValue();
        int bronzeValue = bronzeView.getMoneyValue();
        return new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
    }

    public interface ChangeMoneyListener {
        void onAddMoneyClicked();

        void onSubstractMoneyClicked();
    }
}
