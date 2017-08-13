package ru.exwhythat.yather.events;

import io.reactivex.annotations.NonNull;
import ru.exwhythat.yather.base_util.BaseFragment;

/**
 * Created by Sinjvf on 09.07.2017.
 * Data class for changing fragment in main activity
 * If addToBackStack we add this fragment to backstack (It's unexpectedly, isn't it?=))
 */

public class OpenNewFragmentEvent {
    private final BaseFragment fgm;
    private final boolean addToBackStack;
    private final String tag;

    public OpenNewFragmentEvent(@NonNull BaseFragment fgm, boolean addToBackStack, @NonNull String tag) {
        this.fgm = fgm;
        this.addToBackStack = addToBackStack;
        this.tag = tag;
    }

    @NonNull
    public BaseFragment getFgm() {
        return fgm;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }
}
