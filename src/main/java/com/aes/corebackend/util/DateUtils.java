package com.aes.corebackend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate convertToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.BD_DATE_FORMAT));
    }
}
