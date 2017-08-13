package ru.exwhythat.yather.util;

import java.util.Date;

import ru.exwhythat.yather.data.local.entities.BaseWeather;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/5/17.
 */

public class TestData {

    public static class TestCity {
        public static final int testCityId1 = 11111;
        public static final String testCityName1 = "City1";
        public static final City testCity1 = new City(testCityId1, testCityName1, false);

        public static final int testCityId2 = 22222;
        public static final String testCityName2 = "City2";
        public static final City testCity2 = new City(testCityId2, testCityName2, false);

        public static final int testCityId3 = 33333;
        public static final String testCityName3 = "City3";
        public static final City testCity3 = new City(testCityId3, testCityName3, false);
    }

    public static class TestBaseWeather {
        public static final String testMain1 = "Sunny";
        public static final String testDescr1 = "light sun";
        public static final String testIconId1 = "11a";
        public static final Date testDate1 = new Date(1501549871000L); // Thu, 01 Aug 2017 01:11:11 GMT
        public static final BaseWeather testBaseWeather1 = new BaseWeather(testMain1, testDescr1, testIconId1, testDate1);

        public static final String testMain2 = "Rain";
        public static final String testDescr2 = "heavy rain";
        public static final String testIconId2 = "22b";
        public static final Date testDate2 = new Date(1501640542000L); // Thu, 02 Aug 2017 02:22:22 GMT
        public static final BaseWeather testBaseWeather2 = new BaseWeather(testMain2, testDescr2, testIconId2, testDate2);

        public static final String testMain3 = "Snow";
        public static final String testDescr3 = "bastard snow";
        public static final String testIconId3 = "33c";
        public static final Date testDate3 = new Date(1501731213000L); // Thu, 03 Aug 2017 03:33:33 GMT
        public static final BaseWeather testBaseWeather3 = new BaseWeather(testMain3, testDescr3, testIconId3, testDate3);
    }

    public static class TestCurrentWeather {
        public static final double testTemp1 = 11.11;
        public static final int testHumidity1 = 11;
        public static final double testWindSpeed1 = 11.11;
        public static final double testPressure1 = 11.11;
        public static final CurrentWeather testCurrentWeather1 = new CurrentWeather(TestBaseWeather.testBaseWeather1,
                TestCity.testCityId1, testHumidity1, testWindSpeed1, testPressure1, testTemp1);

        public static final double testTemp2 = 22.22;
        public static final int testHumidity2 = 22;
        public static final double testWindSpeed2 = 22.22;
        public static final double testPressure2 = 22.22;
        public static final CurrentWeather testCurrentWeather2 = new CurrentWeather(TestBaseWeather.testBaseWeather2,
                TestCity.testCityId2, testHumidity2, testWindSpeed2, testPressure2, testTemp2);

        public static final double testTemp3 = 33.33;
        public static final int testHumidity3 = 33;
        public static final double testWindSpeed3 = 33.33;
        public static final double testPressure3 = 33.33;
        public static final CurrentWeather testCurrentWeather3 = new CurrentWeather(TestBaseWeather.testBaseWeather3,
                TestCity.testCityId3, testHumidity3, testWindSpeed3, testPressure3, testTemp3);
    }

    public static class TestForecastWeather {
        public static final double testDayTemp1 = 11.11;
        public static final double testNightTemp1 = -11.11;
        public static final ForecastWeather testForecastWeather1 = new ForecastWeather(TestBaseWeather.testBaseWeather1,
                TestCity.testCityId1, testDayTemp1, testNightTemp1);

        public static final double testDayTemp2 = 22.22;
        public static final double testNightTemp2 = -22.22;
        public static final ForecastWeather testForecastWeather2 = new ForecastWeather(TestBaseWeather.testBaseWeather2,
                TestCity.testCityId2, testDayTemp2, testNightTemp2);

        public static final double testDayTemp3 = 33.33;
        public static final double testNightTemp3 = -33.33;
        public static final ForecastWeather testForecastWeather3 = new ForecastWeather(TestBaseWeather.testBaseWeather3,
                TestCity.testCityId3, testDayTemp3, testNightTemp3);
    }
}
