package com.aes.corebackend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.BD_DATE_FORMAT);
    
    public static LocalDate convertToLocalDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String convertLocalDateToString(LocalDate date) {
        return formatter.format(date);
    }
}
