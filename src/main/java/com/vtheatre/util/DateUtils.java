package com.vtheatre.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Date transformDateFromString(String dateStr) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            logger.error("Date formatting error {}", e.getMessage());
        }

        return date;
    }

}
