package nl.tcilegnar.dndcharactersheet;

import nl.tcilegnar.dndcharactersheet.Storage.Settings;

public abstract class BaseStorageFragment extends BaseFragment {
    protected Settings settings = new Settings();

    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract void onSaveData();
}
