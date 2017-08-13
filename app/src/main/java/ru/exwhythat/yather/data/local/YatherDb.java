package ru.exwhythat.yather.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/4/17.
 */

@Database(entities = {City.class, CurrentWeather.class, ForecastWeather.class}, version = 1)
@TypeConverters({Converters.DateTimestamp.class})
public abstract class YatherDb extends RoomDatabase {
    abstract public CityDao cityDao();
    abstract public CurrentWeatherDao currentWeatherDao();
    abstract public ForecastWeatherDao forecastWeatherDao();
}
