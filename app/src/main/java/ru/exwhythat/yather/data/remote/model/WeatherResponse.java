package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import ru.exwhythat.yather.data.local.entities.BaseWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;

/**
 * Created by Sinjvf on 16.07.2017.
 * Data class describes all weather data.
 * it is the response of weather request
 */

public class WeatherResponse extends BaseResponse {

    @SerializedName("dt") @Expose
    private long dateCalculation;
    @SerializedName("main") @Expose
    private Main main;
    @SerializedName("weather") @Expose
    private List<Weather> weather = null;
    @SerializedName("clouds") @Expose
    private Clouds clouds;
    @SerializedName("rain") @Expose
    private Rain rain;
    @SerializedName("snow") @Expose
    private Snow snow;
    @SerializedName("wind") @Expose
    private Wind wind;
    @SerializedName("sys") @Expose
    private Sys sys;
    @SerializedName("coord") @Expose
    private Coord coord;
    @SerializedName("base") @Expose
    private String base;
    @SerializedName("visibility") @Expose
    private int visibility;
    @SerializedName("id") @Expose
    private int cityId;
    @SerializedName("name") @Expose
    private String cityName;
    @SerializedName("cod") @Expose
    private int cod;

    public static class Mapper {
        public static CurrentWeather toCurrentWeather(WeatherResponse response) {
            String main = response.getWeather().get(0).getMain();
            String descr = response.getWeather().get(0).getDescription();
            String icon = response.getWeather().get(0).getIcon();
            long dateUnix = response.getDateCalculation();
            BaseWeather baseWeather = new BaseWeather(main, descr, icon, new Date(dateUnix * 1000));

            int apiCityId = response.getCityId();
            int humidity = response.getMain().getHumidity();
            double windSpeed = response.getWind().getSpeed();
            double pressure = response.getMain().getPressure();
            double temp = response.getMain().getTemp();

            return new CurrentWeather(baseWeather, apiCityId, humidity, windSpeed, pressure, temp);
        }
    }

    public long getDateCalculation() {
        return dateCalculation;
    }

    public void setDateCalculation(long dateCalculation) {
        this.dateCalculation = dateCalculation;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "dateCalculation=" + dateCalculation +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", rain=" + rain +
                ", snow=" + snow +
                ", wind=" + wind +
                ", sys=" + sys +
                ", coord=" + coord +
                ", base='" + base + '\'' +
                ", visibility=" + visibility +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", cod=" + cod +
                '}';
    }
}
