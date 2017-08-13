package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sinjvf on 16.07.2017.
 * Data class describes main weather data
 */

public class Main {

    @SerializedName("temp") @Expose
    private float temp;
    @SerializedName("pressure") @Expose
    private float pressure;
    @SerializedName("humidity") @Expose
    private int humidity;
    @SerializedName("temp_min") @Expose
    private float tempMin;
    @SerializedName("temp_max") @Expose
    private float tempMax;
    @SerializedName("sea_level") @Expose
    private float seaLevel;
    @SerializedName("grnd_level") @Expose
    private float grndLevel;
    @SerializedName("temp_kf") @Expose
    private float tempKf;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
    }

    public float getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(float grndLevel) {
        this.grndLevel = grndLevel;
    }

    public float getTempKf() {
        return tempKf;
    }

    public void setTempKf(float tempKf) {
        this.tempKf = tempKf;
    }
}
