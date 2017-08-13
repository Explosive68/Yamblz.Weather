package ru.exwhythat.yather.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by exwhythat on 8/3/17.
 */

public class Rain {

    @SerializedName("3h") @Expose
    private double lastThreeHours;

    public double getLastThreeHours() {
        return lastThreeHours;
    }

    public void setLastThreeHours(double lastThreeHours) {
        this.lastThreeHours = lastThreeHours;
    }
}
