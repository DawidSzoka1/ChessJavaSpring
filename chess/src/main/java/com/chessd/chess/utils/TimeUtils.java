package com.chessd.chess.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;

@Component
public class TimeUtils {
    private String calculateDiff(Timestamp start, Timestamp end){
        Duration duration = Duration.ofMillis(end.getTime() - start.getTime());

        long h = duration.toHours();
        long m = duration.toMinutesPart();
        long s = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public String minutesBetween(Timestamp start, Timestamp end) {
        return calculateDiff(start, end);
    }

    public String minutesBetween(Timestamp start) {
        return calculateDiff(start, new Timestamp(System.currentTimeMillis()));
    }
}
