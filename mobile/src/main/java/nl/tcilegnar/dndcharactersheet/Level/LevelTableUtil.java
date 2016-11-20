package nl.tcilegnar.dndcharactersheet.Level;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Level.entities.LevelTable;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelTableUtil {
    protected static final LevelTable[] ALL_LEVELS = LevelTable.values();
    protected static final int MIN_LVL = ALL_LEVELS[0].level;
    protected static final int MAX_LVL = ALL_LEVELS[ALL_LEVELS.length - 1].level;

    /** TODO: Geef een willekeurig & hoog getal terug zodat je na lvl 20 evt verder kan gaan met exp verzamelen */
    public static final int MAX_EXP_FOR_MAX_LEVEL = 0;

    public int getMaxExperience(final int level) {
        validate(level);

        final int nextLevel = level + 1;
        for (LevelTable levelEnum : ALL_LEVELS) {
            if (levelEnum.level == nextLevel) {
                return levelEnum.startingExp;
            }
        }

        if (level == MAX_LVL) {
            return MAX_EXP_FOR_MAX_LEVEL;
        }

        throw new NoMaxExperienceForLevelException(level);
    }

    private void validate(int level) {
        if (level < MIN_LVL || level > MAX_LVL) {
            throw new NoMaxExperienceForLevelException(level);
        }
    }

    protected static class NoMaxExperienceForLevelException extends IllegalArgumentException {
        protected NoMaxExperienceForLevelException(int level) {
            super(App.getResourceString(R.string.max_level_reached_exception) + level);
        }
    }
}
