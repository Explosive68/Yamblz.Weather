package ru.exwhythat.yather.screens.weather;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.utils.DateUtils;
import ru.exwhythat.yather.utils.StringUtils;
import timber.log.Timber;

import static ru.exwhythat.yather.data.remote.model.DailyForecastResponse.WeatherState;

/**
 * Created by exwhythat on 8/8/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> implements LifecycleObserver {

    private Context context;
    private List<ForecastWeather> forecast = new ArrayList<>();
    private OnForecastClickListener clickListener;

    private ForecastDiffUtilCallback diffUtilCallback;

    public ForecastAdapter(Context context, LifecycleOwner lifecycleOwner, OnForecastClickListener listener) {
        this.context = context;
        this.clickListener = listener;
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        final ForecastWeather forecast = this.forecast.get(position);

        String dateString = DateUtils.dateToString(forecast.getBaseWeather().getDate());
        holder.forecastDate.setText(capitalizeFirstLetter(dateString));

        @WeatherState String weatherState = forecast.getBaseWeather().getMain();
        @DrawableRes int weatherIconId = getDrawableResIdForWeatherState(weatherState);
        holder.forecastIcon.setImageDrawable(ContextCompat.getDrawable(context, weatherIconId));

        holder.forecastDayTemp.setText(StringUtils.getFormattedTemperature(context, forecast.getDayTemp()));
        holder.forecastNightTemp.setText(StringUtils.getFormattedTemperature(context, forecast.getNightTemp()));

        holder.itemView.setOnClickListener(view -> clickListener.onForecastClicked(forecast.getApiCityId()));
    }

    private String capitalizeFirstLetter(String word) {
        if (!TextUtils.isEmpty(word)) {
            StringBuilder sb = new StringBuilder(word);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            return sb.toString();
        } else {
            return "";
        }
    }

    private @DrawableRes int getDrawableResIdForWeatherState(@WeatherState String weatherState) {
        switch (weatherState) {
            case WeatherState.clear:
                return R.drawable.ic_sun;
            case WeatherState.rain:
                return R.drawable.ic_rain;
            case WeatherState.clouds:
                return R.drawable.ic_cloud;
            case WeatherState.snow:
                return R.drawable.ic_snow;
            case WeatherState.storm:
                return R.drawable.ic_storm;
            case WeatherState.fog:
                return R.drawable.ic_fog;
            default:
                Timber.e("Unknown weather state: " + weatherState);
                return R.drawable.ic_help_outline;
        }
    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }

    public void updateForecast(List<ForecastWeather> newForecast) {
        diffUtilCallback = new ForecastDiffUtilCallback(forecast, newForecast);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        forecast = newForecast;
        diffResult.dispatchUpdatesTo(this);
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.forecastDate)
        TextView forecastDate;
        @BindView(R.id.forecastIcon)
        ImageView forecastIcon;
        @BindView(R.id.forecastDayTemp)
        TextView forecastDayTemp;
        @BindView(R.id.forecastNightTemp)
        TextView forecastNightTemp;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnForecastClickListener {
        void onForecastClicked(int forecastItemId);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void clear() {
        context = null;
        clickListener = null;
        diffUtilCallback = null;
    }
}
