package ru.javaops.basejava.util;

import ru.javaops.basejava.model.Organization;

import java.time.LocalDate;
import java.util.List;

public class HtmlUtil {

    public String formatDatesToPeriod(Organization.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }

    public String formatDateToMMyyyy(LocalDate date) {
        return DateUtil.format(date);
    }

    public String formatListToString(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        return String.join("\n", items);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}
