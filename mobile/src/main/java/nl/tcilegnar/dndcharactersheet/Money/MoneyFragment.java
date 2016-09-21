package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumView;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverEditor;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneyFragment extends BaseStorageFragment {
    private PlatinumView platinumView;
    private GoldEditor goldEditor;
    private SilverEditor silverEditor;
    private BronzeEditor bronzeEditor;

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
        goldEditor = (GoldEditor) view.findViewById(R.id.gold_editor);
        silverEditor = (SilverEditor) view.findViewById(R.id.silver_editor);
        bronzeEditor = (BronzeEditor) view.findViewById(R.id.bronze_editor);
    }

    @Override
    protected Settings getSettings() {
        return MoneySettings.getInstance();
    }

    @Override
    protected void onLoadData() {
        platinumView.load();
        goldEditor.load();
        silverEditor.load();
        bronzeEditor.load();
    }

    @Override
    protected void onSaveData() {
        platinumView.save();
        goldEditor.save();
        silverEditor.save();
        bronzeEditor.save();
    }

    @Override
    protected void updateSettingsData() {
        platinumView.updateSettingsData();
        goldEditor.updateSettingsData();
        silverEditor.updateSettingsData();
        bronzeEditor.updateSettingsData();
    }
}
