package ru.exwhythat.yather.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;

/**
 * Created by exwhythat on 8/4/17.
 */

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CurrentWeather currentWeather);

    @Query("SELECT * from currentweather INNER JOIN city ON currentweather.apiCityId = city.cityId WHERE city.isselected = 1")
    Flowable<CurrentWeather> getFlowForSelectedCity();

    @Query("SELECT * from currentweather INNER JOIN city ON currentweather.apiCityId = city.cityId WHERE city.isselected = 1")
    Single<CurrentWeather> getSingleForSelectedCity();
}
