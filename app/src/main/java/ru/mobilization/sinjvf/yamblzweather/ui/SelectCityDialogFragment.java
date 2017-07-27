package ru.mobilization.sinjvf.yamblzweather.ui;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.mobilization.sinjvf.yamblzweather.R;
import ru.mobilization.sinjvf.yamblzweather.screens.settings.SettingsViewModel;
import timber.log.Timber;

import static ru.mobilization.sinjvf.yamblzweather.ui.PlaceAutocompleteAdapter.*;

/**
 * Created by Sinjvf on 16.07.2017.
 * Class for showing dialogs to user
 */

public class SelectCityDialogFragment extends DialogFragment {

    @BindView(R.id.autocomplete_cities)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.autocomplete_error)
    TextView autoCompleteErrorView;
    @BindView(R.id.autocomplete_progress)
    ProgressBar autoCompleteProgress;

    private SettingsViewModel settingsModel;

    private GoogleApiClient googleApiClient;

    Unbinder unbinder;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private CompositeDisposable disposables = new CompositeDisposable();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.el_dialog_select_city, container, false);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
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
        PlaceAutocompleteAdapter adapter = new PlaceAutocompleteAdapter(getContext(), googleApiClient, null,
                cityFilter, callbackListener);
        autoCompleteTextView.setAdapter(adapter);

        // Handle user interaction
        Disposable updatePlaceSubscription = itemClicks(autoCompleteTextView)
                .map(adapter::getItem)
                .map(AutocompletePrediction::getPlaceId)
                .switchMap(placeId -> getPlaceById(placeId).toObservable())
                .subscribe(place -> {
                    settingsModel.updateCityInfo(place);
                    dismiss();
                }, error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Timber.e("Place query did not complete. Error: " + error.getMessage());
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
                if (!places.getStatus().isSuccess()) {
                    String errorMsg = String.format(getString(R.string.error_with_explanation), places.getStatus().toString());
                    emit.onError(new IllegalStateException(errorMsg));
                } else {
                    final Place place = places.get(0);
                    Timber.i("Place details received: " + "Name=" + place.getName() +
                            ", coords=[" + place.getLatLng().latitude + ", " + place.getLatLng().longitude + "]");
                    emit.onSuccess(place);
                }
                emit.setCancellable(places::release);
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
        super.onDestroyView();
    }

    private void showError(String errorMsg) {
        mainHandler.post(() -> {
            autoCompleteErrorView.setText(errorMsg);
            autoCompleteErrorView.setVisibility(View.VISIBLE);
            autoCompleteProgress.setVisibility(View.INVISIBLE);
        });
    }

    private void hideError() {
        mainHandler.post(() -> {
            autoCompleteErrorView.setText("");
            autoCompleteErrorView.setVisibility(View.GONE);
        });
    }

    private void showLoading() {
        mainHandler.post(() -> autoCompleteProgress.setVisibility(View.VISIBLE));
    }

    private void hideLoading() {
        mainHandler.post(() -> autoCompleteProgress.setVisibility(View.INVISIBLE));
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