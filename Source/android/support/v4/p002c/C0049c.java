package android.support.v4.p002c;

public final class C0049c {
    private static final Object f59a = new Object();
    private boolean f60b;
    private int[] f61c;
    private Object[] f62d;
    private int f63e;

    public C0049c() {
        this(10);
    }

    private C0049c(int i) {
        this.f60b = false;
        int g = C0049c.m57g(10);
        this.f61c = new int[g];
        this.f62d = new Object[g];
        this.f63e = 0;
    }

    public final void m62b(int i) {
        int a = C0049c.m55a(this.f61c, 0, this.f63e, i);
        if (a >= 0 && this.f62d[a] != f59a) {
            this.f62d[a] = f59a;
            this.f60b = true;
        }
    }

    public final void m63c(int i) {
        if (this.f62d[i] != f59a) {
            this.f62d[i] = f59a;
            this.f60b = true;
        }
    }

    private void m56c() {
        int i = this.f63e;
        int[] iArr = this.f61c;
        Object[] objArr = this.f62d;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != f59a) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                }
                i2++;
            }
        }
        this.f60b = false;
        this.f63e = i2;
    }

    public final void m60a(int i, Object obj) {
        int a = C0049c.m55a(this.f61c, 0, this.f63e, i);
        if (a >= 0) {
            this.f62d[a] = obj;
            return;
        }
        a ^= -1;
        if (a >= this.f63e || this.f62d[a] != f59a) {
            if (this.f60b && this.f63e >= this.f61c.length) {
                m56c();
                a = C0049c.m55a(this.f61c, 0, this.f63e, i) ^ -1;
            }
            if (this.f63e >= this.f61c.length) {
                int g = C0049c.m57g(this.f63e + 1);
                Object obj2 = new int[g];
                Object obj3 = new Object[g];
                System.arraycopy(this.f61c, 0, obj2, 0, this.f61c.length);
                System.arraycopy(this.f62d, 0, obj3, 0, this.f62d.length);
                this.f61c = obj2;
                this.f62d = obj3;
            }
            if (this.f63e - a != 0) {
                System.arraycopy(this.f61c, a, this.f61c, a + 1, this.f63e - a);
                System.arraycopy(this.f62d, a, this.f62d, a + 1, this.f63e - a);
            }
            this.f61c[a] = i;
            this.f62d[a] = obj;
            this.f63e++;
            return;
        }
        this.f61c[a] = i;
        this.f62d[a] = obj;
    }

    public final int m58a() {
        if (this.f60b) {
            m56c();
        }
        return this.f63e;
    }

    public final int m64d(int i) {
        if (this.f60b) {
            m56c();
        }
        return this.f61c[i];
    }

    public final Object m65e(int i) {
        if (this.f60b) {
            m56c();
        }
        return this.f62d[i];
    }

    public final int m66f(int i) {
        if (this.f60b) {
            m56c();
        }
        return C0049c.m55a(this.f61c, 0, this.f63e, i);
    }

    public final void m61b() {
        int i = this.f63e;
        Object[] objArr = this.f62d;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.f63e = 0;
        this.f60b = false;
    }

    private static int m55a(int[] iArr, int i, int i2, int i3) {
        int i4 = -1;
        int i5 = i2 + 0;
        while (i5 - i4 > 1) {
            int i6 = (i5 + i4) / 2;
            if (iArr[i6] < i3) {
                i4 = i6;
            } else {
                i5 = i6;
            }
        }
        if (i5 == i2 + 0) {
            return (i2 + 0) ^ -1;
        }
        return iArr[i5] != i3 ? i5 ^ -1 : i5;
    }

    private static int m57g(int i) {
        int i2 = i * 4;
        for (int i3 = 4; i3 < 32; i3++) {
            if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            }
        }
        return i2 / 4;
    }

    public final Object m59a(int i) {
        int a = C0049c.m55a(this.f61c, 0, this.f63e, i);
        if (a < 0 || this.f62d[a] == f59a) {
            return null;
        }
        return this.f62d[a];
    }
}
