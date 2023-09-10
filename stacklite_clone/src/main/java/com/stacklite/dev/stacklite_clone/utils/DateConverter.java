package com.stacklite.dev.stacklite_clone.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class DateConverter {

    public String convertToDate(LocalDateTime localDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
