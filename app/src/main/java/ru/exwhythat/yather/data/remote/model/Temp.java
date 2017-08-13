package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 8/8/17.
 */

public class Temp {

    @SerializedName("day") @Expose
    public double day;
    @SerializedName("min") @Expose
    public double min;
    @SerializedName("max") @Expose
    public double max;
    @SerializedName("night") @Expose
    public double night;
    @SerializedName("eve") @Expose
    public double eve;
    @SerializedName("morn") @Expose
    public double morn;

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }
}
