package nl.tcilegnar.dndcharactersheet.Base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.DrawerMenu;
import nl.tcilegnar.dndcharactersheet.FragmentManager;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.characters.settings.CharacterSettings;

public abstract class BaseActivity extends AppCompatActivity implements DrawerMenu.DrawerItemSelectedListener {
    protected final String LOGTAG = getClass().getSimpleName();

    protected FragmentManager fragmentManager = new FragmentManager(this);

    @NonNull
    protected abstract BaseFragment getFirstFragment();

    @Nullable
    protected abstract Class<? extends SettingsActivity> getSettingsActivityClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        initToolBar();

        if (savedInstanceState == null) {
            BaseFragment firstFragment = getFirstFragment();
            fragmentManager.addFirstFragment(firstFragment);
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerMenu.INSTANCE.init(this, toolbar);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        DrawerMenu.INSTANCE.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DrawerMenu.INSTANCE.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        Class<? extends SettingsActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            menuInflater.inflate(R.menu.settings_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;

        // TODO: if the toolbar is added to the ActionBarDrawerToggle constructor, only then:
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml.
        // TODO: if above not applies, then:
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO: werkt niet!
                DrawerMenu.INSTANCE.onDrawerIconClicked(this);
                handled = true;
                break;
            case R.id.action_settings:
                startSettingsActivity();
                handled = true;
                break;
        }
        return handled;
    }

    private void startSettingsActivity() {
        Class<? extends SettingsActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            Intent settingsActivity = new Intent(this, settingsActivityClass);
            startActivity(settingsActivity);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @Override
    public void onBackPressed() {
        if (DrawerMenu.INSTANCE.closeDrawer()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public void onAddCharacter() {
        CharacterSettings.getInstance().addCharacter(this);
    }

    @Override
    public void onDeleteCharacter() {
        CharacterSettings.getInstance().deleteCharacter(this);
    }

    @Override
    public void onSwitchCharacter(String characterId) {
        CharacterSettings.getInstance().switchCharacter(characterId);
    }
}
