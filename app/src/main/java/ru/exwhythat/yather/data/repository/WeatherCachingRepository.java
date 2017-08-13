package ru.exwhythat.yather.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CityWithWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/6/17.
 */

public class WeatherCachingRepository {

    private LocalWeatherRepository localRepo;
    private RemoteWeatherRepository remoteRepo;

    @Inject
    public WeatherCachingRepository(LocalWeatherRepository localRepo, RemoteWeatherRepository remoteRepo) {
        this.localRepo = localRepo;
        this.remoteRepo = remoteRepo;
    }

    /**
     * 1) Try to get weather from database
     * 2) If there is no weather for out city in DB, fetch it from network
     * 3) Merge Single with endless Flowable to watch for database changes
     */
    public Flowable<CurrentWeather> getWeather() {
        return localRepo.getSingleWeatherForSelectedCity()
                .onErrorResumeNext(getFreshWeather())
                .toFlowable()
                .mergeWith(localRepo.getFlowingWeatherForSelectedCity());
    }

    public Single<CurrentWeather> getFreshWeather() {
        return localRepo.getSelectedCityIdSingle()
                .flatMap(cityId -> remoteRepo.getCurrentWeatherForCity(cityId))
                .doOnSuccess(currentWeather -> localRepo.updateCurrentWeather(currentWeather));
    }

    public Flowable<List<ForecastWeather>> getForecast() {
        return localRepo.getSingleForecastForSelectedCity()
                .onErrorResumeNext(getFreshForecast())
                .toFlowable()
                .mergeWith(localRepo.getFlowingForecastForSelectedCity());
    }

    public Single<List<ForecastWeather>> getFreshForecast() {
        return localRepo.getSelectedCityIdSingle()
                .flatMap(cityId -> remoteRepo.getForecastForCity(cityId))
                .doOnSuccess(forecast -> localRepo.updateForecast(forecast));
    }

    public Flowable<List<CityWithWeather>> getCitiesWithWeather() {
        return localRepo.getFlowingCitiesWithWeather();
    }

    public long setSelectedCity(int cityId) {
        return localRepo.selectCity(cityId);
    }

    public Single<City> getSelectedCitySingle() {
        return localRepo.getSelectedCitySingle();
    }

    public long deleteCity(int cityId) {
        return localRepo.deleteCity(cityId);
    }
}
