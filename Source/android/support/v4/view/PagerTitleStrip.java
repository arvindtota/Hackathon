package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.google.android.gms.C0145R;
import java.lang.ref.WeakReference;

public class PagerTitleStrip extends ViewGroup implements Decor {
    private static final int[] f68n = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final int[] f69o = new int[]{16843660};
    private static final C0073y f70q;
    ViewPager f71a;
    TextView f72b;
    TextView f73c;
    TextView f74d;
    int f75e;
    private int f76f;
    private float f77g;
    private int f78h;
    private int f79i;
    private boolean f80j;
    private boolean f81k;
    private final C0096x f82l;
    private WeakReference f83m;
    private int f84p;

    static {
        if (VERSION.SDK_INT >= 14) {
            f70q = new aa();
        } else {
            f70q = new C0097z();
        }
    }

    private static void m100a(TextView textView) {
        f70q.mo118a(textView);
    }

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        boolean z = false;
        super(context, attributeSet);
        this.f76f = -1;
        this.f77g = -1.0f;
        this.f82l = new C0096x();
        View textView = new TextView(context);
        this.f72b = textView;
        addView(textView);
        textView = new TextView(context);
        this.f73c = textView;
        addView(textView);
        textView = new TextView(context);
        this.f74d = textView;
        addView(textView);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, f68n);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            this.f72b.setTextAppearance(context, resourceId);
            this.f73c.setTextAppearance(context, resourceId);
            this.f74d.setTextAppearance(context, resourceId);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            setTextSize(0, (float) dimensionPixelSize);
        }
        if (obtainStyledAttributes.hasValue(2)) {
            dimensionPixelSize = obtainStyledAttributes.getColor(2, 0);
            this.f72b.setTextColor(dimensionPixelSize);
            this.f73c.setTextColor(dimensionPixelSize);
            this.f74d.setTextColor(dimensionPixelSize);
        }
        this.f79i = obtainStyledAttributes.getInteger(3, 80);
        obtainStyledAttributes.recycle();
        this.f75e = this.f73c.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(0.6f);
        this.f72b.setEllipsize(TruncateAt.END);
        this.f73c.setEllipsize(TruncateAt.END);
        this.f74d.setEllipsize(TruncateAt.END);
        if (resourceId != 0) {
            obtainStyledAttributes = context.obtainStyledAttributes(resourceId, f69o);
            z = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
        if (z) {
            m100a(this.f72b);
            m100a(this.f73c);
            m100a(this.f74d);
        } else {
            this.f72b.setSingleLine();
            this.f73c.setSingleLine();
            this.f74d.setSingleLine();
        }
        this.f78h = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    public void setTextSpacing(int i) {
        this.f78h = i;
        requestLayout();
    }

    public final int m105b() {
        return this.f78h;
    }

    public void setNonPrimaryAlpha(float f) {
        this.f84p = ((int) (255.0f * f)) & 255;
        int i = (this.f84p << 24) | (this.f75e & 16777215);
        this.f72b.setTextColor(i);
        this.f74d.setTextColor(i);
    }

    public void setTextColor(int i) {
        this.f75e = i;
        this.f73c.setTextColor(i);
        int i2 = (this.f84p << 24) | (this.f75e & 16777215);
        this.f72b.setTextColor(i2);
        this.f74d.setTextColor(i2);
    }

    public void setTextSize(int i, float f) {
        this.f72b.setTextSize(i, f);
        this.f73c.setTextSize(i, f);
        this.f74d.setTextSize(i, f);
    }

    public void setGravity(int i) {
        this.f79i = i;
        requestLayout();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) parent;
            PagerAdapter adapter = viewPager.getAdapter();
            viewPager.setInternalPageChangeListener(this.f82l);
            viewPager.setOnAdapterChangeListener(this.f82l);
            this.f71a = viewPager;
            m104a(this.f83m != null ? (PagerAdapter) this.f83m.get() : null, adapter);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.f71a != null) {
            m104a(this.f71a.getAdapter(), null);
            this.f71a.setInternalPageChangeListener(null);
            this.f71a.setOnAdapterChangeListener(null);
            this.f71a = null;
        }
    }

    final void m103a(int i, PagerAdapter pagerAdapter) {
        CharSequence charSequence;
        CharSequence charSequence2 = null;
        int count = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.f80j = true;
        if (i <= 0 || pagerAdapter == null) {
            charSequence = null;
        } else {
            charSequence = pagerAdapter.getPageTitle(i - 1);
        }
        this.f72b.setText(charSequence);
        TextView textView = this.f73c;
        if (pagerAdapter == null || i >= count) {
            charSequence = null;
        } else {
            charSequence = pagerAdapter.getPageTitle(i);
        }
        textView.setText(charSequence);
        if (i + 1 < count && pagerAdapter != null) {
            charSequence2 = pagerAdapter.getPageTitle(i + 1);
        }
        this.f74d.setText(charSequence2);
        int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        count = MeasureSpec.makeMeasureSpec((int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f), Integer.MIN_VALUE);
        height = MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE);
        this.f72b.measure(count, height);
        this.f73c.measure(count, height);
        this.f74d.measure(count, height);
        this.f76f = i;
        if (!this.f81k) {
            mo101a(i, this.f77g, false);
        }
        this.f80j = false;
    }

    public void requestLayout() {
        if (!this.f80j) {
            super.requestLayout();
        }
    }

    final void m104a(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.f82l);
            this.f83m = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.f82l);
            this.f83m = new WeakReference(pagerAdapter2);
        }
        if (this.f71a != null) {
            this.f76f = -1;
            this.f77g = -1.0f;
            m103a(this.f71a.getCurrentItem(), pagerAdapter2);
            requestLayout();
        }
    }

    void mo101a(int i, float f, boolean z) {
        if (i != this.f76f) {
            m103a(i, this.f71a.getAdapter());
        } else if (!z && f == this.f77g) {
            return;
        }
        this.f81k = true;
        int measuredWidth = this.f72b.getMeasuredWidth();
        int measuredWidth2 = this.f73c.getMeasuredWidth();
        int measuredWidth3 = this.f74d.getMeasuredWidth();
        int i2 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i3 = paddingRight + i2;
        int i4 = (width - (paddingLeft + i2)) - i3;
        float f2 = 0.5f + f;
        if (f2 > 1.0f) {
            f2 -= 1.0f;
        }
        i3 = ((width - i3) - ((int) (f2 * ((float) i4)))) - i2;
        i4 = i3 + measuredWidth2;
        int baseline = this.f72b.getBaseline();
        measuredWidth2 = this.f73c.getBaseline();
        i2 = this.f74d.getBaseline();
        int max = Math.max(Math.max(baseline, measuredWidth2), i2);
        baseline = max - baseline;
        measuredWidth2 = max - measuredWidth2;
        max -= i2;
        int measuredHeight = this.f74d.getMeasuredHeight() + max;
        i2 = Math.max(Math.max(this.f72b.getMeasuredHeight() + baseline, this.f73c.getMeasuredHeight() + measuredWidth2), measuredHeight);
        switch (this.f79i & 112) {
            case C0145R.styleable.MapAttrs_ambientEnabled /*16*/:
                height = (((height - paddingTop) - paddingBottom) - i2) / 2;
                i2 = height + baseline;
                baseline = height + measuredWidth2;
                measuredWidth2 = height + max;
                break;
            case 80:
                height = (height - paddingBottom) - i2;
                i2 = height + baseline;
                baseline = height + measuredWidth2;
                measuredWidth2 = height + max;
                break;
            default:
                i2 = paddingTop + baseline;
                baseline = paddingTop + measuredWidth2;
                measuredWidth2 = paddingTop + max;
                break;
        }
        this.f73c.layout(i3, baseline, i4, this.f73c.getMeasuredHeight() + baseline);
        baseline = Math.min(paddingLeft, (i3 - this.f78h) - measuredWidth);
        this.f72b.layout(baseline, i2, measuredWidth + baseline, this.f72b.getMeasuredHeight() + i2);
        baseline = Math.max((width - paddingRight) - measuredWidth3, this.f78h + i4);
        this.f74d.layout(baseline, measuredWidth2, baseline + measuredWidth3, this.f74d.getMeasuredHeight() + measuredWidth2);
        this.f77g = f;
        this.f81k = false;
    }

    protected void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        mode = mo100a();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int i3 = size2 - paddingTop;
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.8f), Integer.MIN_VALUE);
        i3 = MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
        this.f72b.measure(makeMeasureSpec, i3);
        this.f73c.measure(makeMeasureSpec, i3);
        this.f74d.measure(makeMeasureSpec, i3);
        if (mode2 == 1073741824) {
            setMeasuredDimension(size, size2);
        } else {
            setMeasuredDimension(size, Math.max(mode, this.f73c.getMeasuredHeight() + paddingTop));
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f = 0.0f;
        if (this.f71a != null) {
            if (this.f77g >= 0.0f) {
                f = this.f77g;
            }
            mo101a(this.f76f, f, true);
        }
    }

    int mo100a() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }
}
