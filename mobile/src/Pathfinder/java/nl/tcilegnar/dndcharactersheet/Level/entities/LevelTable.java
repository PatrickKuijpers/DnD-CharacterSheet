package nl.tcilegnar.dndcharactersheet.Level.entities;

public enum LevelTable { // Based on medium exp increase (startingExp)
    ONE(1, 0),
    TWO(2, 2000),
    THREE(3, 5000),
    FOUR(4, 9000),
    FIVE(5, 15000),
    SIX(6, 23000),
    SEVEN(7, 35000),
    EIGHT(8, 51000),
    NINE(9, 75000),
    TEN(10, 105000),
    ELEVEN(11, 155000),
    TWELVE(12, 220000),
    THIRTEEN(13, 315000),
    FOURTEEN(14, 445000),
    FIFTEEN(15, 635000),
    SIXTEEN(16, 890000),
    SEVENTEEN(17, 1300000),
    EIGHTEEN(18, 1800000),
    NINETEEN(19, 2550000),
    TWENTY(20, 3600000);

    public final int level;
    public final int startingExpSlow;
    public final int startingExp;
    public final int startingExpFast;

    LevelTable(int level, int startingExp) {
        this.level = level;
        this.startingExpSlow = 0; // TODO not implemented
        this.startingExp = startingExp;
        this.startingExpFast = 0; // TODO not implemented
    }
}
