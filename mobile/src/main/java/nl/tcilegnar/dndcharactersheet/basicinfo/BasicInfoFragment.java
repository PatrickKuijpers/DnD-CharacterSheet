package nl.tcilegnar.dndcharactersheet.basicinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;

import static nl.tcilegnar.dndcharactersheet.characters.DnDCharacter.Gender;

public class BasicInfoFragment extends BaseStorageFragment {
    private EditText characterNameView;
    private EditText characterRaceView;
    private EditText characterClassView;
    private EditText characterAlignmentView;
    private EditText characterDeityView;
    private Spinner characterGenderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        characterNameView = (EditText) view.findViewById(R.id.character_name);
        characterRaceView = (EditText) view.findViewById(R.id.character_race);
        characterClassView = (EditText) view.findViewById(R.id.character_class);
        characterAlignmentView = (EditText) view.findViewById(R.id.character_alignment);
        characterDeityView = (EditText) view.findViewById(R.id.character_deity);
        characterGenderView = (Spinner) view.findViewById(R.id.character_gender);

        initSpinners();
    }

    private void initSpinners() {
        Gender[] values = Gender.values();
        ArrayAdapter<Gender> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        characterGenderView.setAdapter(adapter);
    }

    @Override
    protected void onLoadData() {
        CurrentCharacter character = CurrentCharacter.DnDCharacter();
        String characterName = character.getName();
        String characterRace = character.getRace();
        String characterClass = character.getClassName();
        String characterAlignment = character.getAlignment();
        String characterDeity = character.getDeity();
        Gender characterGender = character.getGender();
        characterNameView.setText(characterName);
        characterRaceView.setText(characterRace);
        characterClassView.setText(characterClass);
        characterAlignmentView.setText(characterAlignment);
        characterDeityView.setText(characterDeity);
        //        characterGenderView.setText(characterGender.toString());
    }

    @Override
    protected void onSaveData() {
        CurrentCharacter character = CurrentCharacter.DnDCharacter();
        String characterName = characterNameView.getText().toString();
        String characterRace = characterRaceView.getText().toString();
        String characterClass = characterClassView.getText().toString();
        String characterAlignment = characterAlignmentView.getText().toString();
        String characterDeity = characterDeityView.getText().toString();
        Gender characterGender = Gender.valueOf(characterGenderView.getSelectedItem().toString());
        character.setName(characterName);
        character.setRace(characterRace);
        character.setClassName(characterClass);
        character.setAlignment(characterAlignment);
        character.setDeity(characterDeity);
        character.setGender(characterGender);
    }
}
