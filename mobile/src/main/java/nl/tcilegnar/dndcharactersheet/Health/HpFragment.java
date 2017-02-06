package nl.tcilegnar.dndcharactersheet.Health;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettings;
import nl.tcilegnar.dndcharactersheet.Health.ViewGroup.HpIndicator;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Res;

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

    public void showDialog(TextView clickedTextView) {
        final int viewId = clickedTextView.getId();
        String hpType = getHpTypeToChange(viewId);
        String value = clickedTextView.getText().toString();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(String.format(Res.getString(R.string.dialog_title_change_hp_values), hpType));
        dialog.setMessage(Res.getString(R.string.dialog_message_change_hp_values));

        final EditText edittext = getEditText(value);
        dialog.setView(edittext);

        dialog.setPositiveButton(Res.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int newValue = Integer.valueOf(edittext.getText().toString());
                hpIndicator.setNewHpValue(newValue, viewId);
            }
        });

        dialog.setNeutralButton(Res.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        dialog.show();
    }

    private EditText getEditText(String value) {
        final EditText edittext = new EditText(getActivity());
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        edittext.setText(value);
        edittext.setSelection(value.length());
        return edittext;
    }

    private String getHpTypeToChange(int viewId) {
        if (viewId == R.id.total_hp_value) {
            return Res.getString(R.string.total_hp);
        } else if (viewId == R.id.current_hp_value) {
            return Res.getString(R.string.current_hp);
        } else if (viewId == R.id.temp_hp_value) {
            return Res.getString(R.string.temp_hp);
        }
        return "value";
    }
}
