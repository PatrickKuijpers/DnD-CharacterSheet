package nl.tcilegnar.dndcharactersheet.Activity;

import android.app.Activity;
import android.os.Bundle;

public class Settings extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new nl.tcilegnar.dndcharactersheet.Fragment.Settings()).commit();
	}
}
