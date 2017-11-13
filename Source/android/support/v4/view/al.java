package android.support.v4.view;

import android.view.View;
import android.view.View.AccessibilityDelegate;

class al extends ak {
    al() {
    }

    public final void mo122a(View view, C0058a c0058a) {
        view.setAccessibilityDelegate((AccessibilityDelegate) c0058a.getBridge());
    }

    public final boolean mo124a(View view, int i) {
        return view.canScrollHorizontally(i);
    }
}
