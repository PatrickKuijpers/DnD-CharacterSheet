package nl.tcilegnar.dndcharactersheet.Level.entities;

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

    public final int level;
    public final int startingExp;

    LevelTable(int level, int startingExp) {
        this.level = level;
        this.startingExp = startingExp;
    }
}
