package ru.exwhythat.yather.screens.about;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ru.exwhythat.yather.BuildConfig;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.base_util.BaseFragment;

/**
 * Created by Sinjvf on 09.07.2017.
 * Fragment for "about" screen
 */

public class AboutFragment extends BaseFragment {

    public static final String TAG = "AboutFragment";

    AboutViewModel localModel;
    @BindView(R.id.about_app_version)
    TextView versionName;

    public static AboutFragment getInstance() {
        return new AboutFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        localModel = ViewModelProviders.of(getActivity()).get(AboutViewModel.class);
        baseModel = localModel;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fr_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setVers();
    }

    private void setVers() {
        String name =  getString(R.string.about_version, BuildConfig.VERSION_NAME);
        versionName.setText(name);
    }
}

