package ru.exwhythat.yather.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.exwhythat.yather.R;

/**
 * Created by exwhythat on 8/10/17.
 */

public class StringUtils {

    @NonNull
    private static String makeSignedTempr(@NonNull String temperature) {
        if (temperature.charAt(0) != '-') {
            return '+' + temperature;
        } else {
            return temperature;
        }
    }

    @NonNull
    public static String getFormattedTemperature(@NonNull Context context, double temperature) {
        String tempr = String.format(context.getString(R.string.tempr_float), temperature);
        return makeSignedTempr(tempr);
    }

    @NonNull
    public static String getFormattedWind(@NonNull Context context, double windSpeed) {
        return context.getString(R.string.wind, (int)windSpeed);
    }

    @NonNull
    public static String getFormattedHumidity(@NonNull Context context, int humidity) {
        return context.getString(R.string.percent, humidity);
    }
}