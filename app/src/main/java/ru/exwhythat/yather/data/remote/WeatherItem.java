package ru.exwhythat.yather.data.remote;

import ru.exwhythat.yather.data.remote.model.WeatherResponse;

/**
 * Created by exwhythat on 02.08.17.
 */

public class WeatherItem {

    private int mainTemp;
    private int minTemp;
    private int maxTemp;
    private double humidity;
    private int windSpeed;
    private String imageUrlName;

    public WeatherItem(WeatherResponse response) {
        mainTemp = (int) response.getMain().getTemp();
        minTemp = (int) response.getMain().getTempMin();
        maxTemp = (int) response.getMain().getTempMax();
        humidity = response.getMain().getHumidity();
        windSpeed = (int) response.getWind().getSpeed();
        imageUrlName = response.getWeather().get(0).getIcon();
    }

    public int getMainTemp() {
        return mainTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public double getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public String getImageUrlName() {
        return imageUrlName;
    }

    @Override
    public String toString() {
        return "WeatherItem{" +
                "mainTemp=" + mainTemp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", imageUrlName='" + imageUrlName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherItem)) return false;

        WeatherItem that = (WeatherItem) o;

        if (mainTemp != that.mainTemp) return false;
        if (minTemp != that.minTemp) return false;
        if (maxTemp != that.maxTemp) return false;
        if (Double.compare(that.humidity, humidity) != 0) return false;
        if (windSpeed != that.windSpeed) return false;
        return imageUrlName != null ? imageUrlName.equals(that.imageUrlName) : that.imageUrlName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mainTemp;
        result = 31 * result + minTemp;
        result = 31 * result + maxTemp;
        temp = Double.doubleToLongBits(humidity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + windSpeed;
        result = 31 * result + (imageUrlName != null ? imageUrlName.hashCode() : 0);
        return result;
    }
}
