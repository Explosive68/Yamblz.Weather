package ru.exwhythat.yather;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.repository.LocalWeatherRepository;
import ru.exwhythat.yather.data.repository.RemoteWeatherRepository;
import ru.exwhythat.yather.data.repository.WeatherCachingRepository;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;

import static org.mockito.Mockito.*;

/**
 * Created by exwhythat on 8/13/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class WeatherCachingRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    LocalWeatherRepository mockLocalRepo;

    @Mock
    RemoteWeatherRepository mockRemoteRepo;

    private WeatherCachingRepository weatherRepo;

    @Before
    public void prepare() {
        weatherRepo = new WeatherCachingRepository(mockLocalRepo, mockRemoteRepo);

        Single<CurrentWeather> testSingleWeather = Single.just(TestData.testCurrentWeather);
        when(mockLocalRepo.getSingleWeatherForSelectedCity()).thenReturn(testSingleWeather);

        Flowable<CurrentWeather> testFlowWeather = Flowable.create(e -> {}, BackpressureStrategy.BUFFER);
        when(mockLocalRepo.getFlowingWeatherForSelectedCity()).thenReturn(testFlowWeather);

        CurrentWeather weatherFromRemote = TestData.testCurrentWeather;
        weatherFromRemote.getBaseWeather().setDescr("FROM REMOTE");
        when(mockRemoteRepo.getCurrentWeatherForCity(anyInt())).thenReturn(Single.just(weatherFromRemote));

        when(mockLocalRepo.getSelectedCityIdSingle()).thenReturn(Single.just(TestData.TestCity.testCity1.getCityId()));
    }

    @Test
    public void testGetWeather_positive_flow() {
        Flowable<CurrentWeather> weatherFlow = weatherRepo.getWeather();

        weatherFlow.test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(TestData.testCurrentWeather)
                .assertNotComplete()
                .assertNotTerminated();
    }
}
