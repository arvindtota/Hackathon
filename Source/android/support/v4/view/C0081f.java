package android.support.v4.view;

import android.os.Bundle;
import android.support.v4.view.p004a.C0060a;
import android.support.v4.view.p004a.C0065f;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

final class C0081f implements C0080m {
    final /* synthetic */ C0058a f114a;
    final /* synthetic */ C0079e f115b;

    C0081f(C0079e c0079e, C0058a c0058a) {
        this.f115b = c0079e;
        this.f114a = c0058a;
    }

    public final boolean mo152a(View view, AccessibilityEvent accessibilityEvent) {
        return this.f114a.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public final void mo154b(View view, AccessibilityEvent accessibilityEvent) {
        this.f114a.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public final void mo150a(View view, Object obj) {
        this.f114a.onInitializeAccessibilityNodeInfo(view, new C0060a(obj));
    }

    public final void mo155c(View view, AccessibilityEvent accessibilityEvent) {
        this.f114a.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public final boolean mo153a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.f114a.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    public final void mo149a(View view, int i) {
        this.f114a.sendAccessibilityEvent(view, i);
    }

    public final void mo156d(View view, AccessibilityEvent accessibilityEvent) {
        this.f114a.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }

    public final Object mo148a(View view) {
        C0065f accessibilityNodeProvider = this.f114a.getAccessibilityNodeProvider(view);
        return accessibilityNodeProvider != null ? accessibilityNodeProvider.m124a() : null;
    }

    public final boolean mo151a(View view, int i, Bundle bundle) {
        return this.f114a.performAccessibilityAction(view, i, bundle);
    }
}
