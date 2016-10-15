package nl.tcilegnar.dndcharactersheet.Base;

public abstract class BaseStorageFragment extends BaseFragment {

    @Override
    public void onResume() {
        super.onResume();
        onLoadData();
    }

    @Override
    public void onPause() {
        onSaveData();
        super.onPause();
    }

    protected abstract void onLoadData();

    protected abstract void onSaveData();
}
