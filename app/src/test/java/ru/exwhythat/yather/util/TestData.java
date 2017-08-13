package ru.exwhythat.yather.util;

import java.util.Date;

import ru.exwhythat.yather.data.local.entities.BaseWeather;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CityWithWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/13/17.
 */

public class TestData {

    public static final String jsonWeatherResponse = "{\"coord\":{\"lon\":92.79,\"lat\":56.01},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"temp\":11,\"pressure\":1018,\"humidity\":93,\"temp_min\":11,\"temp_max\":11},\"visibility\":10000,\"wind\":{\"speed\":1,\"deg\":210},\"clouds\":{\"all\":40},\"dt\":1502632800,\"sys\":{\"type\":1,\"id\":7285,\"message\":0.0037,\"country\":\"RU\",\"sunrise\":1502576201,\"sunset\":1502630949},\"id\":1502026,\"name\":\"Krasnoyarsk\",\"cod\":200}";
    public static final String jsonDailyForecastResponse = "{\"city\":{\"id\":1502026,\"name\":\"Krasnoyarsk\",\"coord\":{\"lon\":92.7917,\"lat\":56.0097},\"country\":\"RU\",\"population\":0},\"cod\":\"200\",\"message\":0.0629428,\"cnt\":10,\"list\":[{\"dt\":1502600400,\"temp\":{\"day\":11,\"min\":8.01,\"max\":11,\"night\":8.01,\"eve\":11,\"morn\":11},\"pressure\":978.9,\"humidity\":75,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"speed\":1.51,\"deg\":280,\"clouds\":92},{\"dt\":1502686800,\"temp\":{\"day\":15.64,\"min\":9.14,\"max\":16.38,\"night\":9.14,\"eve\":15.69,\"morn\":9.66},\"pressure\":980.96,\"humidity\":71,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"speed\":1.96,\"deg\":227,\"clouds\":92},{\"dt\":1502773200,\"temp\":{\"day\":17.97,\"min\":8.67,\"max\":18.39,\"night\":14.43,\"eve\":17.21,\"morn\":8.67},\"pressure\":981.41,\"humidity\":67,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"02d\"}],\"speed\":1.52,\"deg\":49,\"clouds\":8},{\"dt\":1502859600,\"temp\":{\"day\":17.22,\"min\":13.23,\"max\":18.46,\"night\":17.42,\"eve\":18.46,\"morn\":13.23},\"pressure\":978.45,\"humidity\":76,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":2.47,\"deg\":47,\"clouds\":64,\"rain\":0.25},{\"dt\":1502946000,\"temp\":{\"day\":14.68,\"min\":12.02,\"max\":14.9,\"night\":12.02,\"eve\":14.9,\"morn\":13.05},\"pressure\":978.44,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":1.27,\"deg\":285,\"clouds\":95,\"rain\":7.47},{\"dt\":1503032400,\"temp\":{\"day\":15.59,\"min\":12.86,\"max\":15.59,\"night\":13.12,\"eve\":15.31,\"morn\":12.86},\"pressure\":983.76,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":1.26,\"deg\":279,\"clouds\":58,\"rain\":11.77},{\"dt\":1503118800,\"temp\":{\"day\":17.22,\"min\":9.86,\"max\":17.22,\"night\":9.86,\"eve\":16.79,\"morn\":13.12},\"pressure\":985.19,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":2.21,\"deg\":316,\"clouds\":53,\"rain\":2.66},{\"dt\":1503205200,\"temp\":{\"day\":19.25,\"min\":10.37,\"max\":19.25,\"night\":11.52,\"eve\":18.93,\"morn\":10.37},\"pressure\":982.54,\"humidity\":0,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"speed\":1.1,\"deg\":209,\"clouds\":36,\"rain\":0.34},{\"dt\":1503291600,\"temp\":{\"day\":21.86,\"min\":11.65,\"max\":21.86,\"night\":13.33,\"eve\":18.75,\"morn\":11.65},\"pressure\":980.78,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":1.41,\"deg\":176,\"clouds\":15,\"rain\":4.06},{\"dt\":1503378000,\"temp\":{\"day\":20.17,\"min\":12.77,\"max\":20.17,\"night\":12.77,\"eve\":18.48,\"morn\":13.66},\"pressure\":978.53,\"humidity\":0,\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"}],\"speed\":0.98,\"deg\":100,\"clouds\":11,\"rain\":4.74}]}";

    // Test values from test responses above
    public static final City testCity = new City(1502026, "Krasnoyarsk", false);
    public static final BaseWeather testBaseWeather = new BaseWeather("Clouds", "scattered clouds", "03n", new Date(1502632800L * 1000));
    public static final CurrentWeather testCurrentWeather = new CurrentWeather(testBaseWeather, 1502026, 93, 1, 1018, 11);
    public static final BaseWeather testForecastBaseWeather1 = new BaseWeather("Clouds", "overcast clouds", "04d", new Date(1502600400L * 1000));
    public static final BaseWeather testForecastBaseWeather2 = new BaseWeather("Clouds", "overcast clouds", "04d", new Date(1502686800L * 1000));
    public static final ForecastWeather testForecastWeather1 = new ForecastWeather(testForecastBaseWeather1, 1502026, 11, 8.01);
    public static final ForecastWeather testForecastWeather2 = new ForecastWeather(testForecastBaseWeather2, 1502026, 15.64, 9.14);
    public static final CityWithWeather testCityWithWeather1 = new CityWithWeather(testCity, testCurrentWeather);
    public static final CityWithWeather testCityWithWeather2 = new CityWithWeather(TestCity.testCity2, TestCurrentWeather.testCurrentWeather2);

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
