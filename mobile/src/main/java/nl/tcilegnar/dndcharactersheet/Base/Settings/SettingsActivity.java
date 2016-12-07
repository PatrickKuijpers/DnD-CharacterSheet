package nl.tcilegnar.dndcharactersheet.Base.Settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import nl.tcilegnar.dndcharactersheet.R;

public abstract class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, getSettingsFragment()).commit();
    }

    protected abstract SettingsFragment getSettingsFragment();

    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
