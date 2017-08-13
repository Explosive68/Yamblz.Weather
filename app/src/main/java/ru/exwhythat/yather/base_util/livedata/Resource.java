package ru.exwhythat.yather.base_util.livedata;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ru.exwhythat.yather.base_util.livedata.Status.ERROR;
import static ru.exwhythat.yather.base_util.livedata.Status.LOADING;
import static ru.exwhythat.yather.base_util.livedata.Status.SUCCESS;

/**
 * Created by exwhythat on 8/11/17.
 */

public class Resource<T> {
    @NonNull public final Status status;
    @Nullable public final T data;
    @Nullable public final String message;
    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg) {
        return new Resource<>(ERROR, null, msg);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
