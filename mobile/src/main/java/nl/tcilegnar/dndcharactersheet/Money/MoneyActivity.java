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
        MONEY_EDITOR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentManager.addFirstFragment(getMoneyEditorFragment(), FragTag.MONEY_EDITOR.name());
        }
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
