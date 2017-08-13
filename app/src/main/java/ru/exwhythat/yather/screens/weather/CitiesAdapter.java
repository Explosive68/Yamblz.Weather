package ru.exwhythat.yather.screens.weather;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CityWithWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.utils.StringUtils;

import static ru.exwhythat.yather.data.remote.model.DailyForecastResponse.*;

/**
 * Created by exwhythat on 8/8/17.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> implements LifecycleObserver {

    private Context context;
    private List<CityWithWeather> citiesWithWeather = new ArrayList<>();
    private OnCityInteractionListener selectionListener;

    private CitiesDiffUtilCallback diffUtilCallback;

    public CitiesAdapter(Context context, LifecycleOwner lifecycleOwner, OnCityInteractionListener listener) {
        this.context = context;
        this.selectionListener = listener;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final CityWithWeather cityWithWeather = citiesWithWeather.get(position);
        City city = cityWithWeather.getCity();
        CurrentWeather weather = cityWithWeather.getWeather();

        int selectedItemColor = ContextCompat.getColor(context, R.color.cityListSelectedItemColor);
        int unselectedItemColor = ContextCompat.getColor(context, R.color.cityListUnselectedItemColor);
        holder.itemView.setBackgroundColor(city.isSelected() ? selectedItemColor : unselectedItemColor);

        holder.cityName.setText(city.getName());
        if (weather != null) {
            if (holder.cityTempr != null) {
                holder.cityTempr.setText(StringUtils.getFormattedTemperature(context, weather.getTemp()));
            }
            if (holder.weatherIcon != null) {
                @WeatherState String weatherState = weather.getBaseWeather().getMain();
                holder.weatherIcon.setImageDrawable(ContextCompat.getDrawable(context,
                        WeatherFragment.getDrawableResIdForWeatherState(weatherState)));

                holder.humidityText.setText(StringUtils.getFormattedHumidity(context, weather.getHumidity()));
                holder.windText.setText(StringUtils.getFormattedWind(context, weather.getWindSpeed()));
            }
        }
        holder.itemView.setOnClickListener(view -> selectionListener.onCitySelected(city.getCityId()));
    }

    @Override
    public int getItemCount() {
        return citiesWithWeather.size();
    }

    public void updateCities(List<CityWithWeather> newCities) {
        diffUtilCallback = new CitiesDiffUtilCallback(citiesWithWeather, newCities);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        citiesWithWeather = newCities;
        diffResult.dispatchUpdatesTo(CitiesAdapter.this);
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city_list_item_name)
        TextView cityName;

        @Nullable
        @BindView(R.id.city_list_item_tempr)
        TextView cityTempr;

        @Nullable
        @BindView(R.id.city_list_item_weather_icon)
        ImageView weatherIcon;

        @Nullable
        @BindView(R.id.city_list_item_humidity_text)
        TextView humidityText;

        @Nullable
        @BindView(R.id.city_list_item_wind_text)
        TextView windText;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCityInteractionListener {
        void onCitySelected(int cityId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void clear() {
        context = null;
        selectionListener = null;
        diffUtilCallback = null;
    }

    @Nullable
    public Integer getCityId(int position) {
        if (citiesWithWeather != null && citiesWithWeather.size() > 0) {
            return citiesWithWeather.get(position).getCity().getCityId();
        }
        return null;
    }
}
