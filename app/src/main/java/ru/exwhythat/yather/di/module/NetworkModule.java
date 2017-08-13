package ru.exwhythat.yather.di.module;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.exwhythat.yather.BuildConfig;
import ru.exwhythat.yather.data.remote.WeatherApi;
import ru.exwhythat.yather.data.remote.WeatherApiInterceptor;

/**
 * Created by exwhythat on 8/4/17.
 */

@Module
public class NetworkModule {

    private static final String WEATHER_API_END_POINT = "http://api.openweathermap.org/data/2.5/";

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    WeatherApi provideWeatherApi(Context context, OkHttpClient baseHttpClient) {
        OkHttpClient weatherHttpClient = baseHttpClient.newBuilder()
                .addInterceptor(new WeatherApiInterceptor(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_API_END_POINT)
                .client(weatherHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WeatherApi.class);
    }
}
