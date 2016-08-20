package nl.tcilegnar.dndcharactersheet.MainMenu;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.CrashManagerListener;
import net.hockeyapp.android.UpdateManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import nl.tcilegnar.dndcharactersheet.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceActivity;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.Log;
import nl.tcilegnar.dndcharactersheet.Utils.MyBuildConfig;
import nl.tcilegnar.dndcharactersheet.Utils.MyProperties;

public class MainMenuActivity extends BaseActivity {
    MyBuildConfig myBuildConfig = new MyBuildConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerHockeyAppManagers();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_content, new MainMenuFragment()).commit();
        }
    }

    private void registerHockeyAppManagers() {
        try {
            String hockeyAppId = MyProperties.getHockeyAppId();
            checkForCrashes(hockeyAppId);
            checkForUpdates(hockeyAppId);
        } catch (Exception e) {
            Log.w(LOGTAG, "Helaas geen crash reports & andere handige tools van HockeyApp");
            e.printStackTrace();
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
        if (myBuildConfig.isDebug()) {
            UpdateManager.register(this, hockeyAppId, true);
        }
    }

    private void hockeyAppUnregisterManagers() {
        if (myBuildConfig.isDebug()) {
            UpdateManager.unregister();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hockeyAppUnregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hockeyAppUnregisterManagers();
    }

    public void startLevelAndExperience() {
        Intent experienceActivity = new Intent(this, ExperienceActivity.class);
        startActivity(experienceActivity);
    }
}
