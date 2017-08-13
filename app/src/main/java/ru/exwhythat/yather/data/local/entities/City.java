package ru.exwhythat.yather.data.local.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by exwhythat on 8/4/17.
 */

@Entity
public class City {

    @PrimaryKey private int cityId;
    private String name;
    private boolean isSelected = false;

    public City(int cityId, String name, boolean isSelected) {
        this.cityId = cityId;
        this.name = name;
        this.isSelected = isSelected;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (cityId != city.cityId) return false;
        if (isSelected != city.isSelected) return false;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        int result = cityId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (isSelected ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
