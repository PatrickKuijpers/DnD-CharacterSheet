package nl.tcilegnar.dndcharactersheet.enums;

import android.support.v4.app.Fragment;

import nl.tcilegnar.dndcharactersheet.Experience.ExperienceFragment;
import nl.tcilegnar.dndcharactersheet.Health.HpFragment;
import nl.tcilegnar.dndcharactersheet.MainMenu.MainMenuFragment;
import nl.tcilegnar.dndcharactersheet.Money.MoneyEditorFragment;
import nl.tcilegnar.dndcharactersheet.Money.MoneyFragment;
import nl.tcilegnar.dndcharactersheet.abilities.AbilitiesFragment;
import nl.tcilegnar.dndcharactersheet.basicinfo.BasicInfoFragment;

public enum FragTag {
    MAIN_MENU(new MainMenuFragment()),
    BASIC_INFO(new BasicInfoFragment()),
    EXPERIENCE(new ExperienceFragment()),
    ABILITIES(new AbilitiesFragment()),
    HP(new HpFragment()),
    MONEY(new MoneyFragment()),
    MONEY_EDITOR(new MoneyEditorFragment());

    private final Fragment fragment;

    FragTag(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment get() {
        return fragment;
    }

    public String tag() {
        return name();
    }
}
