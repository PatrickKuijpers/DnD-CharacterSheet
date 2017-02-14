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

import static android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

public enum DrawerMenu implements OnNavigationItemSelectedListener {
    INSTANCE; // TODO: memory leak & elke activity start nog steeds alles opnieuw via init!

    private static final int CHARACTERS_GROUP_ID = 999;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerItemSelectedListener drawerItemSelectedListener;

    public void init(BaseActivity activity, Toolbar toolbar) {
        drawerItemSelectedListener = activity;

        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string
                .content_description_navigation_drawer_open, R.string.content_description_navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);

        initNavigationMenu();
    }

    private void initNavigationMenu() {
        Menu menu = navigationView.getMenu();
        for (DnDCharacter character : CharacterList.INSTANCE.getCharacters()) {
            int menuGroupId = CHARACTERS_GROUP_ID;
            int menuItemId = getCharacterId(character);
            String menuItemTitle = character.getName();
            MenuItem item = menu.add(menuGroupId, menuItemId, Menu.NONE, menuItemTitle);
            item.setCheckable(true);
        }
        selectCurrentCharacter();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private int getCharacterId(DnDCharacter character) {
        return Integer.valueOf(character.getId());
    }

    private void selectCurrentCharacter() {
        Integer drawerMenuItemId = getCharacterId(CurrentCharacter.DnDCharacter());
        MenuItem menuItem = navigationView.getMenu().findItem(drawerMenuItemId);
        menuItem.setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        int groupId = item.getGroupId();

        if (itemId == R.id.character_add) {
            drawerItemSelectedListener.onAddCharacter();
        } else if (itemId == R.id.character_delete) {
            drawerItemSelectedListener.onDeleteCharacter();
        } else if (groupId == CHARACTERS_GROUP_ID) {
            String characterId = String.valueOf(itemId);
            drawerItemSelectedListener.onSwitchCharacter(characterId);
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

    public void onDrawerIconClicked(BaseActivity activity) {
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

    public interface DrawerItemSelectedListener {
        void onAddCharacter();

        void onDeleteCharacter();

        void onSwitchCharacter(String characterId);
    }
}
