package android.support.v4.p003d;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

final class C0054c implements C0052d {
    C0054c() {
    }

    public final Object mo93a(Context context) {
        return new EdgeEffect(context);
    }

    public final void mo94a(Object obj, int i, int i2) {
        ((EdgeEffect) obj).setSize(i, i2);
    }

    public final boolean mo95a(Object obj) {
        return ((EdgeEffect) obj).isFinished();
    }

    public final void mo98b(Object obj) {
        ((EdgeEffect) obj).finish();
    }

    public final boolean mo96a(Object obj, float f) {
        ((EdgeEffect) obj).onPull(f);
        return true;
    }

    public final boolean mo99c(Object obj) {
        EdgeEffect edgeEffect = (EdgeEffect) obj;
        edgeEffect.onRelease();
        return edgeEffect.isFinished();
    }

    public final boolean mo97a(Object obj, Canvas canvas) {
        return ((EdgeEffect) obj).draw(canvas);
    }
}
