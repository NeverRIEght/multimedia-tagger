package dev.mkomarov.multimediatagger.metadata;

public enum Month {
    JAN("01"),
    FEB("02"),
    MAR("03"),
    APR("04"),
    MAY("05"),
    JUN("06"),
    JUL("07"),
    AUG("08"),
    SEP("09"),
    OCT("10"),
    NOV("11"),
    DEC("12");

    private final String monthNumber;

    Month(String monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonthNumber() {
        return monthNumber;
    }

    public static String getNumericValue(String monthString) {
        for (Month month : Month.values()) {
            if (month.name().equalsIgnoreCase(monthString)) {
                return month.getMonthNumber();
            }
        }

        return null; // или можно вернуть "00" или что-то подобное в случае некорректного ввода
    }
}
