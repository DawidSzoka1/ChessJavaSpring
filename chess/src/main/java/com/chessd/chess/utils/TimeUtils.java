package com.chessd.chess.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DecimalFormat;

@Component
public class TimeUtils {
    public double minutesBetween(Timestamp start, Timestamp end) {
        return ((double) (end.getTime() - start.getTime())) / 1000 / 60;
    }

    public String minutesBetween(Timestamp start) {
        DecimalFormat format = new DecimalFormat("##.00");
        double between = ((double) (new Timestamp(System.currentTimeMillis()).getTime() - start.getTime())) / 1000 / 60;
        return (format.format(between)).replace(",", ":");
    }
}
