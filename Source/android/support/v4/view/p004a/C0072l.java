package android.support.v4.view.p004a;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

final class C0072l extends AccessibilityNodeProvider {
    final /* synthetic */ C0069m f107a;

    C0072l(C0069m c0069m) {
        this.f107a = c0069m;
    }

    public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
        return (AccessibilityNodeInfo) this.f107a.mo115a(i);
    }

    public final List findAccessibilityNodeInfosByText(String str, int i) {
        return this.f107a.mo116a(str, i);
    }

    public final boolean performAction(int i, int i2, Bundle bundle) {
        return this.f107a.mo117a(i, i2, bundle);
    }
}
