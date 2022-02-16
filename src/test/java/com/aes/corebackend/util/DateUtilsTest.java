package com.aes.corebackend.util;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilsTest {
    @Test
    public void convertToLocalDateTest() {
        LocalDate date = LocalDate.of(1989, 9, 12);
        String dateStr = "12-09-1989";
        assertEquals(date, DateUtils.convertToLocalDate(dateStr));
    }

    @Test
    public void convertLocalDateToStringTest() {
        LocalDate date = LocalDate.of(1989, 9, 12);
        assertEquals("12-09-1989", DateUtils.convertLocalDateToString(date));
    }
}
