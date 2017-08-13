package ru.exwhythat.yather;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ru.exwhythat.yather.di.AppInjector;
import timber.log.Timber;

/**
 * Created by Sinjvf on 16.07.2017.
 * Application
 * Init some libraries' instances if need
 */

public class YatherApp extends Application implements HasActivityInjector {

    private RefWatcher refWatcher;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new YatherDebugTree());
            Stetho.initializeWithDefaults(this);
        } else {
            Timber.plant(new CrashReportingTree());
        }

        AppInjector.init(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        YatherApp application = (YatherApp) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    /** Customized Timber.DebugTree */
    private static class YatherDebugTree extends Timber.DebugTree {
        @Override
        protected String createStackElementTag(StackTraceElement element) {
            // Add line number to every log message
            return super.createStackElementTag(element) + ":" + element.getLineNumber();
        }
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            log(priority, tag, message);
        }
    }
}
