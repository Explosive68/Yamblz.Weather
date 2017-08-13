package ru.exwhythat.yather;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.v4.app.FragmentManager;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import ru.exwhythat.yather.base_util.livedata.Resource;
import ru.exwhythat.yather.base_util.livedata.Status;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.repository.LocalWeatherRepository;
import ru.exwhythat.yather.screens.settings.SettingsViewModel;
import ru.exwhythat.yather.ui.SelectCityDialogFragment;
import ru.exwhythat.yather.ui.SelectIntervalDialogFragment;
import ru.exwhythat.yather.util.LiveDataTestUtil;
import ru.exwhythat.yather.util.RxImmediateSchedulerRule;
import ru.exwhythat.yather.util.TestData;
import ru.exwhythat.yather.util.UnitUtils;
import ru.exwhythat.yather.utils.Prefs;
import ru.exwhythat.yather.utils.Utils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SettingsViewModelTest {

    private SettingsViewModel settingsViewModel;

    // Mock main looper
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    Application mockApplication;

    @Mock
    Context mockApplicationContext;

    @Mock
    SharedPreferences mockPrefs;

    @Mock
    SharedPreferences.Editor mockEditor;

    @Mock
    LocalWeatherRepository mockLocalRepo;

    @Before
    public void prepare() {
        initMocks();
        settingsViewModel = Mockito.spy(new SettingsViewModel(mockApplication, mockLocalRepo));
        when(mockApplicationContext.getString(R.string.error_oops)).thenReturn("Oops! Unknown error");
    }

    private void initMocks() {
        when(mockApplication.getApplicationContext()).thenReturn(mockApplicationContext);
        when(mockApplication.getSharedPreferences(anyString(), anyInt())).thenReturn(mockPrefs);
        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockPrefs);

        UnitUtils.mockPrefsWithDefaults(mockPrefs, mockEditor);
    }

    @Test
    public void readEmptyIntervalReturnsDefault() {
        Long actualInterval = settingsViewModel.getInterval().getValue();
        assertNotNull(actualInterval);
        Long expectedInterval = Prefs.DEFAULT_INTERVAL;
        assertEquals(expectedInterval, actualInterval);
    }

    @Test
    public void readNotEmptyIntervalReturnsWritten() {
        Long expectedInterval = TimeUnit.MINUTES.toMillis(Utils.TIME_10);
        settingsViewModel.updateInterval(expectedInterval);
        Long actualInterval = settingsViewModel.getInterval().getValue();
        assertNotNull(actualInterval);
        assertEquals(expectedInterval, actualInterval);
    }

    @Test
    public void testGetCity() throws InterruptedException {
        City expectedCity = TestData.TestCity.testCity1;
        when(mockLocalRepo.getSelectedCitySingle())
                .thenReturn(Single.just(expectedCity));

        LiveData<Resource<City>> cityLive = settingsViewModel.getCity();
        Resource<City> cityRes = LiveDataTestUtil.getValue(cityLive);

        verify(settingsViewModel).loadSelectedCity();
        assertEquals(expectedCity, cityRes.data);
        assertNull(cityRes.message);
        assertEquals(Status.SUCCESS, cityRes.status);
    }

    @Test
    public void testGetCityFailedProperly() throws InterruptedException {
        when(mockLocalRepo.getSelectedCitySingle())
                .thenReturn(Single.error(new Throwable("Omg, something went wrong!")));

        LiveData<Resource<City>> cityLive = settingsViewModel.getCity();
        Resource<City> cityRes = LiveDataTestUtil.getValue(cityLive);

        verify(settingsViewModel).loadSelectedCity();
        assertNull(cityRes.data);
        assertNotNull(cityRes.message);
        assertEquals(Status.ERROR, cityRes.status);
    }

    @Test
    public void testUpdateSelectedCity() throws InterruptedException {
        City testCity = TestData.TestCity.testCity1;
        when(mockLocalRepo.getSelectedCitySingle())
                .thenReturn(Single.just(testCity));

        settingsViewModel.updateSelectedCity(Single.just(testCity));
        verify(mockLocalRepo).addNewCity(testCity);
        verify(mockLocalRepo).selectCity(testCity.getCityId());

        LiveData<Resource<City>> cityLive = settingsViewModel.getCity();
        Resource<City> cityRes = LiveDataTestUtil.getValue(cityLive);
        assertEquals(testCity, cityRes.data);
        assertNull(cityRes.message);
        assertEquals(Status.SUCCESS, cityRes.status);
    }
}