package nl.tcilegnar.dndcharactersheet.Money;

import android.support.annotation.NonNull;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyEditorFragment.MoneyChangedListener;
import nl.tcilegnar.dndcharactersheet.Money.MoneyFragment.ChangeMoneyListener;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettingsActivity;

public class MoneyActivity extends BaseStorageActivity implements ChangeMoneyListener, MoneyChangedListener {
    @NonNull
    protected BaseFragment getFirstFragment() {
        return new MoneyFragment();
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
        MoneyEditorFragment moneyEditorFragment = new MoneyEditorFragment();
        moneyEditorFragment.setCurrentMoneyValues(currentMoneyValues);
        fragmentManager.replaceFragment(moneyEditorFragment);
    }

    @Override
    public void onMoneyChanged(MoneyValues newMoneyValues) {
        MoneyFragment moneyFragment = (MoneyFragment) fragmentManager.getExistingFragment(new MoneyFragment());
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
