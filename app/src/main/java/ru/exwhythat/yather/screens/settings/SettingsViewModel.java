package ru.exwhythat.yather.screens.settings;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.base_util.BaseFragmentViewModel;
import ru.exwhythat.yather.base_util.livedata.Resource;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.repository.LocalWeatherRepository;
import ru.exwhythat.yather.ui.SelectCityDialogFragment;
import ru.exwhythat.yather.ui.SelectIntervalDialogFragment;
import ru.exwhythat.yather.utils.Prefs;

/**
 * Created by Sinjvf on 09.07.2017.
 * View model sor settings fragment
 */
@Keep
public class SettingsViewModel extends BaseFragmentViewModel {

    private LocalWeatherRepository localRepo;

    private MutableLiveData<Long> interval = new MutableLiveData<>();
    private MutableLiveData<Resource<City>> city = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(Application application, LocalWeatherRepository localRepo) {
        super(application);
        this.localRepo = localRepo;
    }

    @NonNull
    public LiveData<Long> getInterval() {
        long savedInterval = Prefs.getIntervalTime(context);
        interval.setValue(savedInterval);
        return interval;
    }

    @NonNull
    public LiveData<Resource<City>> getCity() {
        setLiveLoading(city);
        loadSelectedCity();
        return city;
    }

    @VisibleForTesting
    public void loadSelectedCity() {
        localRepo.getSelectedCitySingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newCity -> setLiveSuccess(city, newCity),
                        err -> setLiveError(city, err));
    }

    @Override
    protected int getTitleStringId() {
        return R.string.menu_tools;
    }

    public void selectIntervalClicked(){
        new SelectIntervalDialogFragment().show(fragmentManager, null);
    }

    public void selectCityClicked() {
        new SelectCityDialogFragment().show(fragmentManager, null);
    }

    public void updateSelectedCity(Single<City> singleCity) {
        singleCity
                .doOnSuccess(this::writeCityToStorage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newCity -> setLiveSuccess(city, newCity),
                        err -> setLiveError(city, err));
    }

    @WorkerThread
    private void writeCityToStorage(City newCity) {
        localRepo.addNewCity(newCity);
        localRepo.selectCity(newCity.getCityId());
    }

    public void updateInterval(Long newInterval) {
        Prefs.setIntervalTime(context, newInterval);
        interval.setValue(newInterval);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
