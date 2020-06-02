package ru.javaops.basejava;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) throws ParseException {
        //before java 8
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        System.out.println(sdf.format(date));
        System.out.println(sdf.parse("2020/06/02 11:00"));

        //since java 8
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        System.out.println(dtf.format(ldt));
        System.out.println(LocalDateTime.parse("2020/06/02 11:00", dtf));
    }
}
