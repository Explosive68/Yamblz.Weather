package ru.exwhythat.yather.data.remote.model;

import android.support.annotation.StringDef;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.exwhythat.yather.data.local.entities.BaseWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/3/17.
 */

public class DailyForecastResponse extends BaseResponse {

    @SerializedName("cod") @Expose
    private String cod;
    @SerializedName("message") @Expose
    private double message;
    @SerializedName("cnt") @Expose
    private int responseLinesCount;
    @SerializedName("list") @Expose
    private List<DailyForecast> list = null;
    @SerializedName("city") @Expose
    private City city;

    @StringDef({WeatherState.clear, WeatherState.rain, WeatherState.clouds, WeatherState.snow, WeatherState.storm})
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeatherState {
        String clear = "Clear";
        String rain = "Rain";
        String clouds = "Clouds";
        String snow = "Snow";
        String storm = "Storm";
        String fog = "Fog";
    }
    
    public static class Mapper {
        public static List<ForecastWeather> toForecast(DailyForecastResponse response) {
            List<ForecastWeather> result = new ArrayList<>();

            for (DailyForecast forecast: response.getList()) {
                String main = forecast.getWeather().get(0).getMain();
                String descr = forecast.getWeather().get(0).getDescription();
                String icon = forecast.getWeather().get(0).getIcon();
                long date = forecast.getDateUnix();
                BaseWeather baseWeather = new BaseWeather(main, descr, icon, new Date(date*1000));

                int apiCityId = response.getCity().getId();
                double dayTemp = forecast.getTemp().getDay();
                double nightTemp = forecast.getTemp().getNight();
                result.add(new ForecastWeather(baseWeather, apiCityId, dayTemp, nightTemp));
            }

            return result;
        }
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getResponseLinesCount() {
        return responseLinesCount;
    }

    public void setResponseLinesCount(int responseLinesCount) {
        this.responseLinesCount = responseLinesCount;
    }

    public List<DailyForecast> getList() {
        return list;
    }

    public void setList(List<DailyForecast> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
