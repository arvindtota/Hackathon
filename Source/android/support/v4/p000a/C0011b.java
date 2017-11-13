package android.support.v4.p000a;

import android.os.SystemClock;
import java.util.concurrent.CountDownLatch;

final class C0011b extends C0010k implements Runnable {
    Object f38a;
    boolean f39b;
    final /* synthetic */ C0009a f40c;
    private CountDownLatch f41e = new CountDownLatch(1);

    C0011b(C0009a c0009a) {
        this.f40c = c0009a;
    }

    protected final void mo15a(Object obj) {
        try {
            C0008i c0008i = this.f40c;
            if (c0008i.f24a != this) {
                c0008i.m29a(this, obj);
            } else if (!c0008i.f21j) {
                c0008i.f27d = SystemClock.uptimeMillis();
                c0008i.f24a = null;
                if (c0008i.f18g != null) {
                    c0008i.f18g.onLoadComplete(c0008i, obj);
                }
            }
            this.f41e.countDown();
        } catch (Throwable th) {
            this.f41e.countDown();
        }
    }

    protected final void mo14a() {
        try {
            this.f40c.m29a(this, this.f38a);
        } finally {
            this.f41e.countDown();
        }
    }

    public final void run() {
        this.f39b = false;
        this.f40c.m31b();
    }

    protected final /* synthetic */ Object mo13a(Object[] objArr) {
        this.f38a = this.f40c.mo509c();
        return this.f38a;
    }
}
