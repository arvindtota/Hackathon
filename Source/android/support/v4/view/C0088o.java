package android.support.v4.view;

import android.support.v4.app.NotificationCompat;

final class C0088o implements C0087q {
    C0088o() {
    }

    private static int m248a(int i, int i2, int i3, int i4, int i5) {
        Object obj = 1;
        Object obj2 = (i2 & i3) != 0 ? 1 : null;
        int i6 = i4 | i5;
        if ((i2 & i6) == 0) {
            obj = null;
        }
        if (obj2 != null) {
            if (obj == null) {
                return i & (i6 ^ -1);
            }
            throw new IllegalArgumentException("bad arguments");
        } else if (obj != null) {
            return i & (i3 ^ -1);
        } else {
            return i;
        }
    }

    private static int m249b(int i) {
        int i2;
        if ((i & 192) != 0) {
            i2 = i | 1;
        } else {
            i2 = i;
        }
        if ((i2 & 48) != 0) {
            i2 |= 2;
        }
        return i2 & 247;
    }

    public final boolean mo158a(int i, int i2) {
        if (C0088o.m248a(C0088o.m248a(C0088o.m249b(i) & 247, i2, 1, 64, NotificationCompat.FLAG_HIGH_PRIORITY), i2, 2, 16, 32) == i2) {
            return true;
        }
        return false;
    }

    public final boolean mo157a(int i) {
        return (C0088o.m249b(i) & 247) == 0;
    }
}
