package android.support.v4.p000a;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

public final class C0013d {
    private static final C0014e f42a;

    static {
        int i = VERSION.SDK_INT;
        if (i >= 15) {
            f42a = new C0017h();
        } else if (i >= 11) {
            f42a = new C0016g();
        } else {
            f42a = new C0015f();
        }
    }

    public static Intent m47a(ComponentName componentName) {
        return f42a.mo16a(componentName);
    }
}
