package ru.exwhythat.yather.di.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.exwhythat.yather.di.ViewModelKey;
import ru.exwhythat.yather.di.YatherViewModelFactory;
import ru.exwhythat.yather.screens.settings.SettingsViewModel;
import ru.exwhythat.yather.screens.weather.WeatherViewModel;

/**
 * Created by exwhythat on 02.08.17.
 */

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel bindWeatherViewModel(WeatherViewModel weatherViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(YatherViewModelFactory factory);
}
