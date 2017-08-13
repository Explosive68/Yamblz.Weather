package ru.exwhythat.yather.util;

import android.content.SharedPreferences;

import ru.exwhythat.yather.utils.Prefs;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by exwhythat on 30.07.17.
 */

public class UnitUtils {

    public static void mockPrefsWithDefaults(SharedPreferences preferences, SharedPreferences.Editor editor) {
        when(preferences.edit()).thenReturn(editor);
        when(editor.putLong(anyString(), anyLong())).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);

        long defaultInterval = Prefs.DEFAULT_INTERVAL;
        when(preferences.getLong(eq(Prefs.PREF_INTERVAL_TIME), anyLong())).thenReturn(defaultInterval);
    }
}
