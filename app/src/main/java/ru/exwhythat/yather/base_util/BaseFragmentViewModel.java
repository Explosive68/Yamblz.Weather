package ru.exwhythat.yather.base_util;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;

import java.net.UnknownHostException;

import ru.exwhythat.yather.R;
import ru.exwhythat.yather.base_util.livedata.Resource;

/**
 * Created by Sinjvf on 09.07.2017.
 * Parent class for fragment's ViewModels
 */

public abstract class BaseFragmentViewModel extends AndroidViewModel {

    protected final Context context;
    protected FragmentManager fragmentManager;

    private MutableLiveData<Integer> titleId = new MutableLiveData<>();

    public BaseFragmentViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Integer> getTitle() {
        titleId.setValue(getTitleStringId());
        return titleId;
    }

    protected abstract @StringRes int getTitleStringId();

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    protected  <T> void setLiveSuccess(MutableLiveData<Resource<T>> mutableLiveData, T value) {
        mutableLiveData.setValue(Resource.success(value));
    }

    protected  <T> void setLiveError(MutableLiveData<Resource<T>> mutableLiveData, Throwable e) {
        String msg;
        if (e instanceof UnknownHostException) {
            msg = context.getString(R.string.error_network);
        } else {
            msg = context.getString(R.string.error_oops);
        }
        mutableLiveData.setValue(Resource.error(msg));
    }

    protected <T> void setLiveLoading(MutableLiveData<Resource<T>> mutableLiveData) {
        mutableLiveData.setValue(Resource.loading());
    }
}
