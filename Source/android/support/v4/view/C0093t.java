package android.support.v4.view;

import android.view.MotionEvent;

final class C0093t implements C0091u {
    C0093t() {
    }

    public final int mo159a(MotionEvent motionEvent, int i) {
        return motionEvent.findPointerIndex(i);
    }

    public final int mo160b(MotionEvent motionEvent, int i) {
        return motionEvent.getPointerId(i);
    }

    public final float mo161c(MotionEvent motionEvent, int i) {
        return motionEvent.getX(i);
    }

    public final float mo162d(MotionEvent motionEvent, int i) {
        return motionEvent.getY(i);
    }
}
