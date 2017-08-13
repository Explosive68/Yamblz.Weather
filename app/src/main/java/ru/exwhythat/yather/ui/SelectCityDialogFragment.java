package ru.exwhythat.yather.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.YatherApp;
import ru.exwhythat.yather.data.local.entities.City;
import ru.exwhythat.yather.data.repository.RemoteWeatherRepository;
import ru.exwhythat.yather.di.Injectable;
import ru.exwhythat.yather.screens.settings.SettingsViewModel;
import timber.log.Timber;

import static ru.exwhythat.yather.ui.PlaceAutocompleteAdapter.*;

/**
 * Created by Sinjvf on 16.07.2017.
 * Class for showing dialogs to user
 */

public class SelectCityDialogFragment extends DialogFragment implements Injectable {

    @BindView(R.id.autocomplete_cities)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.autocomplete_error)
    TextView autoCompleteErrorView;
    @BindView(R.id.autocomplete_progress)
    ProgressBar autoCompleteProgress;

    private SettingsViewModel settingsModel;

    private GoogleApiClient googleApiClient;

    @Inject
    RemoteWeatherRepository remoteRepo;

    private Unbinder unbinder;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private CompositeDisposable disposables = new CompositeDisposable();
    private PlaceAutocompleteAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.el_dialog_select_city, container, false);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        }
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsModel = ViewModelProviders.of(getActivity()).get(SettingsViewModel.class);
        prepareAutocomplete();
        showKeyboard();
    }

    private void showKeyboard() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    private void prepareAutocomplete() {
        //Build and set adapter
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .build();
        AutocompleteFilter cityFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        adapter = new PlaceAutocompleteAdapter(getContext(), googleApiClient, null,
                cityFilter, callbackListener);
        autoCompleteTextView.setAdapter(adapter);

        // Handle user interaction
        Disposable updatePlaceSubscription = itemClicks(autoCompleteTextView)
                .map(adapter::getItem)
                .map(AutocompletePrediction::getPlaceId)
                .switchMap(placeId -> getPlaceById(placeId).toObservable())
                .map(place -> remoteRepo.getCityIdByLatLng(place.getLatLng())
                        .map(cityId -> new City(cityId, place.getName().toString(), false))
                        .subscribeOn(Schedulers.io())
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(city -> {
                    settingsModel.updateSelectedCity(city);
                    dismiss();
                }, error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Timber.e("Place query did not complete. Error: " + error.getMessage());
                    dismiss();
                });
        disposables.add(updatePlaceSubscription);
    }

    private Observable<Integer> itemClicks(AutoCompleteTextView autoCompleteTextView) {
        return Observable.create(emit -> {
            AdapterView.OnItemClickListener listener = (adapterView, view, position, id) -> emit.onNext(position);
            autoCompleteTextView.setOnItemClickListener(listener);
            emit.setCancellable(() -> autoCompleteTextView.setOnItemClickListener(null));
        });
    }

    private Single<Place> getPlaceById(String placeId) {
        return Single.create(emit -> {
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
            placeResult.setResultCallback(places -> {
                if (places.getStatus().isSuccess()) {
                    final Place place = places.get(0);
                    Timber.i("Place details received: " + "Name=" + place.getName() +
                            ", coords=[" + place.getLatLng().latitude + ", " + place.getLatLng().longitude + "]");
                    emit.onSuccess(place);
                } else {
                    String errorMsg = String.format(getString(R.string.error_with_explanation), places.getStatus().toString());
                    emit.onError(new IllegalStateException(errorMsg));
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        googleApiClient.disconnect();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        disposables.dispose();
        if (adapter != null) {
            adapter.setCallbackListener(null);
        }
        callbackListener = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        YatherApp.getRefWatcher(getActivity()).watch(this);
    }

    /** These view manipulation methods could be called from the background thread,
     * so we use handler*/
    private void showError(String errorMsg) {
        mainHandler.post(() -> {
            if (autoCompleteErrorView != null && autoCompleteProgress != null) {
                autoCompleteErrorView.setText(errorMsg);
                autoCompleteErrorView.setVisibility(View.VISIBLE);
                autoCompleteProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void hideError() {
        mainHandler.post(() -> {
            if (autoCompleteErrorView != null) {
                autoCompleteErrorView.setText("");
                autoCompleteErrorView.setVisibility(View.GONE);
            }
        });
    }

    private void showLoading() {
        mainHandler.post(() -> {
            if (autoCompleteProgress != null) {
                autoCompleteProgress.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideLoading() {
        mainHandler.post(() -> {
            if (autoCompleteProgress != null) {
                autoCompleteProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private AutocompleteCallbackListener callbackListener = new AutocompleteCallbackListener() {
        @Override
        public void onError(String errorMsg) {
            showError(errorMsg);
        }

        @Override
        public void onError(@StringRes int stringResId) {
            onError(getString(stringResId));
        }

        @Override
        public void onHideError() {
            hideError();
        }

        @Override
        public void onShowLoading() {
            showLoading();
        }

        @Override
        public void onHideLoading() {
            hideLoading();
        }
    };
}
