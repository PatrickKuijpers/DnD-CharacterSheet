package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import nl.tcilegnar.dndcharactersheet.BaseActivity;
import nl.tcilegnar.dndcharactersheet.Experience.ExperienceActivity;
import nl.tcilegnar.dndcharactersheet.R;

public class MainMenuActivity extends BaseActivity {
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
}
