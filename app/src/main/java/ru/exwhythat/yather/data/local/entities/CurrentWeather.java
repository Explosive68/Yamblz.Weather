package ru.exwhythat.yather.data.local.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by exwhythat on 8/4/17.
 */

@Entity (foreignKeys =
            @ForeignKey(entity = City.class,
            parentColumns = {"cityId"},
            childColumns = {"apiCityId"},
            onDelete = ForeignKey.CASCADE))
public class CurrentWeather {

    @PrimaryKey
    private int apiCityId;

    @Embedded
    private BaseWeather baseWeather;

    private int humidity;
    private double windSpeed;
    private double pressure;
    private double temp;

    public CurrentWeather(BaseWeather baseWeather, int apiCityId, int humidity, double windSpeed, double pressure,
                          double temp) {
        this.baseWeather = baseWeather;
        this.apiCityId = apiCityId;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.temp = temp;
    }

    public int getApiCityId() {
        return apiCityId;
    }

    public void setApiCityId(int apiCityId) {
        this.apiCityId = apiCityId;
    }

    public BaseWeather getBaseWeather() {
        return baseWeather;
    }

    public void setBaseWeather(BaseWeather baseWeather) {
        this.baseWeather = baseWeather;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentWeather)) return false;

        CurrentWeather that = (CurrentWeather) o;

        if (apiCityId != that.apiCityId) return false;
        if (humidity != that.humidity) return false;
        if (Double.compare(that.windSpeed, windSpeed) != 0) return false;
        if (Double.compare(that.pressure, pressure) != 0) return false;
        if (Double.compare(that.temp, temp) != 0) return false;
        return baseWeather.equals(that.baseWeather);
    }

    @Override
    public int hashCode() {
        int result;
        long temp1;
        result = apiCityId;
        result = 31 * result + baseWeather.hashCode();
        result = 31 * result + humidity;
        temp1 = Double.doubleToLongBits(windSpeed);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(pressure);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        temp1 = Double.doubleToLongBits(temp);
        result = 31 * result + (int) (temp1 ^ (temp1 >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "apiCityId=" + apiCityId +
                ", baseWeather=" + baseWeather +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", pressure=" + pressure +
                ", temp=" + temp +
                '}';
    }
}
