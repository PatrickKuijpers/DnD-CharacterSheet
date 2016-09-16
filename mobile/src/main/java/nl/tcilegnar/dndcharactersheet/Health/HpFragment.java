package nl.tcilegnar.dndcharactersheet.Health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettings;
import nl.tcilegnar.dndcharactersheet.Health.ViewGroup.HpIndicator;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Main.MainSettings;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class HpFragment extends BaseStorageFragment {
    private HpIndicator hpIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hp, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        hpIndicator = (HpIndicator) view.findViewById(R.id.hp_indicator_view);
        Hp hp = hpIndicator.getHp();

    }

    @Override
    protected Settings getSettings() {
        return HpSettings.getInstance();
    }

    @Override
    protected void onLoadData() {
    }

    @Override
    protected void onSaveData() {
        hpIndicator.save();
    }

    @Override
    protected void updateSettingsData() {
    }
}
