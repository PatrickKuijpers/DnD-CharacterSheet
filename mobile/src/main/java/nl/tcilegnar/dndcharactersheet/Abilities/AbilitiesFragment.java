package nl.tcilegnar.dndcharactersheet.Abilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import nl.tcilegnar.dndcharactersheet.Abilities.Adapter.AbilitiesAdapter;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.R;

public class AbilitiesFragment extends BaseStorageFragment {
    private GridView abilitiesGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abilities, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        abilitiesGrid = (GridView) view.findViewById(R.id.gridview_abilities);
        abilitiesGrid.setAdapter(new AbilitiesAdapter(getActivity()));
    }

    @Override
    protected MoneySettings getSettings() {
        return null;//AbilitiesSettings.getInstance();
    }

    @Override
    protected void updateSettingsData() {
    }

    @Override
    protected void onLoadData() {
    }

    @Override
    protected void onSaveData() {
    }
}
