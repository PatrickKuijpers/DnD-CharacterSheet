package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.IntRange;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelTableUtil {
    /** TODO: Geef een hoog willekeurig getal terug zodat je na lvl 20 evt verder kan gaan met exp verzamelen */
    public static final int MAX_EXP_FOR_MAX_LEVEL = 0;

    public static int getMaxExperience(@IntRange(from = 1, to = 20) final int level) {
        validate(level);

        final int nextLevel = level + 1;
        for (LevelTable thisEnum : LevelTable.values()) {
            if (thisEnum.level == nextLevel) {
                return thisEnum.startingExp;
            }
        }

        if (isMaxLevel(level)) {
            return MAX_EXP_FOR_MAX_LEVEL;
        }

        throw new NoMaxExperienceForLevelException(level);
    }

    private static void validate(int level) {
        if (level < LevelTable.ONE.level || level > maxLevel()) {
            throw new NoMaxExperienceForLevelException(level);
        }
    }

    private static int maxLevel() {
        return LevelTable.values().length;
    }

    private static boolean isMaxLevel(int level) {
        return level == maxLevel();
    }

    protected static class NoMaxExperienceForLevelException extends IllegalArgumentException {
        protected NoMaxExperienceForLevelException(int level) {
            super(String.format(App.getAppResources().getString(R.string.max_level_reached_exception), level));
        }
    }
}
