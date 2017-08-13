package ru.exwhythat.yather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by exwhythat on 8/8/17.
 */

public class DateUtils {

    private static final String DATE_PATTERN = "EE, dd.MM";
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());

    public static String dateToString(Date date) {
        return sdf.format(date);
    }
}
