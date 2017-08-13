package ru.exwhythat.yather.base_util;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.exwhythat.yather.YatherApp;
import ru.exwhythat.yather.activity.MainActivityInterface;
import timber.log.Timber;

/**
 * Created by Sinjvf on 09.07.2017.
 * Parent class for fragments
 */

public class BaseFragment extends LifecycleFragment {
    protected final String TAG = "tag:" + getClass().getSimpleName();
    protected BaseFragmentViewModel baseModel;
    protected Unbinder unbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseModel.getTitle().observe(this, this::setTitleText);
        baseModel.setFragmentManager(getActivity().getSupportFragmentManager());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        getArgs();
    }

    protected void setTitleText(int titleResId) {
        try {
            ((MainActivityInterface) getActivity()).setTitleText(titleResId);
        } catch (ClassCastException e) {
            Timber.e(e.getMessage());
        }
    }

    //if we have the progress bar and want to show\hide it
    protected void setProgressStatus(int status) {}

    //if we have args in fragment and want to get it
    protected void getArgs() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        YatherApp.getRefWatcher(getActivity()).watch(this);
    }
}
