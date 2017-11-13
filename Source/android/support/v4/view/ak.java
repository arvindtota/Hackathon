package android.support.v4.view;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;

class ak extends aj {
    ak() {
    }

    final long mo128a() {
        return ValueAnimator.getFrameDelay();
    }

    public final void mo121a(View view, int i, Paint paint) {
        view.setLayerType(i, paint);
    }
}
