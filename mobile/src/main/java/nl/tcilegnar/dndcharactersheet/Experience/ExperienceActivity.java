package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;

import nl.tcilegnar.dndcharactersheet.BaseActivity;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().replace(R.id.activity_content, new ExperienceFragment()).commit();
		}
	}
}
