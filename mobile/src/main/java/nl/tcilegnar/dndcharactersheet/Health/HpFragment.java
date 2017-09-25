package nl.tcilegnar.dndcharactersheet.Health;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettings;
import nl.tcilegnar.dndcharactersheet.Health.ViewGroup.HpIndicator;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

import static nl.tcilegnar.dndcharactersheet.Health.ViewGroup.HpIndicator.HpType;

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
        hpIndicator.setChangeHpValueCallback(this);
    }

    @Override
    protected HpSettings getSettings() {
        return null; //HpSettings.getInstance();
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

    public void showDialog(String value, HpType hpType) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        setTitleAndMessage(dialog, hpType);

        EditText edittext = getEditText(value, hpType);
        dialog.setView(edittext);

        setButtons(dialog, hpType, edittext);

        dialog.show();
    }

    private void setTitleAndMessage(AlertDialog.Builder dialog, HpType hpType) {
        dialog.setTitle(String.format(Res.getString(R.string.dialog_title_change_hp_values), hpType.toString()));
        if (hpType.equals(HpType.CurrentHp)) {
            dialog.setMessage(Res.getString(R.string.dialog_message_change_current_hp_values));
        } else {
            dialog.setMessage(Res.getString(R.string.dialog_message_change_hp_values));
        }
    }

    private EditText getEditText(String value, HpType hpType) {
        final EditText edittext = new EditText(getActivity());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (!hpType.equals(HpType.CurrentHp)) {
            edittext.setText(value);
            edittext.setSelection(value.length());
        }
        return edittext;
    }

    private void setButtons(AlertDialog.Builder dialog, final HpType hpType, final EditText edittext) {
        switch (hpType) {
            case TotalHp:
            case TempHp:
                dialog.setPositiveButton(Res.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int newValue = getInputNumber(edittext);
                        validateAndSetNewValue(newValue, hpType);
                    }
                });
                break;
            case CurrentHp:
                dialog.setPositiveButton(Res.getString(R.string.plus), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int newValue = getInputNumber(edittext);
                        validateAndSetNewValue(newValue, hpType);
                    }
                });
                dialog.setNegativeButton(Res.getString(R.string.min), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int newValue = -getInputNumber(edittext);
                        validateAndSetNewValue(newValue, hpType);
                    }
                });
                break;
        }

        dialog.setNeutralButton(Res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
    }

    private int getInputNumber(EditText edittext) {
        String stringValue = edittext.getText().toString();
        if (stringValue.length() > 0) {
            return Integer.valueOf(stringValue);
        } else {
            return 0;
        }
    }

    private void validateAndSetNewValue(int newValue, HpType hpType) {
        if (isValidValue(newValue, hpType)) {
            hpIndicator.setNewHpValue(newValue, hpType);
        } else {
            String errorMessage = getErrorMessage(newValue, hpType);
            if (!errorMessage.isEmpty()) {
                Toast.makeText(App.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidValue(int newValue, HpType hpType) {
        switch (hpType) {
            case TotalHp:
                return newValue > 0;
            case CurrentHp:
                return newValue != 0;
            case TempHp:
                return newValue >= 0;
            default:
                return false;
        }
    }

    private String getErrorMessage(int newValue, HpType hpType) {
        switch (hpType) {
            case TotalHp:
                return String.format(Res.getString(R.string.total_hp_input_not_valid), newValue);
            case CurrentHp:
                return "";
            case TempHp:
                return String.format(Res.getString(R.string.temp_hp_input_not_valid), newValue);
            default:
                return Res.getString(R.string.unexpected_error);
        }
    }
}
