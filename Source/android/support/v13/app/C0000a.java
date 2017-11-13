package android.support.v13.app;

import android.app.Fragment;
import android.os.Build.VERSION;

public final class C0000a {
    static final C0001c f9a;

    static {
        if (VERSION.SDK_INT >= 15) {
            f9a = new C0004e();
        } else if (VERSION.SDK_INT >= 14) {
            f9a = new C0003d();
        } else {
            f9a = new C0002b();
        }
    }

    public static void m3a(Fragment fragment, boolean z) {
        f9a.mo1a(fragment, z);
    }

    public static void m4b(Fragment fragment, boolean z) {
        f9a.mo2b(fragment, z);
    }
}
