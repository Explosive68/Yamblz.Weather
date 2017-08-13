package ru.exwhythat.yather;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.exwhythat.yather.data.local.CityDao;
import ru.exwhythat.yather.data.local.CurrentWeatherDao;
import ru.exwhythat.yather.data.local.ForecastWeatherDao;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.data.repository.LocalWeatherRepository;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.exwhythat.yather.util.TestData.testCurrentWeather;
import static ru.exwhythat.yather.util.TestData.testForecastWeather1;
import static ru.exwhythat.yather.util.TestData.testForecastWeather2;

/**
 * This test is kinda messy (and not working yet).
 * App needs to be refactored a lot in a good way to make network operations easy for testing.
 */

@RunWith(MockitoJUnitRunner.class)
public class LocalWeatherRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    CityDao mockCityDao;

    @Mock
    CurrentWeatherDao mockCurrentWeatherDao;

    @Mock
    ForecastWeatherDao mockForecastWeatherDao;

    private LocalWeatherRepository localRepo;

    @Before
    public void prepare() {
        localRepo = new LocalWeatherRepository(mockCityDao, mockCurrentWeatherDao, mockForecastWeatherDao);
    }

    @Test
    public void testGetFlowingWeatherForSelectedCity() {
        localRepo.getFlowingWeatherForSelectedCity();
        verify(mockCurrentWeatherDao).getFlowForSelectedCity();
    }

    @Test
    public void testGetSingleWeatherForSelectedCity() {
        localRepo.getSingleWeatherForSelectedCity();
        verify(mockCurrentWeatherDao).getSingleForSelectedCity();
    }

    @Test
    public void testGetFlowingForecastForSelectedCity() {
        localRepo.getFlowingForecastForSelectedCity();
        verify(mockForecastWeatherDao).getFlowForSelectedCity();
    }

    @Test
    public void testGetSingleForecastForSelectedCity_empty_list_throws_error() {
        when(mockForecastWeatherDao.getSingleForSelectedCity()).thenReturn(Single.just(new ArrayList<>()));
        localRepo.getSingleForecastForSelectedCity()
                .test()
                .assertError(Throwable.class);
        verify(mockForecastWeatherDao).getSingleForSelectedCity();
    }

    @Test
    public void testGetSingleForecastForSelectedCity_not_empty_list_passed_forward() {
        List<ForecastWeather> testForecastList = new ArrayList<>();
        testForecastList.add(testForecastWeather1);
        testForecastList.add(testForecastWeather2);
        when(mockForecastWeatherDao.getSingleForSelectedCity()).thenReturn(Single.just(testForecastList));
        localRepo.getSingleForecastForSelectedCity()
                .test()
                .assertNoErrors()
                .assertResult(testForecastList);
        verify(mockForecastWeatherDao).getSingleForSelectedCity();
    }

    @Test
    public void testGetFlowingCitiesWithWeather() {
        localRepo.getFlowingCitiesWithWeather();
        verify(mockCityDao).getCitiesWithWeather();
    }

    @Test
    public void testGetSelectedCityIdSingle() {
        localRepo.getSelectedCityIdSingle();
        verify(mockCityDao).getSelectedCityIdSingle();
    }

    @Test
    public void testGetSelectedCitySingle() {
        localRepo.getSelectedCitySingle();
        verify(mockCityDao).getSelectedCitySingle();
    }

    @Test
    public void testUpdateCurrentWeather() {
        localRepo.updateCurrentWeather(testCurrentWeather);
        verify(mockCurrentWeatherDao).insert(testCurrentWeather);
    }

    @Test
    public void testUpdateForecast_empty_list_does_nothing() {
        List<ForecastWeather> emptyForecastList = new ArrayList<>();
        localRepo.updateForecast(emptyForecastList);
        verify(mockForecastWeatherDao, never()).deleteForCity(anyInt());
        verify(mockForecastWeatherDao, never()).insertAll(anyList());
    }

    @Test
    public void testUpdateForecast_not_empty_list_updates() {
        List<ForecastWeather> testForecastList = new ArrayList<>();
        testForecastList.add(testForecastWeather1);
        testForecastList.add(testForecastWeather2);
        localRepo.updateForecast(testForecastList);
        verify(mockForecastWeatherDao).deleteForCity(testForecastList.get(0).getApiCityId());
        verify(mockForecastWeatherDao).insertAll(testForecastList);
    }

    @Test
    public void testAddNewCity() {
        localRepo.addNewCity(TestData.TestCity.testCity1);
        verify(mockCityDao).insert(TestData.TestCity.testCity1);
    }

    @Test
    public void testSelectCity() {
        int cityId = TestData.TestCity.testCity1.getCityId();
        localRepo.selectCity(cityId);
        verify(mockCityDao).unselectAll();
        verify(mockCityDao).setSelected(cityId);
    }

    @Test
    public void testDeleteCity() {
        int cityId = TestData.TestCity.testCity1.getCityId();
        localRepo.deleteCity(cityId);
        verify(mockCityDao).deleteCity(cityId);
    }
}
