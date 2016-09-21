package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverEditor;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneyEditorFragment extends BaseStorageFragment {
    private PlatinumEditor platinumEditor;
    private GoldEditor goldEditor;
    private SilverEditor silverEditor;
    private BronzeEditor bronzeEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        platinumEditor = (PlatinumEditor) view.findViewById(R.id.platinum_editor);
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
        platinumEditor.load();
        goldEditor.load();
        silverEditor.load();
        bronzeEditor.load();
    }

    @Override
    protected void onSaveData() {
        platinumEditor.save();
        goldEditor.save();
        silverEditor.save();
        bronzeEditor.save();
    }

    @Override
    protected void updateSettingsData() {
        platinumEditor.updateSettingsData();
        goldEditor.updateSettingsData();
        silverEditor.updateSettingsData();
        bronzeEditor.updateSettingsData();
    }
}