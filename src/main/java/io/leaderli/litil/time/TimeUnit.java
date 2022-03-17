package io.leaderli.litil.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;

/**
 * @author leaderli
 * @since 2022/3/17 4:07 PM
 */
public class TimeUnit {


    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();

        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    }
}
