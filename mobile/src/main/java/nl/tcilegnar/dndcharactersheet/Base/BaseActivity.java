package nl.tcilegnar.dndcharactersheet.Base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import nl.tcilegnar.dndcharactersheet.Base.Settings.SettingsActivity;
import nl.tcilegnar.dndcharactersheet.FragmentManager;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;
import nl.tcilegnar.dndcharactersheet.characters.CharacterList;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;
import nl.tcilegnar.dndcharactersheet.characters.DnDCharacter;
import nl.tcilegnar.dndcharactersheet.characters.settings.CharacterSettings;

public abstract class BaseActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    protected final String LOGTAG = getClass().getSimpleName();

    private static final int CHARACTERS_GROUP_ID = 999;

    protected FragmentManager fragmentManager = new FragmentManager(this);

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

    @NonNull
    protected abstract BaseFragment getFirstFragment();

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
        Menu menu = navigationView.getMenu();
        for (DnDCharacter character : CharacterList.INSTANCE.getCharacters()) {
            String characterId = character.getId();
            int order = Menu.NONE;
            String characterName = character.getName();
            MenuItem item = menu.add(CHARACTERS_GROUP_ID, Integer.valueOf(characterId), order, characterName);
            item.setCheckable(true);
            if (characterId.equals(CurrentCharacter.DnDCharacter().getId())) {
                item.setChecked(true);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int itemId = item.getItemId();
        int groupId = item.getGroupId();

        CharacterSettings charSettings = CharacterSettings.getInstance();
        if (itemId == R.id.characters_header) {
            addCharacter();
        } else if (itemId == R.id.character_delete_test) {
            charSettings.removeCharacter(CurrentCharacter.DnDCharacter().getId()); //TODO
        } else if (groupId == CHARACTERS_GROUP_ID) {
            String characterId = String.valueOf(itemId);
            charSettings.switchCharacter(characterId);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addCharacter() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Character name");
        alert.setMessage("input the name of the new character");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                CharacterSettings.getInstance().addCharacter(name);
            }
        });

        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();

        Class<? extends SettingsActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            menuInflater.inflate(R.menu.settings_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startSettingsActivity();
            new Storage().print();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {
        Class<? extends SettingsActivity> settingsActivityClass = getSettingsActivityClass();
        if (settingsActivityClass != null) {
            Intent settingsActivity = new Intent(this, settingsActivityClass);
            startActivity(settingsActivity);
        }
    }

    @Nullable
    protected abstract Class<? extends SettingsActivity> getSettingsActivityClass();

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
}
