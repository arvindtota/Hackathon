package android.support.v4.view;

import android.database.DataSetObserver;
import android.support.v4.view.ViewPager.OnPageChangeListener;

final class C0096x extends DataSetObserver implements OnAdapterChangeListener, OnPageChangeListener {
    final /* synthetic */ PagerTitleStrip f122a;
    private int f123b;

    private C0096x(PagerTitleStrip pagerTitleStrip) {
        this.f122a = pagerTitleStrip;
    }

    public final void onPageScrolled(int i, float f, int i2) {
        if (f > 0.5f) {
            i++;
        }
        this.f122a.mo101a(i, f, false);
    }

    public final void onPageSelected(int i) {
        float f = 0.0f;
        if (this.f123b == 0) {
            this.f122a.m103a(this.f122a.f71a.getCurrentItem(), this.f122a.f71a.getAdapter());
            if (this.f122a.f77g >= 0.0f) {
                f = this.f122a.f77g;
            }
            this.f122a.mo101a(this.f122a.f71a.getCurrentItem(), f, true);
        }
    }

    public final void onPageScrollStateChanged(int i) {
        this.f123b = i;
    }

    public final void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        this.f122a.m104a(pagerAdapter, pagerAdapter2);
    }

    public final void onChanged() {
        float f = 0.0f;
        this.f122a.m103a(this.f122a.f71a.getCurrentItem(), this.f122a.f71a.getAdapter());
        if (this.f122a.f77g >= 0.0f) {
            f = this.f122a.f77g;
        }
        this.f122a.mo101a(this.f122a.f71a.getCurrentItem(), f, true);
    }
}
