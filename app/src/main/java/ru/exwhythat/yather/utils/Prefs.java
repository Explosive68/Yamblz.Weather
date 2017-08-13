package ru.exwhythat.yather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * Created by Sinjvf on 16.07.2017.
 * works with shared preference
 */

public class Prefs {
    private static final String PACKAGE = "ru.exwhythat.yather";

    public static final String PREF_UNITS = "pref_units";
    private static final String PREF_LAST_TIME_UPDATE = "pref_last_time";
    public static final String PREF_INTERVAL_TIME = "pref_interval_time";

    public static final long DEFAULT_INTERVAL = TimeUnit.MINUTES.toMillis(Utils.TIME_10);

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PACKAGE, Context.MODE_PRIVATE);
    }

    public static String getUnits(Context context, String defaultUnits) {
        return getPrefs(context).getString(PREF_UNITS, defaultUnits);
    }

    public static void setUnits(Context context, String units) {
        getPrefs(context).edit().putString(PREF_UNITS, units).apply();
    }

    public static long getPrefLastTimeUpdateDate(Context context) {
        return getPrefs(context).getLong(PREF_LAST_TIME_UPDATE, 0);
    }

    public static void setPrefLastTimeUpdateDate(Context context) {
        long date = Calendar.getInstance().getTime().getTime();
        getPrefs(context).edit().putLong(PREF_LAST_TIME_UPDATE, date).apply();
    }

    public static long getIntervalTime(Context context) {
        return getPrefs(context).getLong(PREF_INTERVAL_TIME, DEFAULT_INTERVAL);
    }

    public static void setIntervalTime(Context context, long interval) {
        getPrefs(context).edit().putLong(PREF_INTERVAL_TIME, interval).apply();
    }
}
