package ru.exwhythat.yather.screens.weather;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.exwhythat.yather.data.local.entities.ForecastWeather;

/**
 * Created by exwhythat on 8/8/17.
 */

public class ForecastDiffUtilCallback extends DiffUtil.Callback{

    List<ForecastWeather> oldForecast;
    List<ForecastWeather> newForecast;

    public ForecastDiffUtilCallback(List<ForecastWeather> oldForecast, List<ForecastWeather> newForecast) {
        this.oldForecast = oldForecast;
        this.newForecast = newForecast;
    }

    @Override
    public int getOldListSize() {
        return oldForecast.size();
    }

    @Override
    public int getNewListSize() {
        return newForecast.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldForecast.get(oldItemPosition).getForecastId() == newForecast.get(newItemPosition).getForecastId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldForecast.get(oldItemPosition).equals(newForecast.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
