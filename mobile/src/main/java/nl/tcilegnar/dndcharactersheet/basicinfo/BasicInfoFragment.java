package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.characters.Current;

public class BasicInfoFragment extends BaseStorageFragment {
    private EditText characterNameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        characterNameView = (EditText) view.findViewById(R.id.character_name);
    }

    @Override
    protected void onLoadData() {
        String characterName = Current.DnDCharacter().getName();
        characterNameView.setText(characterName);
    }

    @Override
    protected void onSaveData() {
        String characterName = characterNameView.getText().toString();
        Current.DnDCharacter().setName(characterName);
    }
}
