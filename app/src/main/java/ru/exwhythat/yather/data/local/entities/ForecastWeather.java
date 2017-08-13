package ru.exwhythat.yather.data.local.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by exwhythat on 8/5/17.
 */

@Entity (foreignKeys =
            @ForeignKey(entity = City.class,
            parentColumns = "cityId",
            childColumns = "apiCityId",
            onDelete = ForeignKey.CASCADE))
public class ForecastWeather {

    @PrimaryKey(autoGenerate = true)
    private int forecastId;

    @Embedded
    private BaseWeather baseWeather;

    private int apiCityId;
    private double dayTemp;
    private double nightTemp;

    public ForecastWeather(BaseWeather baseWeather, int apiCityId, double dayTemp, double nightTemp) {
        this.baseWeather = baseWeather;
        this.apiCityId = apiCityId;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
    }

    public int getForecastId() {
        return forecastId;
    }

    public void setForecastId(int forecastId) {
        this.forecastId = forecastId;
    }

    public BaseWeather getBaseWeather() {
        return baseWeather;
    }

    public void setBaseWeather(BaseWeather baseWeather) {
        this.baseWeather = baseWeather;
    }

    public int getApiCityId() {
        return apiCityId;
    }

    public void setApiCityId(int apiCityId) {
        this.apiCityId = apiCityId;
    }

    public double getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(double dayTemp) {
        this.dayTemp = dayTemp;
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(double nightTemp) {
        this.nightTemp = nightTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForecastWeather)) return false;

        ForecastWeather that = (ForecastWeather) o;

        if (forecastId != that.forecastId) return false;
        if (apiCityId != that.apiCityId) return false;
        if (Double.compare(that.dayTemp, dayTemp) != 0) return false;
        if (Double.compare(that.nightTemp, nightTemp) != 0) return false;
        return baseWeather.equals(that.baseWeather);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = forecastId;
        result = 31 * result + baseWeather.hashCode();
        result = 31 * result + apiCityId;
        temp = Double.doubleToLongBits(dayTemp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nightTemp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ForecastWeather{" +
                "forecastId=" + forecastId +
                ", baseWeather=" + baseWeather +
                ", apiCityId=" + apiCityId +
                ", dayTemp=" + dayTemp +
                ", nightTemp=" + nightTemp +
                '}';
    }
}
