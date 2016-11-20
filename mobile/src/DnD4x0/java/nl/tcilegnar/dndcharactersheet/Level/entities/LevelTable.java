package nl.tcilegnar.dndcharactersheet.Level.entities;

public enum LevelTable {
    ONE(1, 0),
    TWO(2, 1000),
    THREE(3, 2250),
    FOUR(4, 3750),
    FIVE(5, 5500),
    SIX(6, 7500),
    SEVEN(7, 10000),
    EIGHT(8, 13000),
    NINE(9, 16500),
    TEN(10, 20500),
    ELEVEN(11, 26000),
    TWELVE(12, 32000),
    THIRTEEN(13, 39000),
    FOURTEEN(14, 47000),
    FIFTEEN(15, 57000),
    SIXTEEN(16, 69000),
    SEVENTEEN(17, 83000),
    EIGHTEEN(18, 99000),
    NINETEEN(19, 119000),
    TWENTY(20, 143000),
    TWENTYONE(21, 175000),
    TWENTYTWO(22, 210000),
    TWENTYTHREE(23, 255000),
    TWENTYFOUR(24, 310000),
    TWENTYFIVE(25, 375000),
    TWENTYSIX(26, 450000),
    TWENTYSEVEN(27, 550000),
    TWENTYEIGHT(28, 657000),
    TWENTYNINE(29, 825000),
    THIRTY(30, 1000000);

    public final int level;
    public final int startingExp;

    LevelTable(int level, int startingExp) {
        this.level = level;
        this.startingExp = startingExp;
    }
}
