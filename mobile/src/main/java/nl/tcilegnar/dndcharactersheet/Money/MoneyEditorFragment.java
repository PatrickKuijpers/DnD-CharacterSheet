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
import nl.tcilegnar.dndcharactersheet.Utils.KeyboardUtil;
import nl.tcilegnar.dndcharactersheet.Utils.Sound;

public class MoneyEditorFragment extends BaseFragment implements OnClickListener {
    private PlatinumEditor platinumEditor;
    private GoldEditor goldEditor;
    private SilverEditor silverEditor;
    private BronzeEditor bronzeEditor;
    private MoneyChangedListener moneyChangedListener;
    private MoneyValues currentMoneyValues;

    private enum MoneyChangeMode {
        ADD, SUBSTRACT
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MoneyChangedListener) {
            moneyChangedListener = (MoneyChangedListener) context;
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
        view.findViewById(R.id.change_money_plus_button).setOnClickListener(this);
        view.findViewById(R.id.change_money_min_button).setOnClickListener(this);
    }

    @Override
    protected MoneySettings getSettings() {
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
        KeyboardUtil.hideKeyboard(getActivity());

        int viewId = view.getId();
        if (viewId == R.id.change_money_plus_button) {
            changeMoney(MoneyChangeMode.ADD);
        } else if (viewId == R.id.change_money_min_button) {
            changeMoney(MoneyChangeMode.SUBSTRACT);
        }
    }

    private void changeMoney(MoneyChangeMode mode) {
        MoneyValues moneyChangeValues = getMoneyChangeValues(mode);
        try {
            MoneyCalculator calculator = new MoneyCalculator(currentMoneyValues);
            MoneyValues newMoneyValues = calculator.calculateNewMoneyValues(moneyChangeValues);

            moneyChangedListener.onMoneyChanged(newMoneyValues);
            doMoneySoundEffect(moneyChangeValues);
        } catch (MoneyCalculator.MaxMoneyReachedException | MoneyCalculator.NotEnoughMoneyException e) {
            moneyChangedListener.onMoneyNotChanged();
        }
    }

    private void doMoneySoundEffect(MoneyValues moneyValues) {
        if (moneyValues.isMoneyIncrease()) {
            Sound.play(R.raw.sack_of_coins_picked_up_off_wood);
        } else if (moneyValues.isMoneyDecrease()) {
            if (moneyValues.isSingleCoinChanged()) {
                if (moneyValues.isHighValueCoinChanged()) {
                    Sound.play(R.raw.large_coin_fall_on_wood);
                } else {
                    Sound.playRandom(R.raw.single_coin_fall_on_wood, R.raw.single_coin_fall_on_concrete);
                }
            } else {
                Sound.playRandom(R.raw.many_coins_fall_on_wood, R.raw.many_coins_falling_on_concrete);
            }
        }
    }

    private MoneyValues getMoneyChangeValues(MoneyChangeMode mode) {
        removeFocusFromAllViews();

        MoneyValues moneyChangeValues = null;

        int platinumValue = platinumEditor.getMoneyValue();
        int goldValue = goldEditor.getMoneyValue();
        int silverValue = silverEditor.getMoneyValue();
        int bronzeValue = bronzeEditor.getMoneyValue();
        switch (mode) {
            case ADD:
                moneyChangeValues = new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
                break;
            case SUBSTRACT:
                moneyChangeValues = new MoneyValues(-platinumValue, -goldValue, -silverValue, -bronzeValue);
                break;
        }
        return moneyChangeValues;
    }

    private void removeFocusFromAllViews() {
        View viewInFocus = getActivity().getCurrentFocus();
        if (viewInFocus != null) {
            viewInFocus.clearFocus();
        }
    }

    public void setCurrentMoneyValues(MoneyValues currentMoneyValues) {
        this.currentMoneyValues = currentMoneyValues;
    }

    public interface MoneyChangedListener {
        void onMoneyChanged(MoneyValues newMoneyValues);

        void onMoneyNotChanged();
    }
}
