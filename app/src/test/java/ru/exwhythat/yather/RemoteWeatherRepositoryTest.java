package ru.exwhythat.yather;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import ru.exwhythat.yather.data.remote.WeatherApi;
import ru.exwhythat.yather.data.remote.model.DailyForecastResponse;
import ru.exwhythat.yather.data.remote.model.WeatherResponse;
import ru.exwhythat.yather.data.repository.RemoteWeatherRepository;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;

import static org.mockito.Mockito.*;

/**
 * Created by exwhythat on 8/13/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class RemoteWeatherRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    WeatherApi mockWeatherApi;

    private RemoteWeatherRepository remoteRepo;

    private Gson gson;

    @Before
    public void prepare() {
        remoteRepo = new RemoteWeatherRepository(mockWeatherApi);
        gson = new GsonBuilder().create();

        DailyForecastResponse forecastResponse = gson.fromJson(TestData.jsonDailyForecastResponse, DailyForecastResponse.class);
        WeatherResponse weatherResponse = gson.fromJson(TestData.jsonWeatherResponse, WeatherResponse.class);

        when(mockWeatherApi.getWeatherByCityId(anyInt())).thenReturn(Single.just(weatherResponse));
        when(mockWeatherApi.getForecastByCityId(anyInt(), anyInt())).thenReturn(Single.just(forecastResponse));
        when(mockWeatherApi.getWeatherByLocation(anyDouble(), anyDouble())).thenReturn(Single.just(weatherResponse));
    }

    @Test
    public void testGetCurrentWeatherForCity() {
        int cityId = TestData.TestCity.testCity1.getCityId();
        remoteRepo.getCurrentWeatherForCity(TestData.TestCity.testCity1.getCityId());
        verify(mockWeatherApi).getWeatherByCityId(cityId);
    }

    @Test
    public void testGetForecastForCity() {
        int cityId = TestData.TestCity.testCity1.getCityId();
        remoteRepo.getForecastForCity(TestData.TestCity.testCity1.getCityId());
        verify(mockWeatherApi).getForecastByCityId(eq(cityId), anyInt());
    }

    @Test
    public void testGetCityIdByLatLng() {
        double testLat = 5.3;
        double testLon = 3.33;
        remoteRepo.getCityIdByLatLng(new LatLng(testLat, testLon));
        verify(mockWeatherApi).getWeatherByLocation(testLat, testLon);
    }
}
