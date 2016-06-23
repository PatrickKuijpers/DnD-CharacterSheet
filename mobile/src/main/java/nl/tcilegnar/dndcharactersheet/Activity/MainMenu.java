package nl.tcilegnar.dndcharactersheet.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent experienceActivity = new Intent(this, ExperienceActivity.class);
		startActivity(experienceActivity);
		finish();
	}
}
