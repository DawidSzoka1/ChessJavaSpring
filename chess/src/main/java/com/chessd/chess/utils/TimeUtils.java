package com.chessd.chess.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TimeUtils {
    public double minutesBetween(Timestamp start, Timestamp end){
        return ((double) (end.getTime() - start.getTime())) / 1000 / 60;
    }
}
