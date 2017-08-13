package ru.exwhythat.yather.screens.weather;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import ru.exwhythat.yather.data.local.entities.CityWithWeather;

/**
 * Created by exwhythat on 8/8/17.
 */

public class CitiesDiffUtilCallback extends DiffUtil.Callback{

    private List<CityWithWeather> oldCities;
    private List<CityWithWeather> newCities;

    public CitiesDiffUtilCallback(List<CityWithWeather> oldCities, List<CityWithWeather> newCities) {
        this.oldCities = oldCities;
        this.newCities = newCities;
    }

    @Override
    public int getOldListSize() {
        return oldCities.size();
    }

    @Override
    public int getNewListSize() {
        return newCities.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCities.get(oldItemPosition).getCity().getCityId() == newCities.get(newItemPosition).getCity().getCityId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCities.get(oldItemPosition).equals(newCities.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
