package ru.exwhythat.yather.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import ru.exwhythat.yather.YatherApp;
import ru.exwhythat.yather.di.module.MainActivityModule;
import ru.exwhythat.yather.di.module.AppModule;

/**
 * Created by exwhythat on 01.08.17.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(YatherApp yatherApp);
}
