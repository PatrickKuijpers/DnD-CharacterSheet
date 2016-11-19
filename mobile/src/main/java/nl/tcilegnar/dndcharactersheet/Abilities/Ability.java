package nl.tcilegnar.dndcharactersheet.Abilities;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static nl.tcilegnar.dndcharactersheet.Storage.Storage.Key;

public enum Ability {
    STRENGTH(Key.STRENGTH, R.string.ability_str, R.string.ability_str_abbr, R.drawable.strength, R.color.str),
    WISDOM(Key.WISDOM, R.string.ability_wis, R.string.ability_wis_abbr, R.drawable.wisdom, R.color.wis),
    DEXTERITY(Key.DEXTERITY, R.string.ability_dex, R.string.ability_dex_abbr, R.drawable.dexterity, R.color.dex),
    INTELLIGENCE(Key.INTELLIGENCE, R.string.ability_int, R.string.ability_int_abbr, R.drawable.intelligence, R.color
            .intl),
    CONSTITUTION(Key.CONSTITUTION, R.string.ability_con, R.string.ability_con_abbr, R.drawable.constitution, R.color
            .con),
    CHARISMA(Key.CHARISMA, R.string.ability_cha, R.string.ability_cha_abbr, R.drawable.charisma, R.color.cha);

    private Storage storage = new Storage();

    private final Key storageKey;
    private final String name;
    private final String abbreviation;
    private final int imageRes;
    private final String imageDescription;
    private final int color;

    Ability(Key storageKey, @StringRes int nameRes, @StringRes int abbreviationRes, @DrawableRes int imageRes,
            @ColorRes int colorRes) {
        this.storageKey = storageKey;
        this.name = getString(nameRes);
        this.abbreviation = getString(abbreviationRes);
        this.imageRes = imageRes;
        this.imageDescription = String.format(getString(R.string.image_description_ability), name);
        this.color = App.getResourceColor(colorRes);
    }

    @VisibleForTesting
    void setStorage(Storage storage) {
        this.storage = storage;
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
    @ColorInt
    int getColor() {
        return color;
    }

    public void saveValue(int value) {
        storage.saveAbility(storageKey, value);
    }

    public int loadValue() {
        return storage.loadAbility(storageKey);
    }

    private static String getString(@StringRes int stringRes) {
        return App.getResourceString(stringRes);
    }
}
