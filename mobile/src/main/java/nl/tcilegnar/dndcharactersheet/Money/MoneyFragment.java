package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.MoneyView.GoldView;
import nl.tcilegnar.dndcharactersheet.Money.MoneyView.SilverView;
import nl.tcilegnar.dndcharactersheet.R;

public class MoneyFragment extends BaseStorageFragment {
    private GoldView goldView;
    private SilverView silverView;

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
        goldView = (GoldView) view.findViewById(R.id.gold_view);
        silverView = (SilverView) view.findViewById(R.id.silver_view);
    }

    @Override
    protected void updateSettingsData() {
    }

    @Override
    protected void onSaveData() {
    }
}
