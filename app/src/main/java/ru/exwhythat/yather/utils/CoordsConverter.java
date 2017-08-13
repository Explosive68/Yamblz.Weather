package ru.exwhythat.yather.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by exwhythat on 29.07.17.
 */
public class CoordsConverter {
    private static final String COORDS_SPLIT_SYMBOL = " ";
    private static final String COORDS_FORMAT_PATTERN = "%1$.10f" + COORDS_SPLIT_SYMBOL + "%2$.10f";

    public static LatLng fromStringToCoords(String strCoords) {
        String[] splittedCoords = strCoords.split(COORDS_SPLIT_SYMBOL);
        double latitude = Double.valueOf(splittedCoords[0].replace(',', '.'));
        double longitude = Double.valueOf(splittedCoords[1].replace(',', '.'));
        return new LatLng(latitude, longitude);
    }

    public static String fromCoordsToString(LatLng coords) {
        return String.format(Locale.getDefault(), COORDS_FORMAT_PATTERN, coords.latitude, coords.longitude);
    }
}
