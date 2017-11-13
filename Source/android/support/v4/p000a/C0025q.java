package android.support.v4.p000a;

import android.os.Handler;
import android.os.Message;

final class C0025q extends Handler {
    private C0025q() {
    }

    public final void handleMessage(Message message) {
        C0024p c0024p = (C0024p) message.obj;
        switch (message.what) {
            case 1:
                C0010k.m38c(c0024p.f48a, c0024p.f49b[0]);
                return;
            case 2:
                C0010k.m36b();
                return;
            default:
                return;
        }
    }
}
