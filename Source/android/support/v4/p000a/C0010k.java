package android.support.v4.p000a;

import android.support.v4.app.NotificationCompat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class C0010k {
    private static final ThreadFactory f29a = new C0019l();
    private static final BlockingQueue f30b = new LinkedBlockingQueue(10);
    private static final C0025q f31c = new C0025q();
    public static final Executor f32d = new ThreadPoolExecutor(5, NotificationCompat.FLAG_HIGH_PRIORITY, 1, TimeUnit.SECONDS, f30b, f29a);
    private static volatile Executor f33e = f32d;
    private final C0020s f34f = new C0021m(this);
    private final FutureTask f35g = new C0022n(this, this.f34f);
    private volatile int f36h = C0026r.f50a;
    private final AtomicBoolean f37i = new AtomicBoolean();

    protected abstract Object mo13a(Object... objArr);

    private Object m35b(Object obj) {
        f31c.obtainMessage(1, new C0024p(this, obj)).sendToTarget();
        return obj;
    }

    protected void mo15a(Object obj) {
    }

    protected static void m36b() {
    }

    protected void mo14a() {
    }

    public final boolean m43a(boolean z) {
        return this.f35g.cancel(false);
    }

    public final C0010k m39a(Executor executor, Object... objArr) {
        if (this.f36h != C0026r.f50a) {
            switch (C0023o.f47a[this.f36h - 1]) {
                case 1:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.f36h = C0026r.f51b;
        this.f34f.f44b = null;
        executor.execute(this.f35g);
        return this;
    }

    static /* synthetic */ void m37b(C0010k c0010k, Object obj) {
        if (!c0010k.f37i.get()) {
            c0010k.m35b(obj);
        }
    }

    static /* synthetic */ void m38c(C0010k c0010k, Object obj) {
        if (c0010k.f35g.isCancelled()) {
            c0010k.mo14a();
        } else {
            c0010k.mo15a(obj);
        }
        c0010k.f36h = C0026r.f52c;
    }
}
