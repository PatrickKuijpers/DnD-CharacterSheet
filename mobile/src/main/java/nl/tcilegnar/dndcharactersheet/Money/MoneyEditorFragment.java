package nl.tcilegnar.dndcharactersheet.Money;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettings;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.BronzeEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.GoldEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.PlatinumEditor;
import nl.tcilegnar.dndcharactersheet.Money.ViewGroup.SilverEditor;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Settings;

public class MoneyEditorFragment extends BaseFragment implements OnClickListener {
    private PlatinumEditor platinumEditor;
    private GoldEditor goldEditor;
    private SilverEditor silverEditor;
    private BronzeEditor bronzeEditor;
    private ConfirmChangeMoneyListener confirmChangeMoneyListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ConfirmChangeMoneyListener) {
            confirmChangeMoneyListener = (ConfirmChangeMoneyListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_money_editor, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initClickListeners(view);
    }

    private void initViews(View view) {
        platinumEditor = (PlatinumEditor) view.findViewById(R.id.platinum_editor);
        goldEditor = (GoldEditor) view.findViewById(R.id.gold_editor);
        silverEditor = (SilverEditor) view.findViewById(R.id.silver_editor);
        bronzeEditor = (BronzeEditor) view.findViewById(R.id.bronze_editor);
    }

    private void initClickListeners(View view) {
        view.findViewById(R.id.change_money_ok_button).setOnClickListener(this);
    }

    @Override
    protected Settings getSettings() {
        return MoneySettings.getInstance();
    }

    @Override
    protected void updateSettingsData() {
        platinumEditor.updateSettingsData();
        goldEditor.updateSettingsData();
        silverEditor.updateSettingsData();
        bronzeEditor.updateSettingsData();
    }

    @Override
    public void onClick(View view) {
        int platinumValue = platinumEditor.getMoneyValue();
        int goldValue = goldEditor.getMoneyValue();
        int silverValue = silverEditor.getMoneyValue();
        int bronzeValue = bronzeEditor.getMoneyValue();
        MoneyValues changeMoneyValues = new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
        confirmChangeMoneyListener.onConfirmChangeMoney(changeMoneyValues);
    }

    public interface ConfirmChangeMoneyListener {
        void onConfirmChangeMoney(MoneyValues changeMoneyValues);
    }
}
