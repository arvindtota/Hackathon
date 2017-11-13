package android.support.v4.p002c;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.io.Writer;

public final class C0048b extends Writer {
    private final String f57a;
    private StringBuilder f58b = new StringBuilder(NotificationCompat.FLAG_HIGH_PRIORITY);

    public C0048b(String str) {
        this.f57a = str;
    }

    public final void close() {
        m54a();
    }

    public final void flush() {
        m54a();
    }

    public final void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                m54a();
            } else {
                this.f58b.append(c);
            }
        }
    }

    private void m54a() {
        if (this.f58b.length() > 0) {
            Log.d(this.f57a, this.f58b.toString());
            this.f58b.delete(0, this.f58b.length());
        }
    }
}
