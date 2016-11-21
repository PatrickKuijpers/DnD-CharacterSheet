package nl.tcilegnar.dndcharactersheet.Base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.FragmentManager;
import nl.tcilegnar.dndcharactersheet.R;

public abstract class BaseActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    protected final String LOGTAG = getClass().getSimpleName();
    protected FragmentManager fragmentManager = new FragmentManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        initToolBar();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawerMenu(toolbar);
    }

    private void initDrawerMenu(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string
                .content_description_navigation_drawer_open, R.string.content_description_navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        initNavigationView();
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();

        Class<? extends PreferenceActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            menuInflater.inflate(R.menu.settings_menu, menu);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {
        Class<? extends PreferenceActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            Intent settingsActivity = new Intent(this, settingsActivityClass);
            Bundle animation = ActivityOptions.makeCustomAnimation(App.getContext(), R.anim.anim_enter_from_right, R
                    .anim.anim_exit_to_left).toBundle();

            startActivity(settingsActivity, animation);
        }
    }

    protected abstract Class<? extends PreferenceActivity> getSettingsActivityClass();

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //        if (id == R.id.nav_char_A) {
        //        } else if (id == R.id.nav_char_B) {
        //        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
