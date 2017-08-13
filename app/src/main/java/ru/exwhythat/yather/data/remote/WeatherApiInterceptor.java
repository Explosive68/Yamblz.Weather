package ru.exwhythat.yather.data.remote;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.exwhythat.yather.utils.Prefs;

import static ru.exwhythat.yather.data.remote.WeatherApiConstants.WEATHER_API_KEY;

/**
 * Created by exwhythat on 15.07.17.
 */

public class WeatherApiInterceptor implements Interceptor {

    private static final String QUERY_PARAMETER_API_KEY = "APPID";
    private static final String QUERY_PARAMETER_UNITS_KEY = "units";
    private static final String QUERY_PARAMETER_LANGUAGE_KEY = "lang";

    private String units = null;
    private String language = null;

    @Inject
    public WeatherApiInterceptor(Context context) {
        units = Prefs.getUnits(context, WeatherApiConstants.DEFAULT_UNITS);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder()
                .addQueryParameter(QUERY_PARAMETER_API_KEY, WEATHER_API_KEY);
        if (!TextUtils.isEmpty(units)) {
            urlBuilder.addQueryParameter(QUERY_PARAMETER_UNITS_KEY, units);
        }
        if (!TextUtils.isEmpty(language)) {
            urlBuilder.addQueryParameter(QUERY_PARAMETER_LANGUAGE_KEY, language);
        }

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(urlBuilder.build());

        Request requestWithParameters = requestBuilder.build();
        return chain.proceed(requestWithParameters);
    }
}