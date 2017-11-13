package android.support.v4.app;

import android.app.Activity;
import android.os.Build.VERSION;
import android.support.v4.p000a.C0012c;

public class ActivityCompat extends C0012c {
    public static boolean invalidateOptionsMenu(Activity activity) {
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        ActivityCompatHoneycomb.invalidateOptionsMenu(activity);
        return true;
    }
}
