package com.example.bestbefore.db.converter;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class DateConverter {

    @TypeConverter
    public static LocalDate toLocalDate(String dateText) {
        return dateText == null ? null : LocalDate.parse(dateText, ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static String toDateText(LocalDate date) {
        return date == null ? null : date.format(ISO_LOCAL_DATE);
    }
}
