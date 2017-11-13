package android.support.v4.view;

import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.View;

public final class ah {
    static final ao f110a;

    static {
        int i = VERSION.SDK_INT;
        if (i >= 17) {
            f110a = new an();
        } else if (i >= 16) {
            f110a = new am();
        } else if (i >= 14) {
            f110a = new al();
        } else if (i >= 11) {
            f110a = new ak();
        } else if (i >= 9) {
            f110a = new aj();
        } else {
            f110a = new ai();
        }
    }

    public static boolean m144a(View view, int i) {
        return f110a.mo124a(view, i);
    }

    public static int m140a(View view) {
        return f110a.mo120a(view);
    }

    public static void m142a(View view, C0058a c0058a) {
        f110a.mo122a(view, c0058a);
    }

    public static void m145b(View view) {
        f110a.mo125b(view);
    }

    public static void m143a(View view, Runnable runnable) {
        f110a.mo123a(view, runnable);
    }

    public static int m147c(View view) {
        return f110a.mo127c(view);
    }

    public static void m146b(View view, int i) {
        f110a.mo126b(view, 1);
    }

    public static void m141a(View view, int i, Paint paint) {
        f110a.mo121a(view, i, null);
    }
}
