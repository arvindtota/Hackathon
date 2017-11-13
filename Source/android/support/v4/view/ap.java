package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public final class ap {
    static final as f111a;

    static {
        if (VERSION.SDK_INT >= 11) {
            f111a = new ar();
        } else {
            f111a = new aq();
        }
    }

    public static int m174a(ViewConfiguration viewConfiguration) {
        return f111a.mo129a(viewConfiguration);
    }
}
