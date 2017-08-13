package ru.exwhythat.yather;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exwhythat.yather.base_util.livedata.Resource;
import ru.exwhythat.yather.base_util.livedata.Status;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CityWithWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.data.repository.WeatherCachingRepository;
import ru.exwhythat.yather.screens.weather.WeatherViewModel;
import ru.exwhythat.yather.util.LiveDataTestUtil;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
import static ru.exwhythat.yather.util.TestData.testCityWithWeather1;
import static ru.exwhythat.yather.util.TestData.testCityWithWeather2;
import static ru.exwhythat.yather.util.TestData.testForecastWeather1;
import static ru.exwhythat.yather.util.TestData.testForecastWeather2;

/**
 * Created by exwhythat on 02.08.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class WeatherViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    YatherApp yatherApp;

    @Mock
    WeatherCachingRepository weatherRepo;

    private WeatherViewModel weatherModel;

    private List<ForecastWeather> testForecastList;
    private List<CityWithWeather> testCityWeatherList;

    @Before
    public void prepare() {
        weatherModel = Mockito.spy(new WeatherViewModel(yatherApp, weatherRepo));

        when(weatherRepo.getSelectedCitySingle())
                .thenReturn(Single.just(TestData.TestCity.testCity1));

        when(weatherRepo.getWeather())
                .thenReturn(Flowable.create(em -> {
                    em.onNext(TestData.TestCurrentWeather.testCurrentWeather1);
                }, BackpressureStrategy.BUFFER));

        testForecastList = new ArrayList<>();
        testForecastList.add(testForecastWeather1);
        testForecastList.add(testForecastWeather2);

        when(weatherRepo.getForecast())
                .thenReturn(Flowable.create(em -> {
                    em.onNext(testForecastList);
                }, BackpressureStrategy.BUFFER));

        testCityWeatherList = new ArrayList<>();
        testCityWeatherList.add(testCityWithWeather1);
        testCityWeatherList.add(testCityWithWeather2);

        when(weatherRepo.getCitiesWithWeather())
                .thenReturn(Flowable.create(em -> {
                    em.onNext(testCityWeatherList);
                }, BackpressureStrategy.BUFFER));
    }

    @Test
    public void testGetSelectedCity() throws InterruptedException {
        LiveData<Resource<City>> cityResource = weatherModel.getSelectedCity();
        // TODO: Should verify loading status here, but how?
        verify(weatherModel).loadSelectedCityFromRepo();
        verify(weatherRepo).getSelectedCitySingle();
        Resource<City> cityResult = LiveDataTestUtil.getValue(cityResource);
        assertEquals(TestData.TestCity.testCity1, cityResult.data);
        assertNull(cityResult.message);
        assertEquals(Status.SUCCESS, cityResult.status);
    }

    @Test
    public void testGetWeather() throws InterruptedException {
        LiveData<Resource<CurrentWeather>> weatherResource = weatherModel.getWeather();
        verify(weatherModel).loadWeatherFromRepo();
        verify(weatherRepo).getWeather();
        Resource<CurrentWeather> weatherResult = LiveDataTestUtil.getValue(weatherResource);
        assertEquals(TestData.TestCurrentWeather.testCurrentWeather1, weatherResult.data);
        assertNull(weatherResult.message);
        assertEquals(Status.SUCCESS, weatherResult.status);
    }

    @Test
    public void testGetForecast() throws InterruptedException {
        LiveData<Resource<List<ForecastWeather>>> forecastResource = weatherModel.getForecast();
        verify(weatherModel).loadForecastFromRepo();
        verify(weatherRepo).getForecast();
        Resource<List<ForecastWeather>> forecastResult = LiveDataTestUtil.getValue(forecastResource);
        assertEquals(testForecastList, forecastResult.data);
        assertNull(forecastResult.message);
        assertEquals(Status.SUCCESS, forecastResult.status);
    }

    @Test
    public void testGetCitiesWithWeather() throws InterruptedException {
        LiveData<Resource<List<CityWithWeather>>> forecastResource = weatherModel.getCitiesWithWeather();
        verify(weatherModel).loadCitiesWithWeatherFromRepo();
        verify(weatherRepo).getCitiesWithWeather();
        Resource<List<CityWithWeather>> forecastResult = LiveDataTestUtil.getValue(forecastResource);
        assertEquals(testCityWeatherList, forecastResult.data);
        assertNull(forecastResult.message);
        assertEquals(Status.SUCCESS, forecastResult.status);
    }

    @Test
    public void testOnCitySelected() {
        when(weatherRepo.setSelectedCity(anyInt()))
                .thenReturn(1L);

        int cityId = TestData.TestCity.testCity1.getCityId();
        weatherModel.onCitySelected(cityId);
        verify(weatherModel).loadSelectedCityFromRepo();
        verify(weatherModel).loadWeatherFromRepo();
        verify(weatherModel).loadForecastFromRepo();
    }
}
