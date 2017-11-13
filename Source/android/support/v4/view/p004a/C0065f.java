package android.support.v4.view.p004a;

import android.os.Build.VERSION;
import java.util.List;

public final class C0065f {
    private static final C0066g f103a;
    private final Object f104b;

    static {
        if (VERSION.SDK_INT >= 16) {
            f103a = new C0068h();
        } else {
            f103a = new C0067j();
        }
    }

    public C0065f() {
        this.f104b = f103a.mo114a(this);
    }

    public C0065f(Object obj) {
        this.f104b = obj;
    }

    public final Object m124a() {
        return this.f104b;
    }

    public static C0060a m121b() {
        return null;
    }

    public static boolean m122c() {
        return false;
    }

    public static List m123d() {
        return null;
    }
}
