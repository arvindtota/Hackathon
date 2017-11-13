package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.VelocityTracker;

public final class ad {
    static final ag f109a;

    static {
        if (VERSION.SDK_INT >= 11) {
            f109a = new af();
        } else {
            f109a = new ae();
        }
    }

    public static float m136a(VelocityTracker velocityTracker, int i) {
        return f109a.mo119a(velocityTracker, i);
    }
}
