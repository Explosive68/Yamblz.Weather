package ru.exwhythat.yather.data.local;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by exwhythat on 8/5/17.
 */

public class Converters {

    public static class DateTimestamp {
        @TypeConverter
        public static Date fromTimestamp(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp*1000);
        }

        @TypeConverter
        public static Long fromDate(Date date) {
            return date == null ? null : date.getTime();
        }
    }
}
