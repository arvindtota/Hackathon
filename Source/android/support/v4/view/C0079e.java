package android.support.v4.view;

import android.os.Bundle;
import android.support.v4.view.p004a.C0065f;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.accessibility.AccessibilityNodeProvider;

final class C0079e extends C0076b {
    C0079e() {
    }

    public final Object mo132a(C0058a c0058a) {
        return new C0085l(new C0081f(this, c0058a));
    }

    public final C0065f mo130a(Object obj, View view) {
        AccessibilityNodeProvider accessibilityNodeProvider = ((AccessibilityDelegate) obj).getAccessibilityNodeProvider(view);
        if (accessibilityNodeProvider != null) {
            return new C0065f(accessibilityNodeProvider);
        }
        return null;
    }

    public final boolean mo135a(Object obj, View view, int i, Bundle bundle) {
        return ((AccessibilityDelegate) obj).performAccessibilityAction(view, i, bundle);
    }
}
