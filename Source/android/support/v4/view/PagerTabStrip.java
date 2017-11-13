package android.support.v4.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class PagerTabStrip extends PagerTitleStrip {
    private int f85f;
    private int f86g;
    private int f87h;
    private int f88i;
    private int f89j;
    private int f90k;
    private final Paint f91l;
    private final Rect f92m;
    private int f93n;
    private boolean f94o;
    private boolean f95p;
    private int f96q;
    private boolean f97r;
    private float f98s;
    private float f99t;
    private int f100u;

    public PagerTabStrip(Context context) {
        this(context, null);
    }

    public PagerTabStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f91l = new Paint();
        this.f92m = new Rect();
        this.f93n = 255;
        this.f94o = false;
        this.f95p = false;
        this.f85f = this.e;
        this.f91l.setColor(this.f85f);
        float f = context.getResources().getDisplayMetrics().density;
        this.f86g = (int) ((3.0f * f) + 0.5f);
        this.f87h = (int) ((6.0f * f) + 0.5f);
        this.f88i = (int) (64.0f * f);
        this.f90k = (int) ((16.0f * f) + 0.5f);
        this.f96q = (int) ((1.0f * f) + 0.5f);
        this.f89j = (int) ((f * 32.0f) + 0.5f);
        this.f100u = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(m105b());
        setWillNotDraw(false);
        this.b.setFocusable(true);
        this.b.setOnClickListener(new C0094v(this));
        this.d.setFocusable(true);
        this.d.setOnClickListener(new C0095w(this));
        if (getBackground() == null) {
            this.f94o = true;
        }
    }

    public void setTabIndicatorColor(int i) {
        this.f85f = i;
        this.f91l.setColor(this.f85f);
        invalidate();
    }

    public void setTabIndicatorColorResource(int i) {
        setTabIndicatorColor(getContext().getResources().getColor(i));
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (i4 < this.f87h) {
            i4 = this.f87h;
        }
        super.setPadding(i, i2, i3, i4);
    }

    public void setTextSpacing(int i) {
        if (i < this.f88i) {
            i = this.f88i;
        }
        super.setTextSpacing(i);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.f95p) {
            this.f94o = drawable == null;
        }
    }

    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        if (!this.f95p) {
            this.f94o = (-16777216 & i) == 0;
        }
    }

    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (!this.f95p) {
            this.f94o = i == 0;
        }
    }

    public void setDrawFullUnderline(boolean z) {
        this.f94o = z;
        this.f95p = true;
        invalidate();
    }

    final int mo100a() {
        return Math.max(super.mo100a(), this.f89j);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.f97r) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.f98s = x;
                this.f99t = y;
                this.f97r = false;
                break;
            case 1:
                if (x >= ((float) (this.c.getLeft() - this.f90k))) {
                    if (x > ((float) (this.c.getRight() + this.f90k))) {
                        this.a.setCurrentItem(this.a.getCurrentItem() + 1);
                        break;
                    }
                }
                this.a.setCurrentItem(this.a.getCurrentItem() - 1);
                break;
                break;
            case 2:
                if (Math.abs(x - this.f98s) > ((float) this.f100u) || Math.abs(y - this.f99t) > ((float) this.f100u)) {
                    this.f97r = true;
                    break;
                }
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.c.getLeft() - this.f90k;
        int right = this.c.getRight() + this.f90k;
        int i = height - this.f86g;
        this.f91l.setColor((this.f93n << 24) | (this.f85f & 16777215));
        canvas.drawRect((float) left, (float) i, (float) right, (float) height, this.f91l);
        if (this.f94o) {
            this.f91l.setColor(-16777216 | (this.f85f & 16777215));
            canvas.drawRect((float) getPaddingLeft(), (float) (height - this.f96q), (float) (getWidth() - getPaddingRight()), (float) height, this.f91l);
        }
    }

    final void mo101a(int i, float f, boolean z) {
        Rect rect = this.f92m;
        int height = getHeight();
        int i2 = height - this.f86g;
        rect.set(this.c.getLeft() - this.f90k, i2, this.c.getRight() + this.f90k, height);
        super.mo101a(i, f, z);
        this.f93n = (int) ((Math.abs(f - 0.5f) * 2.0f) * 255.0f);
        rect.union(this.c.getLeft() - this.f90k, i2, this.c.getRight() + this.f90k, height);
        invalidate(rect);
    }
}
