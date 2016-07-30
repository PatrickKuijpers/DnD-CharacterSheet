package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverView;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyFragment extends BaseStorageFragment {
    private PlatinumView platinumView;
    private GoldView goldView;
    private SilverView silverView;
    private BronzeView bronzeView;

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
        platinumView.save();
        goldView.save();
        silverView.save();
        bronzeView.save();
    }

    @Override
    protected void updateSettingsData() {
        platinumView.updateSettingsData();
        goldView.updateSettingsData();
        silverView.updateSettingsData();
        bronzeView.updateSettingsData();
    }
}
