package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyEditorFragment.ConfirmChangeMoneyListener;
import nl.tcilegnar.dndcharactersheet.Money.MoneyFragment.ChangeMoneyListener;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettingsActivity;

import static nl.tcilegnar.dndcharactersheet.FragmentManager.Anim.SLIDE_RIGHT_TO_LEFT;

public class MoneyActivity extends BaseStorageActivity implements ChangeMoneyListener, ConfirmChangeMoneyListener {
    private MoneyChangeMode moneyChangeMode = MoneyChangeMode.NO_CHANGE;

    public enum MoneyChangeMode {
        NO_CHANGE, ADD, SUBSTRACT
    }

    @Override
    protected Class<? extends PreferenceActivity> getSettingsActivityClass() {
        return MoneySettingsActivity.class;
    }

    public enum FragTag {
        MONEY,
        MONEY_EDITOR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentManager.addFirstFragment(getMoneyFragment(), FragTag.MONEY.name());
        }
    }

    public MoneyFragment getMoneyFragment() {
        String tag = FragTag.MONEY.name();
        MoneyFragment moneyFragment = fragmentManager.getFragment(MoneyFragment.class, tag);
        if (moneyFragment == null) {
            moneyFragment = new MoneyFragment();
        }
        return moneyFragment;
    }

    public MoneyEditorFragment getMoneyEditorFragment() {
        String tag = FragTag.MONEY_EDITOR.name();
        MoneyEditorFragment moneyEditorFragment = fragmentManager.getFragment(MoneyEditorFragment.class, tag);
        if (moneyEditorFragment == null) {
            moneyEditorFragment = new MoneyEditorFragment();
        }
        return moneyEditorFragment;
    }

    @Override
    public void onAddMoneyClicked() {
        moneyChangeMode = MoneyChangeMode.ADD;
        startMoneyEditor();
    }

    @Override
    public void onSubstractMoneyClicked() {
        moneyChangeMode = MoneyChangeMode.SUBSTRACT;
        startMoneyEditor();
    }

    private void startMoneyEditor() {
        MoneyEditorFragment moneyEditorFragment = getMoneyEditorFragment();
        fragmentManager.replaceFragment(moneyEditorFragment, FragTag.MONEY_EDITOR.name(), true, SLIDE_RIGHT_TO_LEFT);
    }

    @Override
    public void onConfirmChangeMoney(MoneyValues changeMoneyValues) {
        changeMoney(changeMoneyValues);
        onBackPressed();
    }

    private void changeMoney(MoneyValues changeMoneyValues) {
        MoneyFragment moneyFragment = getMoneyFragment();
        MoneyValues currentMoneyValues = moneyFragment.getMoneyValues();

        // TODO: tijdelijke snelle oplossing, kan mooier!
        if (moneyChangeMode == MoneyChangeMode.SUBSTRACT) {
            changeMoneyValues = convertAllMoneyValuesToMinus(changeMoneyValues);
        }

        try {
            MoneyCalculator calculator = new MoneyCalculator(currentMoneyValues);
            MoneyValues newMoneyValues = calculator.calculateNewMoneyValues(changeMoneyValues);

            moneyFragment.changeMoney(newMoneyValues);
        } catch (MoneyCalculator.NotEnoughMoneyException e) {
            //TODO
        }

        // Reset
        moneyChangeMode = MoneyChangeMode.NO_CHANGE;
    }

    private MoneyValues convertAllMoneyValuesToMinus(MoneyValues newMoneyValues) {
        int platinumValue = -newMoneyValues.getPlatinumValue();
        int goldValue = -newMoneyValues.getGoldValue();
        int silverValue = -newMoneyValues.getSilverValue();
        int bronzeValue = -newMoneyValues.getBronzeValue();
        return new MoneyValues(platinumValue, goldValue, silverValue, bronzeValue);
    }
}
