package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;

public final class C0086n {
    static final C0087q f118a;

    static {
        if (VERSION.SDK_INT >= 11) {
            f118a = new C0089p();
        } else {
            f118a = new C0088o();
        }
    }

    public static boolean m245a(KeyEvent keyEvent, int i) {
        return f118a.mo158a(keyEvent.getMetaState(), 1);
    }

    public static boolean m244a(KeyEvent keyEvent) {
        return f118a.mo157a(keyEvent.getMetaState());
    }
}
