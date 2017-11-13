package android.support.v4.view;

import android.view.KeyEvent;

final class C0089p implements C0087q {
    C0089p() {
    }

    public final boolean mo158a(int i, int i2) {
        return KeyEvent.metaStateHasModifiers(i, i2);
    }

    public final boolean mo157a(int i) {
        return KeyEvent.metaStateHasNoModifiers(i);
    }
}
