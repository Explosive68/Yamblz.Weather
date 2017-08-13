package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sinjvf on 16.07.2017.
 * Data class describes wind
 */

public class Wind {

    @SerializedName("speed") @Expose
    private double speed;
    @SerializedName("deg") @Expose
    private double deg;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}
