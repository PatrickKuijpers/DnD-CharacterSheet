package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyEditorFragment.MoneyChangedListener;
import nl.tcilegnar.dndcharactersheet.Money.MoneyFragment.ChangeMoneyListener;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettingsActivity;

public class MoneyActivity extends BaseStorageActivity implements ChangeMoneyListener, MoneyChangedListener {
    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
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
    public void onChangeMoney(MoneyValues currentMoneyValues) {
        startMoneyEditor(currentMoneyValues);
    }

    private void startMoneyEditor(MoneyValues currentMoneyValues) {
        MoneyEditorFragment moneyEditorFragment = getMoneyEditorFragment();
        moneyEditorFragment.setCurrentMoneyValues(currentMoneyValues);
        fragmentManager.replaceFragment(moneyEditorFragment, FragTag.MONEY_EDITOR.name());
    }

    @Override
    public void onMoneyChanged(MoneyValues newMoneyValues) {
        MoneyFragment moneyFragment = getMoneyFragment();
        stopMoneyEditorAndGoBackToMoneyFragment();
        moneyFragment.changeMoney(newMoneyValues); // TODO: mogelijke bug?
    }

    @Override
    public void onMoneyNotChanged() {
        // TODO (exceptions verwerken) ?
        stopMoneyEditorAndGoBackToMoneyFragment();
    }

    private void stopMoneyEditorAndGoBackToMoneyFragment() {
        onBackPressed();
    }
}
