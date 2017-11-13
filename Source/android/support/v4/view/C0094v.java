package android.support.v4.view;

import android.view.View;
import android.view.View.OnClickListener;

final class C0094v implements OnClickListener {
    final /* synthetic */ PagerTabStrip f120a;

    C0094v(PagerTabStrip pagerTabStrip) {
        this.f120a = pagerTabStrip;
    }

    public final void onClick(View view) {
        this.f120a.a.setCurrentItem(this.f120a.a.getCurrentItem() - 1);
    }
}
