package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.R;

public class BasicInfoFragment extends BaseStorageFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info, container, false);
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onSaveData() {

    }
}
