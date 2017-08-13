package ru.exwhythat.yather.data.remote;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.exwhythat.yather.data.remote.model.DailyForecastResponse;
import ru.exwhythat.yather.data.remote.model.WeatherResponse;

/**
 * Created by exwhythat on 15.07.17.
 */

public interface WeatherApi {

    @GET("weather")
    Single<WeatherResponse> getWeatherByCityId(@Query("id") int cityId);

    @GET("weather")
    Single<WeatherResponse> getWeatherByLocation(@Query("lat") double lat,
                                              @Query("lon") double lon);

    /** @param daysCount maximum 16 */
    @GET("forecast/daily")
    Single<DailyForecastResponse> getForecastByCityId(@Query("id") int cityId, @Query("cnt") int daysCount);
}
