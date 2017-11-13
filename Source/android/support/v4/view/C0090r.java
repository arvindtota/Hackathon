package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.MotionEvent;

public final class C0090r {
    static final C0091u f119a;

    static {
        if (VERSION.SDK_INT >= 5) {
            f119a = new C0093t();
        } else {
            f119a = new C0092s();
        }
    }

    public static int m254a(MotionEvent motionEvent) {
        return (motionEvent.getAction() & 65280) >> 8;
    }

    public static int m255a(MotionEvent motionEvent, int i) {
        return f119a.mo159a(motionEvent, i);
    }

    public static int m256b(MotionEvent motionEvent, int i) {
        return f119a.mo160b(motionEvent, i);
    }

    public static float m257c(MotionEvent motionEvent, int i) {
        return f119a.mo161c(motionEvent, i);
    }

    public static float m258d(MotionEvent motionEvent, int i) {
        return f119a.mo162d(motionEvent, i);
    }
}
