package ru.exwhythat.yather.screens.settings;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.base_util.BaseFragment;
import ru.exwhythat.yather.base_util.livedata.Resource;
import ru.exwhythat.yather.base_util.livedata.Status;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.di.Injectable;
import ru.exwhythat.yather.utils.Utils;
import timber.log.Timber;

/**
 * Created by Sinjvf on 09.07.2017.
 * Fragment for settings screen
 */

public class SettingsFragment extends BaseFragment implements Injectable {

    public static final String TAG = "SettingsFragment";

    @BindView(R.id.interval_text)
    TextView intervalView;

    @BindView(R.id.city_name_title)
    TextView cityTitleView;
    @BindView(R.id.city_name_text)
    TextView cityNameView;
    @BindView(R.id.progress_city_selection)
    ProgressBar progressCitySelection;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    SettingsViewModel localModel;

    public static SettingsFragment getInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        localModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(SettingsViewModel.class);
        baseModel = localModel;
        localModel.getInterval().observe(this, this::setInterval);
        localModel.getCity().observe(this, this::setCityInfo);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fr_settings, container, false);
    }


    @OnClick(R.id.interval_selection)
    public void selectIntervalClicked(){
        localModel.selectIntervalClicked();
    }

    @OnClick(R.id.city_selection)
    public void selectCityClicked() {
        localModel.selectCityClicked();
    }

    public void setInterval(Long interval){
        int minutes = (int)(interval/ Utils.MINUTE);
        intervalView.setText(String.format(getString(R.string.n_min), minutes));
    }

    public void setCityInfo(Resource<City> city){
        if (city.status == Status.SUCCESS) {
            cityNameView.setText(city.data.getName());
            hideCityLoading();
        } else if (city.status == Status.LOADING) {
            showCityLoading();
        } else if (city.status == Status.ERROR) {
            Toast.makeText(getContext(), getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
            Timber.e(city.message);
            hideCityLoading();
        }
    }

    private void hideCityLoading() {
        cityTitleView.setVisibility(View.VISIBLE);
        cityNameView.setVisibility(View.VISIBLE);
        progressCitySelection.setVisibility(View.GONE);
    }

    private void showCityLoading() {
        cityTitleView.setVisibility(View.GONE);
        cityNameView.setVisibility(View.GONE);
        progressCitySelection.setVisibility(View.VISIBLE);
    }
}