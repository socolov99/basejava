package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class MainDate {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        System.out.println(localDate + "\n" + localTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        System.out.println(sdf.format(date));
    }
}
