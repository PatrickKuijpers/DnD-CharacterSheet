package nl.tcilegnar.dndcharactersheet.Level;

public enum LevelTable {
    ONE(1, 0),
    TWO(2, 300),
    THREE(3, 900),
    FOUR(4, 2700),
    FIVE(5, 6500),
    SIX(6, 14000),
    SEVEN(7, 23000),
    EIGHT(8, 34000),
    NINE(9, 48000),
    TEN(10, 64000),
    ELEVEN(11, 85000),
    TWELVE(12, 100000),
    THIRTEEN(13, 120000),
    FOURTEEN(14, 140000),
    FIFTEEN(15, 165000),
    SIXTEEN(16, 195000),
    SEVENTEEN(17, 225000),
    EIGHTEEN(18, 265000),
    NINETEEN(19, 305000),
    TWENTY(20, 355000);

    public final int level;
    public final int startingExp;

    LevelTable(int level, int startingExp) {
        this.level = level;
        this.startingExp = startingExp;
    }
}
