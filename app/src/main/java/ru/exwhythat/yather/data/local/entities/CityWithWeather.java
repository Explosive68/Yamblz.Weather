package ru.exwhythat.yather.data.local.entities;

import android.arch.persistence.room.Embedded;

import javax.annotation.Nullable;

/**
 * Created by exwhythat on 8/11/17.
 */

public class CityWithWeather {
    @Embedded
    private City city;
    @Embedded
    private CurrentWeather weather;

    public CityWithWeather(City city, CurrentWeather weather) {
        this.city = city;
        this.weather = weather;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Nullable
    public CurrentWeather getWeather() {
        return weather;
    }

    public void setWeather(CurrentWeather weather) {
        this.weather = weather;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityWithWeather)) return false;

        CityWithWeather that = (CityWithWeather) o;

        if (!city.equals(that.city)) return false;
        return weather != null ? weather.equals(that.weather) : that.weather == null;
    }

    @Override
    public int hashCode() {
        int result = city.hashCode();
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityWithWeather{" +
                "city=" + city +
                ", weather=" + weather +
                '}';
    }
}