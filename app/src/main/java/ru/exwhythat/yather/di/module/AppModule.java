package ru.exwhythat.yather.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.exwhythat.yather.utils.Prefs;

/**
 * Created by exwhythat on 01.08.17.
 */

@Module(includes = {ViewModelModule.class, NetworkModule.class, DbModule.class})
public class AppModule {

    @Singleton
    @Provides
    Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return Prefs.getPrefs(application);
    }
}