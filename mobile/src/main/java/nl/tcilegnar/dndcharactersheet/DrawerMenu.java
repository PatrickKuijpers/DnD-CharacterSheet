package nl.tcilegnar.dndcharactersheet;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import nl.tcilegnar.dndcharactersheet.Base.BaseActivity;
import nl.tcilegnar.dndcharactersheet.characters.CharacterList;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;
import nl.tcilegnar.dndcharactersheet.characters.DnDCharacter;
import nl.tcilegnar.dndcharactersheet.characters.settings.CharacterSettings;

import static android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

public enum DrawerMenu implements OnNavigationItemSelectedListener {
    INSTANCE;

    private static final int CHARACTERS_GROUP_ID = 999;
    private BaseActivity activity;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    public void init(BaseActivity baseActivity, Toolbar toolbar) {
        this.activity = baseActivity;

        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string
                .content_description_navigation_drawer_open, R.string.content_description_navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        initNavigationView();
    }

    private void initNavigationView() {
        Menu menu = navigationView.getMenu();
        for (DnDCharacter character : CharacterList.INSTANCE.getCharacters()) {
            String characterId = character.getId();
            String characterName = character.getName();
            MenuItem item = menu.add(CHARACTERS_GROUP_ID, Integer.valueOf(characterId), Menu.NONE, characterName);
            item.setCheckable(true);
        }
        selectCurrentCharacter();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void selectCurrentCharacter() {
        MenuItem menuItem = navigationView.getMenu().findItem(Integer.valueOf(CurrentCharacter.DnDCharacter().getId()));
        menuItem.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        int groupId = item.getGroupId();

        CharacterSettings characterSettings = CharacterSettings.getInstance();
        if (itemId == R.id.character_add) {
            characterSettings.addCharacter(activity);
        } else if (itemId == R.id.character_delete) {
            characterSettings.deleteCharacter(activity);
        } else if (groupId == CHARACTERS_GROUP_ID) {
            String characterId = String.valueOf(itemId);
            characterSettings.switchCharacter(characterId);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean closeDrawer() {
        boolean hasBeenClosed = false;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            hasBeenClosed = true;
        }
        return hasBeenClosed;
    }

    public void syncState() {
        drawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void onDrawerIconClicked() {
        //if (drawerToggle.onOptionsItemSelected(item)) {
        //    return true;
        //}
        if (drawerToggle.isDrawerIndicatorEnabled()) {
            // Default gedrag is het openen of sluiten van het DrawerMainMenu
            // TODO: werkt nog niet... http://stackoverflow
            // .com/questions/30824324/clicking-hamburger-icon-on-toolbar-does-not-open-navigation-drawer
            // en
            // http://stackoverflow.com/questions/26588917/appcompat-v7-toolbar-onoptionsitemselected-not-called
            // /28177091#28177091
            // drawerToggle.toggleDrawerMenu();
        } else {
            // Als de drawerLayout-icon niet enabled is, dan is deze waarschijnlijk (momenteel) een terug-knop
            activity.onBackPressed();
        }
    }
}
