package ru.exwhythat.yather.data.repository;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.data.remote.WeatherApi;
import ru.exwhythat.yather.data.remote.WeatherApiConstants;
import ru.exwhythat.yather.data.remote.model.DailyForecastResponse;
import ru.exwhythat.yather.data.remote.model.WeatherResponse;

/**
 * Created by exwhythat on 02.08.17.
 */

public class RemoteWeatherRepository {

    private WeatherApi weatherApi;

    @Inject
    public RemoteWeatherRepository(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Single<CurrentWeather> getCurrentWeatherForCity(int cityId) {
        return weatherApi.getWeatherByCityId(cityId)
                .map(WeatherResponse.Mapper::toCurrentWeather);
    }

    public Single<List<ForecastWeather>> getForecastForCity(int cityId) {
        return weatherApi.getForecastByCityId(cityId, WeatherApiConstants.DEFAULT_FORECAST_DAYS_COUNT)
                .map(DailyForecastResponse.Mapper::toForecast);
    }

    public Single<Integer> getCityIdByLatLng(LatLng latLng) {
        return weatherApi.getWeatherByLocation(latLng.latitude, latLng.longitude)
                .map(WeatherResponse::getCityId);
    }
}
