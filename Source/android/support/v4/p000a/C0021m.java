package android.support.v4.p000a;

import android.os.Process;

final class C0021m extends C0020s {
    final /* synthetic */ C0010k f45a;

    C0021m(C0010k c0010k) {
        this.f45a = c0010k;
        super();
    }

    public final Object call() {
        this.f45a.f37i.set(true);
        Process.setThreadPriority(10);
        return this.f45a.m35b(this.f45a.mo13a(this.b));
    }
}
