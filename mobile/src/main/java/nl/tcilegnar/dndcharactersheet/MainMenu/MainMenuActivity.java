package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nl.tcilegnar.dndcharactersheet.Experience.ExperienceActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent experienceActivity = new Intent(this, ExperienceActivity.class);
        startActivity(experienceActivity);
        finish();
    }
}
