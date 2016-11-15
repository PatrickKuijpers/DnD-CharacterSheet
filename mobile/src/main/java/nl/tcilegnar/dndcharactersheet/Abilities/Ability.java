package nl.tcilegnar.dndcharactersheet.Abilities;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static nl.tcilegnar.dndcharactersheet.Storage.Storage.Key;

public enum Ability {
    STRENGTH(Key.STRENGTH, get(R.string.ability_str), get(R.string.ability_str_abbr), R.drawable.strength, R.color.str),
    WISDOM(Key.WISDOM, get(R.string.ability_wis), get(R.string.ability_wis_abbr), R.drawable.wisdom, R.color.wis),
    DEXTERITY(Key.DEXTERITY, get(R.string.ability_dex), get(R.string.ability_dex_abbr), R.drawable.dexterity, R.color
            .dex),
    INTELLIGENCE(Key.INTELLIGENCE, get(R.string.ability_int), get(R.string.ability_int_abbr), R.drawable
            .intelligence, R.color.intl),
    CONSTITUTION(Key.CONSTITUTION, get(R.string.ability_con), get(R.string.ability_con_abbr), R.drawable
            .constitution, R.color.con),
    CHARISMA(Key.CHARISMA, get(R.string.ability_cha), get(R.string.ability_cha_abbr), R.drawable.charisma, R.color.cha);

    private static final Storage STORAGE = new Storage();

    private final Key storageKey;
    private final String name;
    private final String abbreviation;
    private final int imageRes;
    private final String imageDescription;
    private final int colorRes;

    Ability(Key storageKey, String name, String abbreviation, @DrawableRes int imageRes, @ColorRes int colorRes) {
        this.storageKey = storageKey;
        this.name = name;
        this.abbreviation = abbreviation;
        this.imageRes = imageRes;
        this.imageDescription = String.format(get(R.string.image_description_ability), name);
        this.colorRes = colorRes;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public
    @DrawableRes
    int getImageRes() {
        return imageRes;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public
    @ColorRes
    int getColorRes() {
        return colorRes;
    }

    public void saveValue(int value) {
        STORAGE.saveAbility(storageKey, value);
    }

    public int loadValue() {
        return STORAGE.loadAbility(storageKey);
    }

    private static String get(@StringRes int stringRes) {
        return App.getResourceString(stringRes);
    }
}
