package android.support.v4.p000a;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.p002c.C0050d;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class C0009a extends C0008i {
    volatile C0011b f24a;
    volatile C0011b f25b;
    long f26c;
    long f27d = -10000;
    Handler f28e;

    public abstract Object mo509c();

    public C0009a(Context context) {
        super(context);
    }

    protected final void mo11a() {
        super.mo11a();
        if (this.f24a != null) {
            if (this.f25b != null) {
                if (this.f24a.f39b) {
                    this.f24a.f39b = false;
                    this.f28e.removeCallbacks(this.f24a);
                }
                this.f24a = null;
            } else if (this.f24a.f39b) {
                this.f24a.f39b = false;
                this.f28e.removeCallbacks(this.f24a);
                this.f24a = null;
            } else {
                if (this.f24a.m43a(false)) {
                    this.f25b = this.f24a;
                }
                this.f24a = null;
            }
        }
        this.f24a = new C0011b(this);
        m31b();
    }

    final void m31b() {
        if (this.f25b == null && this.f24a != null) {
            if (this.f24a.f39b) {
                this.f24a.f39b = false;
                this.f28e.removeCallbacks(this.f24a);
            }
            if (this.f26c <= 0 || SystemClock.uptimeMillis() >= this.f27d + this.f26c) {
                this.f24a.m39a(C0010k.f32d, null);
                return;
            }
            this.f24a.f39b = true;
            this.f28e.postAtTime(this.f24a, this.f27d + this.f26c);
        }
    }

    final void m29a(C0011b c0011b, Object obj) {
        if (this.f25b == c0011b) {
            this.f27d = SystemClock.uptimeMillis();
            this.f25b = null;
            m31b();
        }
    }

    public final void mo12a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.mo12a(str, fileDescriptor, printWriter, strArr);
        if (this.f24a != null) {
            printWriter.print(str);
            printWriter.print("mTask=");
            printWriter.print(this.f24a);
            printWriter.print(" waiting=");
            printWriter.println(this.f24a.f39b);
        }
        if (this.f25b != null) {
            printWriter.print(str);
            printWriter.print("mCancellingTask=");
            printWriter.print(this.f25b);
            printWriter.print(" waiting=");
            printWriter.println(this.f25b.f39b);
        }
        if (this.f26c != 0) {
            printWriter.print(str);
            printWriter.print("mUpdateThrottle=");
            C0050d.m70a(this.f26c, printWriter);
            printWriter.print(" mLastLoadCompleteTime=");
            C0050d.m69a(this.f27d, SystemClock.uptimeMillis(), printWriter);
            printWriter.println();
        }
    }
}
