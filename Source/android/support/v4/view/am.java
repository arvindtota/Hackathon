package android.support.v4.view;

import android.view.View;

class am extends al {
    am() {
    }

    public final void mo125b(View view) {
        view.postInvalidateOnAnimation();
    }

    public final void mo123a(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public final int mo127c(View view) {
        return view.getImportantForAccessibility();
    }

    public final void mo126b(View view, int i) {
        view.setImportantForAccessibility(i);
    }
}
