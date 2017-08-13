package ru.exwhythat.yather.data.local.entities;

import java.util.Date;

/**
 * Created by exwhythat on 8/5/17.
 */

public class BaseWeather {

    private String main;
    private String descr;
    private String iconId;
    private Date date;

    public BaseWeather(String main, String descr, String iconId, Date date) {
        this.main = main;
        this.descr = descr;
        this.iconId = iconId;
        this.date = date;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseWeather)) return false;

        BaseWeather that = (BaseWeather) o;

        if (!main.equals(that.main)) return false;
        if (!descr.equals(that.descr)) return false;
        if (!iconId.equals(that.iconId)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = main.hashCode();
        result = 31 * result + descr.hashCode();
        result = 31 * result + iconId.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BaseWeather{" +
                "main='" + main + '\'' +
                ", descr='" + descr + '\'' +
                ", iconId='" + iconId + '\'' +
                ", date=" + date +
                '}';
    }
}
