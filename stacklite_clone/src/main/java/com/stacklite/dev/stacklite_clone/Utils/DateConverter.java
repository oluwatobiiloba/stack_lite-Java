package com.stacklite.dev.stacklite_clone.Utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class DateConverter {

    public String convertToDate(LocalDateTime localDateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
    }
}
