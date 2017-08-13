package ru.exwhythat.yather;

import org.junit.Test;

import ru.exwhythat.yather.data.local.entities.City;

import static org.junit.Assert.*;

/**
 * Created by exwhythat on 30.07.17.
 */

public class CityTest {

    @Test
    public void testEquals() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", false);
        assertEquals(cityInfo1, cityInfo2);
    }

    @Test
    public void testEqualsReflexive() {
        City cityInfo1 = new City(55, "Moscow", false);
        assertEquals(cityInfo1, cityInfo1);
    }

    @Test
    public void testEqualsSymmetric() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", false);
        assertEquals(cityInfo1, cityInfo2);
        assertEquals(cityInfo2, cityInfo1);
    }

    @Test
    public void testEqualsTransitive() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", false);
        City cityInfo3 = new City(55, "Moscow", false);
        assertEquals(cityInfo1, cityInfo2);
        assertEquals(cityInfo2, cityInfo3);
        assertEquals(cityInfo3, cityInfo1);
    }

    @Test
    public void testNotEqualsNull() {
        City cityInfo1 = new City(55, "Moscow", false);
        assertNotEquals(cityInfo1, null);
    }

    @Test
    public void testNotEquals_1() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow2", false);
        assertNotEquals(cityInfo1, cityInfo2);
    }

    @Test
    public void testNotEquals_2() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(51, "Moscow", false);
        assertNotEquals(cityInfo1, cityInfo2);
    }

    @Test
    public void testNotEquals_3() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", true);
        assertNotEquals(cityInfo1, cityInfo2);
    }

    @Test
    public void testHashCode_Equals() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", false);
        assertTrue(cityInfo1.hashCode() == cityInfo2.hashCode());
    }

    @Test
    public void testHashCode_Not_Equals_1() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow2", false);
        assertTrue(cityInfo1.hashCode() != cityInfo2.hashCode());
    }

    @Test
    public void testHashCode_Not_Equals_2() {
        City cityInfo1 = new City(55, "Moscow", false);
        City cityInfo2 = new City(55, "Moscow", true);
        assertTrue(cityInfo1.hashCode() != cityInfo2.hashCode());
    }
}
