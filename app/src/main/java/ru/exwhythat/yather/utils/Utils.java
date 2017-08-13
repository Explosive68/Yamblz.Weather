package ru.exwhythat.yather.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Sinjvf on 16.07.2017.
 * Some utils
 */

public class Utils {
    public static final long MINUTE = 1000 * 60;
    public static final int PROGRESS_SHOW = 0;
    public static final int PROGRESS_SUCCESS = 1;
    public static final int PROGRESS_FAIL = -1;

    public static final int TIME_10 = 10;
    public static final int TIME_15 = 15;
    public static final int TIME_30 = 30;
    public static final int TIME_60 = 60;

    public static String lastUpdateString(Context context) {
        Date date = new Date(Prefs.getPrefLastTimeUpdateDate(context));
        SimpleDateFormat sf = new SimpleDateFormat(" HH:mm, dd.MM.yy ", Locale.getDefault());
        return sf.format(date);
    }

    public static String lastUpdateString(long data) {
        Date date = new Date(data);
        SimpleDateFormat sf = new SimpleDateFormat(" HH:mm, dd.MM.yy ", Locale.getDefault());
        return sf.format(date);
    }

    //if we have many inner classes and can catch the NullPointer Exception while do many get calls
    public static <T> T getDataWithoutException(GetData<T> getData) {
        try {
            return getData.getData();
        } catch (NullPointerException e) {
            Timber.e(e.getMessage());
        }
        return null;
    }

    public interface GetData<T> {
        T getData();
    }
}
