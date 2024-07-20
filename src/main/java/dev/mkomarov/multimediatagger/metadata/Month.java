package dev.mkomarov.multimediatagger.metadata;

public enum Month {
    JAN(1),
    FEB(2),
    MAR(3),
    APR(4),
    MAY(5),
    JUN(6),
    JUL(7),
    AUG(8),
    SEP(9),
    OCT(10),
    NOV(11),
    DEC(12);

    private final int monthNumber;

    Month(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public static int getNumericValue(String monthString) {
        for (Month month : Month.values()) {
            if (month.name().equalsIgnoreCase(monthString)) {
                return month.getMonthNumber();
            }
        }

        throw new IllegalArgumentException("Invalid month string: " + monthString);
    }
}
