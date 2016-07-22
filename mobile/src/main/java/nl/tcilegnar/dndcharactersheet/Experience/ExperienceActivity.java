package nl.tcilegnar.dndcharactersheet.Experience;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import nl.tcilegnar.dndcharactersheet.BaseActivity;
import nl.tcilegnar.dndcharactersheet.R;

public class ExperienceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_content, new ExperienceFragment()).commit();
        }
    }
}
