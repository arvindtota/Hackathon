package android.support.v4.p001b;

import android.os.Build.VERSION;
import android.os.Parcelable.Creator;

public final class C0043a {
    public static Creator m52a(C0045c c0045c) {
        if (VERSION.SDK_INT >= 13) {
            C0046d c0046d = new C0046d(c0045c);
        }
        return new C0044b(c0045c);
    }
}
