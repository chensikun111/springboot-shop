package org.example.shopvue.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class GetNowTime {
    public static Timestamp GetNowTime() {
        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
