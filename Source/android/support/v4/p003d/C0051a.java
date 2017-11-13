package android.support.v4.p003d;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;

public final class C0051a {
    private static final C0052d f66b;
    private Object f67a;

    static {
        if (VERSION.SDK_INT >= 14) {
            f66b = new C0054c();
        } else {
            f66b = new C0053b();
        }
    }

    public C0051a(Context context) {
        this.f67a = f66b.mo93a(context);
    }

    public final void m72a(int i, int i2) {
        f66b.mo94a(this.f67a, i, i2);
    }

    public final boolean m73a() {
        return f66b.mo95a(this.f67a);
    }

    public final void m76b() {
        f66b.mo98b(this.f67a);
    }

    public final boolean m74a(float f) {
        return f66b.mo96a(this.f67a, f);
    }

    public final boolean m77c() {
        return f66b.mo99c(this.f67a);
    }

    public final boolean m75a(Canvas canvas) {
        return f66b.mo97a(this.f67a, canvas);
    }
}
