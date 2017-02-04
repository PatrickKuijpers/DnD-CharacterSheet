package nl.tcilegnar.dndcharactersheet.Health;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageFragment;
import nl.tcilegnar.dndcharactersheet.Health.Settings.HpSettings;
import nl.tcilegnar.dndcharactersheet.Health.ViewGroup.HpIndicator;
import nl.tcilegnar.dndcharactersheet.R;

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
        return HpSettings.getInstance();
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        //        new AlertDialog.Builder(mContext, R.style.MyCustomDialogTheme);
        alert.setMessage("Enter new value below");
        alert.setTitle("Change " + hpType);

        final EditText edittext = new EditText(getActivity());
        edittext.setText(value);
        alert.setView(edittext);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int newValue = Integer.valueOf(edittext.getText().toString());
                hpIndicator.setNewHpValue(newValue, viewId);
            }
        });

        alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private String getHpTypeToChange(int viewId) {
        if (viewId == R.id.total_hp_value) {
            return "total HP";
        } else if (viewId == R.id.current_hp_value) {
            return "current HP";
        } else if (viewId == R.id.temp_hp_value) {
            return "temp HP";
        }
        return "value";
    }
}
