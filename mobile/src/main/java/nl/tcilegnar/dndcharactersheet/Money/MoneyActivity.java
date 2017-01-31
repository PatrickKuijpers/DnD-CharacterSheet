package nl.tcilegnar.dndcharactersheet.Money;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyEditorFragment.MoneyChangedListener;
import nl.tcilegnar.dndcharactersheet.Money.MoneyFragment.ChangeMoneyListener;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettingsActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class MoneyActivity extends BaseStorageActivity implements ChangeMoneyListener, MoneyChangedListener {
    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.MONEY;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return MoneySettingsActivity.class;
    }

    @Override
    public void onChangeMoney(MoneyValues currentMoneyValues) {
        startMoneyEditor(currentMoneyValues);
    }

    private void startMoneyEditor(MoneyValues currentMoneyValues) {
        FragTag moneyEditorFragTag = FragTag.MONEY_EDITOR;
        MoneyEditorFragment moneyEditorFragment = (MoneyEditorFragment) moneyEditorFragTag.get();
        moneyEditorFragment.setCurrentMoneyValues(currentMoneyValues);
        fragmentManager.replaceFragment(moneyEditorFragTag);
    }

    @Override
    public void onMoneyChanged(MoneyValues newMoneyValues) {
        MoneyFragment moneyFragment = (MoneyFragment) fragmentManager.getExistingFragment(FragTag.MONEY);
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
