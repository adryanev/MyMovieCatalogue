package com.adryanev.dicoding.mymoviecatalogue.utils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {

 @NotNull
 public static String convertToYear(String date) throws ParseException {
     SimpleDateFormat format = new SimpleDateFormat("yyyy");
     Date dates = format.parse(date);
     return dates.toString().split("\\([0-9]{4}\\)")[0];
 }
}
