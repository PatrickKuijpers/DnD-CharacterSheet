package nl.tcilegnar.dndcharactersheet;

import android.support.v4.app.Fragment;

public abstract class BaseStorageFragment extends Fragment {
    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract void onSaveData();
}
