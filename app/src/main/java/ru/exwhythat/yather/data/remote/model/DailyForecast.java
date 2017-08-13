package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by exwhythat on 8/3/17.
 */

public class DailyForecast {

    @SerializedName("dt") @Expose
    public int dateUnix;
    @SerializedName("temp") @Expose
    public Temp temp;
    @SerializedName("pressure") @Expose
    public double pressure;
    @SerializedName("humidity") @Expose
    public int humidity;
    @SerializedName("weather") @Expose
    public List<Weather> weather = null;
    @SerializedName("speed") @Expose
    public double speed;
    @SerializedName("deg") @Expose
    public int deg;
    @SerializedName("clouds") @Expose
    public int clouds;
    @SerializedName("rain") @Expose
    public double rain;

    public int getDateUnix() {
        return dateUnix;
    }

    public void setDateUnix(int dateUnix) {
        this.dateUnix = dateUnix;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
}
