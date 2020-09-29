package ru.javaops.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    public static final String STR_NOW = "Настоящее время";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? STR_NOW : date.format(DATE_FORMATTER);
    }

    public static LocalDate parse(String date) {
        if (STR_NOW.equals(date) || HtmlUtil.isEmpty(date)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
        return STR_NOW.equals(date) ? NOW : LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }
}
