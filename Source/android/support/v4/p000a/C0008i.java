package android.support.v4.p000a;

import android.content.Context;
import android.support.v4.p002c.C0047a;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class C0008i {
    int f17f;
    C0018j f18g;
    Context f19h;
    boolean f20i = false;
    boolean f21j = false;
    boolean f22k = true;
    boolean f23l = false;

    public C0008i(Context context) {
        this.f19h = context.getApplicationContext();
    }

    public final void m19a(int i, C0018j c0018j) {
        if (this.f18g != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.f18g = c0018j;
        this.f17f = i;
    }

    public final void m20a(C0018j c0018j) {
        if (this.f18g == null) {
            throw new IllegalStateException("No listener register");
        } else if (this.f18g != c0018j) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        } else {
            this.f18g = null;
        }
    }

    public final void m22d() {
        this.f20i = true;
        this.f22k = false;
        this.f21j = false;
        mo510e();
    }

    protected void mo510e() {
    }

    public final void m24f() {
        mo11a();
    }

    protected void mo11a() {
    }

    public final void m25g() {
        this.f20i = false;
    }

    public final void m26h() {
        this.f21j = true;
    }

    public final void m27i() {
        this.f22k = true;
        this.f20i = false;
        this.f21j = false;
        this.f23l = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        C0047a.m53a(this, stringBuilder);
        stringBuilder.append(" id=");
        stringBuilder.append(this.f17f);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void mo12a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.f17f);
        printWriter.print(" mListener=");
        printWriter.println(this.f18g);
        printWriter.print(str);
        printWriter.print("mStarted=");
        printWriter.print(this.f20i);
        printWriter.print(" mContentChanged=");
        printWriter.print(this.f23l);
        printWriter.print(" mAbandoned=");
        printWriter.print(this.f21j);
        printWriter.print(" mReset=");
        printWriter.println(this.f22k);
    }
}
