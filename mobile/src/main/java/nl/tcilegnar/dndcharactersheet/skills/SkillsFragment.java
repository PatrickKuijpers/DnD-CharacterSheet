package nl.tcilegnar.dndcharactersheet.skills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Base.Settings.Settings;
import nl.tcilegnar.dndcharactersheet.R;

public class SkillsFragment extends BaseStorageFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_skills, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
    }

    @Override
    protected Settings getSettings() {
        return null;//SkillsSettings.getInstance();
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
