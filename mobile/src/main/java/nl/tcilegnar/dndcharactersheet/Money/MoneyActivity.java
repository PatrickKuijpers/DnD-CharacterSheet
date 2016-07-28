package nl.tcilegnar.dndcharactersheet.Money;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.BaseStorageActivity;

public class MoneyActivity extends BaseStorageActivity {
    public enum FragTag {
        MONEY
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

    @Override
    protected void updateSettingsData() {
        getMoneyFragment().updateSettingsData();
    }
}
