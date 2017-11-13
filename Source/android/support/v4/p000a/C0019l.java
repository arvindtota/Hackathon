package android.support.v4.p000a;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class C0019l implements ThreadFactory {
    private final AtomicInteger f43a = new AtomicInteger(1);

    C0019l() {
    }

    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "ModernAsyncTask #" + this.f43a.getAndIncrement());
    }
}
