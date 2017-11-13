package android.support.v4.view.p004a;

import android.os.Build.VERSION;

public final class C0060a {
    private static final C0061c f101a;
    private final Object f102b;

    static {
        if (VERSION.SDK_INT >= 16) {
            f101a = new C0064d();
        } else if (VERSION.SDK_INT >= 14) {
            f101a = new C0063b();
        } else {
            f101a = new C0062e();
        }
    }

    public C0060a(Object obj) {
        this.f102b = obj;
    }

    public final Object m108a() {
        return this.f102b;
    }

    public final void m109a(int i) {
        f101a.mo111a(this.f102b, i);
    }

    public final void m111a(boolean z) {
        f101a.mo113a(this.f102b, z);
    }

    public final void m110a(CharSequence charSequence) {
        f101a.mo112a(this.f102b, charSequence);
    }

    public final int hashCode() {
        return this.f102b == null ? 0 : this.f102b.hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        C0060a c0060a = (C0060a) obj;
        if (this.f102b == null) {
            if (c0060a.f102b != null) {
                return false;
            }
            return true;
        } else if (this.f102b.equals(c0060a.f102b)) {
            return true;
        } else {
            return false;
        }
    }
}
