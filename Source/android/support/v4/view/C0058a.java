package android.support.v4.view;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.p004a.C0060a;
import android.support.v4.view.p004a.C0065f;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class C0058a {
    private static final Object DEFAULT_DELEGATE = IMPL.mo131a();
    private static final C0074d IMPL;
    final Object mBridge = IMPL.mo132a(this);

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new C0079e();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new C0076b();
        } else {
            IMPL = new C0075g();
        }
    }

    Object getBridge() {
        return this.mBridge;
    }

    public void sendAccessibilityEvent(View view, int i) {
        IMPL.mo133a(DEFAULT_DELEGATE, view, i);
    }

    public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo140d(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.mo136a(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo139c(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.mo138b(DEFAULT_DELEGATE, view, accessibilityEvent);
    }

    public void onInitializeAccessibilityNodeInfo(View view, C0060a c0060a) {
        IMPL.mo134a(DEFAULT_DELEGATE, view, c0060a);
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.mo137a(DEFAULT_DELEGATE, viewGroup, view, accessibilityEvent);
    }

    public C0065f getAccessibilityNodeProvider(View view) {
        return IMPL.mo130a(DEFAULT_DELEGATE, view);
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return IMPL.mo135a(DEFAULT_DELEGATE, view, i, bundle);
    }
}
