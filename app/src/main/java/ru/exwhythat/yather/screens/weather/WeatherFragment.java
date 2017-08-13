package ru.exwhythat.yather.screens.weather;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.activity.MainActivity;
import ru.exwhythat.yather.base_util.livedata.Resource;
import ru.exwhythat.yather.base_util.livedata.Status;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.local.entities.CityWithWeather;
import ru.exwhythat.yather.data.local.entities.CurrentWeather;
import ru.exwhythat.yather.data.local.entities.ForecastWeather;
import ru.exwhythat.yather.di.Injectable;
import ru.exwhythat.yather.base_util.BaseFragment;
import ru.exwhythat.yather.utils.StringUtils;
import timber.log.Timber;

import static ru.exwhythat.yather.data.remote.model.DailyForecastResponse.*;

/**
 * Created by Sinjvf on 09.07.2017.
 * Fragment for weather screen - main application screen
 */

public class WeatherFragment extends BaseFragment implements Injectable, SwipeRefreshLayout.OnRefreshListener,
CitiesAdapter.OnCityInteractionListener {

    public static final String TAG = "WeatherFragment";

    @BindView(R.id.city_name)
    TextView cityNameView;
    @BindView(R.id.main_tempr)
    TextView mainTempr;
    @BindView(R.id.last_updated)
    TextView lastUpdatedView;
    @BindView(R.id.image_weather)
    ImageView imageWeather;
    @BindView(R.id.wind_text)
    TextView windView;
    @BindView(R.id.humidity_text)
    TextView humidityView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.citiesRecycler)
    RecyclerView citiesRecycler;
    @BindView(R.id.forecastRecycler)
    RecyclerView forecastRecycler;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CitiesAdapter citiesAdapter;
    private ForecastAdapter forecastAdapter;

    private WeatherViewModel localModel;

    private boolean isTwoPane;

    public static WeatherFragment getInstance(){
        return new WeatherFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        localModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
        baseModel = localModel;
        super.onActivityCreated(savedInstanceState);
        isTwoPane = ((MainActivity)getActivity()).getIsTwoPane();

        localModel.getSelectedCity().observe(this, this::setCity);
        localModel.getWeather().observe(this, this::setWeather);
        localModel.getLastUpdate().observe(this, this::setLastUpdate);

        initRecyclers();
        localModel.getCitiesWithWeather().observe(this, this::setCitiesWithWeather);
        localModel.getForecast().observe(this, this::setForecast);
    }

    private void initRecyclers() {
        citiesAdapter = new CitiesAdapter(getContext(), this, this);
        int citiesOrientation = isTwoPane ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL;
        initRecycler(citiesRecycler, citiesAdapter, citiesOrientation);
        ItemTouchHelper citiesItemSwipeHelper = createItemTouchHelper(isTwoPane);
        citiesItemSwipeHelper.attachToRecyclerView(citiesRecycler);

        forecastAdapter = new ForecastAdapter(getContext(), this, this::onForecastClicked);
        initRecycler(forecastRecycler, forecastAdapter, LinearLayoutManager.VERTICAL);
        forecastRecycler.setNestedScrollingEnabled(false);
    }

    private ItemTouchHelper createItemTouchHelper(boolean isTwoPane) {
        int swipeDirectionOne = isTwoPane ? ItemTouchHelper.LEFT : ItemTouchHelper.UP;
        int swipeDirectionTwo = isTwoPane ? ItemTouchHelper.RIGHT : ItemTouchHelper.DOWN;
        ItemTouchHelper.SimpleCallback cityItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, swipeDirectionOne | swipeDirectionTwo) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getContext(), getString(R.string.city_list_item_deleted), Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                Integer cityId = citiesAdapter.getCityId(position);
                if (cityId != null) {
                    // remove from db
                    localModel.deleteCity(cityId);
                }
            }
        };
        return new ItemTouchHelper(cityItemTouchCallback);
    }

    @Override
    public void onCitySelected(int cityId) {
        localModel.onCitySelected(cityId);
    }

    private void onForecastClicked(int forecastItemId) {
        Toast.makeText(getContext(), "Forecast clicked: "  + forecastItemId, Toast.LENGTH_SHORT).show();
    }

    private void initRecycler(RecyclerView rv, RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), orientation, false);
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        rv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fr_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setCity(Resource<City> cityRes) {
        if (cityRes.status == Status.SUCCESS) {
            cityNameView.setText(cityRes.data.getName());
        }
        Timber.d("setCity: " + cityRes);
    }

    private void setWeather(Resource<CurrentWeather> weatherRes) {
        if (weatherRes.status == Status.SUCCESS) {
            setWeatherDataToViews(weatherRes.data);
            setIsLoading(false);
        } else if (weatherRes.status == Status.LOADING) {
            setIsLoading(true);
            return;
        } else if (weatherRes.status == Status.ERROR) {
            Toast.makeText(getContext(), weatherRes.message, Toast.LENGTH_SHORT).show();
            setIsLoading(false);
        }
        Timber.d("setWeather: " + weatherRes);
    }

    private void setForecast(Resource<List<ForecastWeather>> forecastRes) {
        if (forecastRes.status == Status.SUCCESS) {
            forecastAdapter.updateForecast(forecastRes.data);
            setIsLoading(false);
        } else if (forecastRes.status == Status.LOADING) {
            setIsLoading(true);
            return;
        } else if (forecastRes.status == Status.ERROR) {
            Toast.makeText(getContext(), forecastRes.message, Toast.LENGTH_SHORT).show();
            setIsLoading(false);
        }
        Timber.d("setForecast: " + forecastRes);
    }

    private void setCitiesWithWeather(Resource<List<CityWithWeather>> citiesRes) {
        if (citiesRes.status == Status.SUCCESS) {
            citiesAdapter.updateCities(citiesRes.data);
            setIsLoading(false);
        } else if (citiesRes.status == Status.LOADING) {
            setIsLoading(true);
            return;
        } else if (citiesRes.status == Status.ERROR) {
            Toast.makeText(getContext(), citiesRes.message, Toast.LENGTH_SHORT).show();
            setIsLoading(false);
        }
        Timber.d("setCitiesWithWeather: " + citiesRes);
    }

    private void setWeatherDataToViews(CurrentWeather weather) {
        @WeatherState String weatherState = weather.getBaseWeather().getMain();
        imageWeather.setImageDrawable(ContextCompat.getDrawable(getContext(), getDrawableResIdForWeatherState(weatherState)));

        mainTempr.setText(StringUtils.getFormattedTemperature(getContext(), weather.getTemp()));
        windView.setText(StringUtils.getFormattedWind(getContext(), weather.getWindSpeed()));
        humidityView.setText(StringUtils.getFormattedHumidity(getContext(), weather.getHumidity()));
    }

    public static @DrawableRes int getDrawableResIdForWeatherState(@WeatherState String weatherState) {
        switch (weatherState) {
            case WeatherState.clear:
                return R.drawable.ic_sun_big;
            case WeatherState.rain:
                return R.drawable.ic_rain_big;
            case WeatherState.clouds:
                return R.drawable.ic_cloud_big;
            case WeatherState.snow:
                return R.drawable.ic_snow_big;
            case WeatherState.storm:
                return R.drawable.ic_storm_big;
            case WeatherState.fog:
                return R.drawable.ic_fog;
            default:
                Timber.e("Unknown weather state: " + weatherState);
                return R.drawable.ic_help_outline;
        }
    }

    private void setLastUpdate(String lastUpdate){
        lastUpdatedView.setText(String.format(getString(R.string.last_update), lastUpdate));
    }

    @Override
    public void onRefresh() {
        localModel.forceUpdateWeatherAndForecast();
    }

    private void setIsLoading(boolean isLoading) {
        swipeRefreshLayout.setRefreshing(isLoading);
    }
}

