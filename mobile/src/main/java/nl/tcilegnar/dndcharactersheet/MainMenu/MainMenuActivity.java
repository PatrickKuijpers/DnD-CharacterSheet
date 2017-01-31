package nl.tcilegnar.dndcharactersheet.MainMenu;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.UpdateManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceActivity;
import nl.tcilegnar.dndcharactersheet.Health.HpActivity;
import nl.tcilegnar.dndcharactersheet.MainMenu.Settings.MainSettingsActivity;
import nl.tcilegnar.dndcharactersheet.Money.MoneyActivity;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Log;
import nl.tcilegnar.dndcharactersheet.Utils.MyBuildConfig;
import nl.tcilegnar.dndcharactersheet.Utils.MyProperties;
import nl.tcilegnar.dndcharactersheet.abilities.AbilitiesActivity;
import nl.tcilegnar.dndcharactersheet.basicinfo.BasicInfoActivity;
import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class MainMenuActivity extends BaseActivity {
    MyBuildConfig myBuildConfig = new MyBuildConfig();

    @NonNull
    protected FragTag getFirstFragTag() {
        return FragTag.MAIN_MENU;
    }

    @Override
    protected Class<? extends SettingsActivity> getSettingsActivityClass() {
        return MainSettingsActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHockeyAppManagers();
    }

    private void registerHockeyAppManagers() {
        if (!myBuildConfig.isProduction()) {
            try {
                String hockeyAppId = MyProperties.getHockeyAppId();
                checkForCrashes(hockeyAppId);
                checkForUpdates(hockeyAppId);
            } catch (Exception e) {
                Log.w(LOGTAG, "Helaas geen crash reports & andere handige tools van HockeyApp");
                e.printStackTrace();
            }
        }
    }

    private void checkForCrashes(String hockeyAppId) {
        CrashManager.register(this, hockeyAppId, new CrashManagerListener() {
            public boolean shouldAutoUploadCrashes() {
                return true;
            }
        });
    }

    private void checkForUpdates(String hockeyAppId) {
        UpdateManager.register(this, hockeyAppId, true);
    }

    private void unregisterHockeyAppManagers() {
        if (!myBuildConfig.isProduction()) {
            UpdateManager.unregister();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterHockeyAppManagers();
    }

    public void onMainMenuButtonClicked(int viewId) {
        if (viewId == R.id.main_menu_button_basic_character_info) {
            startBasicInfo();
        } else if (viewId == R.id.main_menu_button_level_and_experience) {
            startLevelAndExperience();
        } else if (viewId == R.id.main_menu_button_abilities) {
            startAbilities();
        } else if (viewId == R.id.main_menu_button_hp) {
            showComingSoon();
        } else if (viewId == R.id.main_menu_button_money) {
            startMoney();
        }
    }

    private void showComingSoon() {
        Toast.makeText(App.getContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
    }

    public void startBasicInfo() {
        Intent basicInfoActivity = new Intent(this, BasicInfoActivity.class);
        startActivity(basicInfoActivity);
    }

    public void startLevelAndExperience() {
        Intent experienceActivity = new Intent(this, ExperienceActivity.class);
        startActivity(experienceActivity);
    }

    public void startAbilities() {
        Intent abilitiesActivity = new Intent(this, AbilitiesActivity.class);
        startActivity(abilitiesActivity);
    }

    public void startHp() {
        Intent hpActivity = new Intent(this, HpActivity.class);
        startActivity(hpActivity);
    }

    public void startMoney() {
        Intent moneyActivity = new Intent(this, MoneyActivity.class);
        startActivity(moneyActivity);
    }

    @Override
    protected void onLeaveThisActivity() {
        // Don't use an exit animation when leaving the main activity!
    }
}
