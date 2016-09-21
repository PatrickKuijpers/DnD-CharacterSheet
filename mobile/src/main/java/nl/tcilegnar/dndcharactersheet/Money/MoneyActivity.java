package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.Base.BaseStorageActivity;
import nl.tcilegnar.dndcharactersheet.Money.Settings.MoneySettingsActivity;

public class MoneyActivity extends BaseStorageActivity {
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
            //            fragmentManager.addFirstFragment(getMoneyEditorFragment(), FragTag.MONEY_EDITOR.name());
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
}
