package android.support.v4.view;

import android.support.v4.view.p004a.C0060a;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

final class C0078c implements C0077j {
    final /* synthetic */ C0058a f112a;
    final /* synthetic */ C0076b f113b;

    C0078c(C0076b c0076b, C0058a c0058a) {
        this.f113b = c0076b;
        this.f112a = c0058a;
    }

    public final boolean mo143a(View view, AccessibilityEvent accessibilityEvent) {
        return this.f112a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public final void mo145b(View view, AccessibilityEvent accessibilityEvent) {
        this.f112a.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public final void mo142a(View view, Object obj) {
        this.f112a.onInitializeAccessibilityNodeInfo(view, new C0060a(obj));
    }

    public final void mo146c(View view, AccessibilityEvent accessibilityEvent) {
        this.f112a.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public final boolean mo144a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.f112a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public final void mo141a(View view, int i) {
        this.f112a.sendAccessibilityEvent(view, i);
    }

    public final void mo147d(View view, AccessibilityEvent accessibilityEvent) {
        this.f112a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }
}
