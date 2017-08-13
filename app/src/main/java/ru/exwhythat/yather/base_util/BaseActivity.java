package ru.exwhythat.yather.base_util;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.annotation.Keep;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sinjvf on 11.07.2017.
 */

@Keep
public class BaseActivity extends AppCompatActivity implements LifecycleRegistryOwner {
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
