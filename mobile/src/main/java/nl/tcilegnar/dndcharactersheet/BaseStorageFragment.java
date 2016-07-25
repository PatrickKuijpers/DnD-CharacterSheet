package nl.tcilegnar.dndcharactersheet;

public abstract class BaseStorageFragment extends BaseFragment {
    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract void onSaveData();
}
