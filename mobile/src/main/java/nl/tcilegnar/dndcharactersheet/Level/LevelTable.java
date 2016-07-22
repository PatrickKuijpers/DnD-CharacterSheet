package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.IntRange;

public enum LevelTable {
    ONE(1, 0),
    TWO(2, 1000),
    THREE(3, 3000),
    FOUR(4, 6000),
    FIVE(5, 10000),
    SIX(6, 15000),
    SEVEN(7, 21000),
    EIGHT(8, 28000),
    NINE(9, 36000),
    TEN(10, 45000),
    ELEVEN(11, 55000),
    TWELVE(12, 66000),
    THIRTEEN(13, 78000),
    FOURTEEN(14, 91000),
    FIFTEEN(15, 105000),
    SIXTEEN(16, 120000),
    SEVENTEEN(17, 136000),
    EIGHTEEN(18, 153000),
    NINETEEN(19, 171000),
    TWENTY(20, 190000);

    /** TODO: Geef een hoog willekeurig getal terug zodat je na lvl 20 evt verder kan gaan met exp verzamelen */
    public static final int MAX_EXP_FOR_MAX_LEVEL = 0;
    public final int level;
    public final int startingExp;

    LevelTable(int level, int startingExp) {
        this.level = level;
        this.startingExp = startingExp;
    }

    public static int getMaxExperience(@IntRange(from = 1, to = 20) final int level) {
        validate(level);

        final int nextLevel = level + 1;
        for (LevelTable thisEnum : values()) {
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
        if (level < ONE.level || level > maxLevel()) {
            throw new NoMaxExperienceForLevelException(level);
        }
    }

    private static int maxLevel() {
        return values().length;
    }

    static boolean isMaxLevel(int level) {
        return level == maxLevel();
    }

    protected static class NoMaxExperienceForLevelException extends IllegalArgumentException {
        protected NoMaxExperienceForLevelException(int level) {
            super("Geen maximum experience mogelijk voor level " + String.valueOf(level));
        }
    }
}
