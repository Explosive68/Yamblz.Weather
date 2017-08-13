package ru.exwhythat.yather.di.module;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.data.local.CityDao;
import ru.exwhythat.yather.data.local.CurrentWeatherDao;
import ru.exwhythat.yather.data.local.ForecastWeatherDao;
import ru.exwhythat.yather.data.local.YatherDb;
import ru.exwhythat.yather.utils.Prefs;

/**
 * Created by exwhythat on 8/6/17.
 */

@Module
public class DbModule {

    @Singleton
    @Provides
    YatherDb provideDb(Application application) {
        return Room.databaseBuilder(application, YatherDb.class, "yather.db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        // FIXME: insert default city on database creation
                        ContentValues cv = new ContentValues();
                        cv.put("cityId", application.getResources().getInteger(R.integer.moscow_id));
                        cv.put("name", application.getString(R.string.moscow_name));
                        cv.put("isSelected", 1);
                        db.insert("city", SQLiteDatabase.CONFLICT_REPLACE, cv);
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    CityDao provideCityDao(YatherDb db) {
        return db.cityDao();
    }

    @Singleton
    @Provides
    CurrentWeatherDao provideCurrentWeatherDao(YatherDb db) {
        return db.currentWeatherDao();
    }

    @Singleton
    @Provides
    ForecastWeatherDao provideForecastWeatherDao(YatherDb db) {
        return db.forecastWeatherDao();
    }
}
