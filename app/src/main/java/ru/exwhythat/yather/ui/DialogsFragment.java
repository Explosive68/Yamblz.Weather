package ru.exwhythat.yather.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.YatherApp;
import timber.log.Timber;

/**
 * Created by Sinjvf on 16.07.2017.
 * Class for showing dialogs to user
 */

public class DialogsFragment extends DialogFragment {

    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.cancel)
    TextView cancel;

    Unbinder unbinder;

    private String title, text;
    private boolean haveCancelButton;
    private SingleObserver<Boolean> action;
    private static final String TITLE_KEY = "title_key";
    private static final String TEXT_KEY = "text_key";
    private static final String HAVE_CANCEL_KEY = "have_cancel_key";


    public static DialogsFragment getInstance(String title, String text, boolean haveCancelButton) {
        DialogsFragment dialog = new DialogsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_KEY, title);
        bundle.putString(TEXT_KEY, text);
        bundle.putBoolean(HAVE_CANCEL_KEY, haveCancelButton);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setAction(SingleObserver<Boolean> action) {
        this.action = action;
    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        title = bundle.getString(TITLE_KEY);
        text = bundle.getString(TEXT_KEY);
        haveCancelButton = bundle.getBoolean(HAVE_CANCEL_KEY);
    }

    public void setData() {
        if (!TextUtils.isEmpty(title))
            titleView.setText(title);
        else
            titleView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text))
            textView.setText(text);
        else
            textView.setVisibility(View.GONE);
        if (!haveCancelButton)
            cancel.setVisibility(View.GONE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.el_dialog, container, false);
        unbinder = ButterKnife.bind(this, v);
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }catch (NullPointerException e){
            Timber.e(e.getMessage());
        }
        getData();
        setData();
        return v;
    }

    @OnClick(R.id.ok)
    public void onClickOk() {
        if (action != null)
            Single.fromObservable(Observable.fromArray(true)).subscribe(action);
        dismiss();
    }

    @OnClick(R.id.cancel)
    public void onClickCancel() {
        if (action != null)
            Single.fromObservable(Observable.fromArray(false)).subscribe(action);
        dismiss();
    }

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
