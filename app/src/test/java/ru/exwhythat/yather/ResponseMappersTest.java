package ru.exwhythat.yather;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Single;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.data.remote.WeatherApi;
import ru.exwhythat.yather.data.remote.model.DailyForecastResponse;
import ru.exwhythat.yather.data.remote.model.WeatherResponse;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;
import static ru.exwhythat.yather.util.TestData.jsonDailyForecastResponse;
import static ru.exwhythat.yather.util.TestData.jsonWeatherResponse;

/**
 * This test is kinda messy (and not working yet).
 * App needs to be refactored a lot in a good way to make network operations easy for testing.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class ResponseMappersTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    WeatherApi mockWeatherApi;

    private Gson gson;

    private WeatherResponse testWeatherResponse;
    private DailyForecastResponse testDailyForecastResponse;

    @Before
    public void prepare() {
        initMocks();
    }

    private void initMocks() {
        gson = new GsonBuilder().create();

        testWeatherResponse = gson.fromJson(jsonWeatherResponse, WeatherResponse.class);
        testDailyForecastResponse = gson.fromJson(jsonDailyForecastResponse, DailyForecastResponse.class);

        when(mockWeatherApi.getWeatherByCityId(anyInt()))
                .thenReturn(Single.just(testWeatherResponse));

        when(mockWeatherApi.getForecastByCityId(anyInt(), anyInt()))
                .thenReturn(Single.just(testDailyForecastResponse));
    }

    @Test
    public void testWeatherResponseMapper() {
        CurrentWeather actualCurrentWeather = WeatherResponse.Mapper.toCurrentWeather(testWeatherResponse);
        assertEquals(TestData.testCurrentWeather, actualCurrentWeather);
    }

    @Test
    public void testDailyForecastResponseMapper() {
        List<ForecastWeather> actualForecastWeatherList = DailyForecastResponse.Mapper.toForecast(testDailyForecastResponse);
        ForecastWeather actualForecastWeather = actualForecastWeatherList.get(0);
        assertEquals(TestData.testForecastWeather1, actualForecastWeather);
    }
}
