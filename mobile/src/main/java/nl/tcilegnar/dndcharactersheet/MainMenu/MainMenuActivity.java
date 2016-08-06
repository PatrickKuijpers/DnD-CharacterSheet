package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentTransaction;

import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyActivity;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Settings.Main.MainSettingsActivity;

public class MainMenuActivity extends BaseActivity {
    @Override
    protected Class<? extends PreferenceActivity> getSettingsActivityClass() {
        return MainSettingsActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_content, new MainMenuFragment()).commit();
        }
    }

    public void startLevelAndExperience() {
        Intent experienceActivity = new Intent(this, ExperienceActivity.class);
        startActivity(experienceActivity);
    }

    public void startMoney() {
        Intent moneyActivity = new Intent(this, MoneyActivity.class);
        startActivity(moneyActivity);
    }
}
