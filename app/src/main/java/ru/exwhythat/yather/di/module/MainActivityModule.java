package ru.exwhythat.yather.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.exwhythat.yather.activity.MainActivity;

/**
 * Created by exwhythat on 03.08.17.
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();
}
