package android.support.v4.view.p004a;

import android.view.accessibility.AccessibilityNodeInfo;

class C0063b extends C0062e {
    C0063b() {
    }

    public final void mo111a(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).addAction(i);
    }

    public final void mo112a(Object obj, CharSequence charSequence) {
        ((AccessibilityNodeInfo) obj).setClassName(charSequence);
    }

    public final void mo113a(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setScrollable(z);
    }
}
