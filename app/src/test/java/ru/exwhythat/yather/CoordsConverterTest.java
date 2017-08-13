package ru.exwhythat.yather;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import ru.exwhythat.yather.utils.CoordsConverter;

import static org.junit.Assert.*;

/**
 * Created by exwhythat on 30.07.17.
 */

/**
 * Coordinates values range: {@link LatLng)}
 * Latitude: -90..90.
 * Longitude: -180..180
 */
public class CoordsConverterTest {

    @Test
    public void testFromStringToCoords_Valid() {
        String coordsString = "89.0234234 177.2342";
        LatLng latLng = CoordsConverter.fromStringToCoords(coordsString);
        double expectedLatitude = 89.0234234;
        double expectedLongitude = 177.2342;
        assertEquals(expectedLatitude, latLng.latitude, 0);
        assertEquals(expectedLongitude, latLng.longitude, 0);
    }

    @Test
    public void testFromStringToCoords_Valid_Comma() {
        String coordsString = "89,0234234 177,2342";
        LatLng latLng = CoordsConverter.fromStringToCoords(coordsString);
        double expectedLatitude = 89.0234234;
        double expectedLongitude = 177.2342;
        assertEquals(expectedLatitude, latLng.latitude, 0);
        assertEquals(expectedLongitude, latLng.longitude, 0);
    }

    @Test
    public void testFromStringToCoords_OutOfRange() {
        String coordsString = "899.0234234 1770.2342";
        LatLng latLng = CoordsConverter.fromStringToCoords(coordsString);
        double notExpectedLatitude = 899.0234234;
        double notExpectedLongitude = 1770.2342;
        assertNotEquals(notExpectedLatitude, latLng.latitude, 0);
        assertNotEquals(notExpectedLongitude, latLng.longitude, 0);
    }

    @Test (expected = NumberFormatException.class)
    public void testFromStringToCoords_InvalidFormat() {
        String invalidCoordsString = "899.0234234anyString 1770.2342anotherOne";
        CoordsConverter.fromStringToCoords(invalidCoordsString);
    }

    @Test
    public void testFromCoordsToString() {
        LatLng latLng = new LatLng(54.1234546465, 66.43214565);
        String coordsString = CoordsConverter.fromCoordsToString(latLng);
        assertEquals("54.1234546465 66.4321456500", coordsString);
    }
}