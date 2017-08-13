package ru.exwhythat.yather.db;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.exwhythat.yather.data.local.ForecastWeatherDao;

/**
 * Created by exwhythat on 8/4/17.
 */

@RunWith(AndroidJUnit4.class)
public class ForecastWeatherDaoTest extends WeatherTest {

    private ForecastWeatherDao forecastWeatherDao;

    @Before
    public void prepareForecastWeather() {
        forecastWeatherDao = db.forecastWeatherDao();
    }

    @Test
    public void insertOneAndLoad() {

    }
}
