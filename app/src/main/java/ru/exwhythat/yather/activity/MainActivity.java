package ru.exwhythat.yather.activity;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.exwhythat.yather.R;
import ru.exwhythat.yather.base_util.BaseActivity;
import ru.exwhythat.yather.base_util.BaseFragment;
import ru.exwhythat.yather.events.OpenNewFragmentEvent;
import ru.exwhythat.yather.screens.about.AboutFragment;
import ru.exwhythat.yather.screens.settings.SettingsFragment;
import ru.exwhythat.yather.screens.weather.WeatherFragment;
import timber.log.Timber;

/**
 * Created by Sinjvf on 09.07.2017.
 * Main activity class.
 * Contains and handles  the navigation drawer, the toolbar and the fragment content
 */

@Keep
public class MainActivity extends BaseActivity
        implements MainActivityInterface, HasSupportFragmentInjector, FragmentManager.OnBackStackChangedListener {

    protected final String TAG = "tag:" + getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private Menu menu;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isTwoPane = findViewById(R.id.landEmptyView) != null;

        setSupportActionBar(toolbar);
        Timber.tag(TAG);

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(this);
        if (savedInstanceState == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_container, WeatherFragment.getInstance(), WeatherFragment.TAG)
                    .setCustomAnimations(0, 0, R.anim.fast_fade_in, R.anim.fast_fade_out)
                    .commit();
        }

        setToolbarHomeAsUpAndMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        boolean isRootFragmentVisible = !isInnerFragmentVisible();
        setMenuVisibility(isRootFragmentVisible);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BaseFragment fgm;
        String tag;
        switch (item.getItemId()) {
            case R.id.nav_tools:
                fgm = SettingsFragment.getInstance();
                tag = SettingsFragment.TAG;
                break;
            case R.id.nav_about:
                fgm = AboutFragment.getInstance();
                tag = AboutFragment.TAG;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        changeFragment(new OpenNewFragmentEvent(fgm, true, tag));
        return true;
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }

    public void changeFragment(@NonNull OpenNewFragmentEvent event) {
        FragmentManager fm = getSupportFragmentManager();
        if (event.isAddToBackStack()) {
            fm.beginTransaction()
                    .addToBackStack(null)
                    .setCustomAnimations(R.anim.fast_fade_in, R.anim.fast_fade_out, R.anim.fast_fade_in, R.anim.fast_fade_out)
                    .replace(R.id.fragment_container, event.getFgm())
                    .commit();
        } else {
            fm.beginTransaction()
                    .replace(R.id.fragment_container, event.getFgm())
                    .commit();
        }
    }

    @Override
    public void setTitleText(@StringRes int titleResId) {
        if (titleResId != -1) {
            String title = getString(titleResId);
            ActionBar actionbar = getSupportActionBar();
            if (actionbar == null) return;
            actionbar.setTitle(title);
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onBackStackChanged() {
        setToolbarHomeAsUpAndMenu();
    }

    public void setToolbarHomeAsUpAndMenu(){
        //Enable Up button only if there are entries in the back stack
        boolean isInner = isInnerFragmentVisible();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(isInner);
        }
        setMenuVisibility(!isInner);
    }

    private boolean isInnerFragmentVisible() {
        return getSupportFragmentManager().getBackStackEntryCount() > 0;
    }

    private void setMenuVisibility(boolean isVisible) {
        if (menu != null) {
            menu.setGroupVisible(R.id.nav_group, isVisible);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public boolean getIsTwoPane() {
        return isTwoPane;
    }
}
